package com.example.todo_Backend.Common.security.signin.handler;

import com.example.todo_Backend.Common.Response.ErrorResponse;
import com.example.todo_Backend.User.Member.exception.MemberStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


import java.io.IOException;

@Slf4j
public class SignInFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(MemberStatus.NOT_FOUND_MEMBER_BY_ID.getStatus())
                .message(MemberStatus.NOT_FOUND_MEMBER_BY_ID.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        // response.getWriter().write("로그인 실패!! 이메일이나 비밀번호를 확인해주세요.");
        objectMapper.writeValue(response.getWriter(), errorResponse);
        log.info("로그인 실패. 메시지: {}", exception.getMessage());
    }
}
