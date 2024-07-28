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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import org.springframework.security.web.SecurityFilterChain;
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


    // Swagger URL
    private final String[] permitUrl = new String[]{"/swagger", "/swagger-ui.html", "/swagger-ui/**"
            , "/api-docs", "/api-docs/**", "/v3/api-docs/**", "/uploadImage/**"
    };

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
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((request) -> {
                        
                        // (신버전)
                        request.requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(null)).permitAll();
                        // request.requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(MemberRole.USER)).hasAnyAuthority(MemberRole.USER.getKey());

                        request.anyRequest().authenticated();
                    }
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
                "http://localhost:3000", "http://localhost:3001"
                , "https://localhost:3001", "https://tea-bliss.vercel.app/"
                , "https://teabliss.kro.kr/"
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

}
