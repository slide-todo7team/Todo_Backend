package com.example.todo_Backend.Common.Config;

import com.example.todo_Backend.Common.security.signin.JwtAuthorizationFilter;
import com.example.todo_Backend.Common.security.signin.JwtExceptionFilter;
import com.example.todo_Backend.Common.security.signin.RequestMatcherHolder;
import com.example.todo_Backend.Common.security.signin.UsernamePasswordAuthenticationFilter;
import com.example.todo_Backend.Common.security.signin.handler.SignInFailureHandler;
import com.example.todo_Backend.Common.security.signin.handler.SignInSuccessHandler;
import com.example.todo_Backend.Common.security.signin.service.JwtService;
import com.example.todo_Backend.Common.security.signin.service.UserDetailsServiceImpl;
import com.example.todo_Backend.User.Member.Repository.MemberRepository;
import com.example.todo_Backend.User.Member.entity.MemberRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;


import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailService;
    private final ObjectMapper objectMapper;
    private final RequestMatcherHolder requestMatcherHolder;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                // error endpoint를 열어줘야 함, favicon.ico 추가!
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> corsFilter()))
//                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((request) ->
                        request.requestMatchers("/api/auth/user","/api/auth/login","/api-docs/**","/v3/api-docs/**","/swagger-ui/**").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(getUnathorizedEntryPoint())
                        .accessDeniedHandler(getAccessDeniedHandler())
                );


        http
                .addFilterAfter(usernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter(), JwtAuthorizationFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfiguration corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://http://52.78.126.130:8080/"
                ,"http://localhost:8080/"
                ,"http://localhost:3000"
                ,"http://localhost:3001"
        ));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization", "Set-Cookie"));
        config.setMaxAge(3600L);
        return config;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        return delegatingPasswordEncoder;
    }

    @Bean
    public SignInSuccessHandler signInSuccessHandler(){
        return new SignInSuccessHandler(memberRepository, jwtService,objectMapper);
    }

    @Bean
    public SignInFailureHandler signInFailureHandler(){
        return new SignInFailureHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception{
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter(objectMapper);
        usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(signInSuccessHandler());
        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(signInFailureHandler());
        return usernamePasswordAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(requestMatcherHolder, memberRepository, jwtService);
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter();
    }

    //권한없이 접근했을 때 접근 거부 상황 처리
    private AccessDeniedHandler accessDeniedHandler = null;

    private AccessDeniedHandler getAccessDeniedHandler() {
        if(accessDeniedHandler == null){
            return accessDeniedHandler = (request, response, accessDeniedException) -> {
                response.sendError(HttpServletResponse.SC_FORBIDDEN); //403에러 발생
            };
        }
        return accessDeniedHandler;
    }

    //사용자 인증이 실패한 경우
    private AuthenticationEntryPoint authenticationEntryPoint = null;
    private AuthenticationEntryPoint getUnathorizedEntryPoint(){
        if(authenticationEntryPoint == null){
            return authenticationEntryPoint = (request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED); //401 에러 발생
            };
        }
        return authenticationEntryPoint;
    }

}
