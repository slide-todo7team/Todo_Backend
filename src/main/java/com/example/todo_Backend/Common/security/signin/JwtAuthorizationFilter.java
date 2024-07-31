package com.example.todo_Backend.Common.security.signin;


import com.example.todo_Backend.Common.security.signin.service.JwtService;
import com.example.todo_Backend.User.Member.entity.Member;
import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.User.Member.Repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final RequestMatcherHolder requestMatcherHolder;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private static final String NO_CHECK_URL = "/auth/user";

    private static final String NO_URL="/api/auth/login";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return requestMatcherHolder.getRequestMatchersByMinRole(null).matches(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL) ) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getRequestURI().equals(NO_URL) ) {
            filterChain.doFilter(request, response);
            return;
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                    .flatMap(jwtService::extractEmail)
                        .flatMap(memberRepository::findByEmail)
                                .ifPresent(this::saveAuthentication);

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(Member member) {
        MemberDetails memberDetails = MemberDetails.builder()
                .member(member)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, authoritiesMapper.mapAuthorities(memberDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}