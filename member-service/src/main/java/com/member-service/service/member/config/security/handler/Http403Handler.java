package com.mod1.service.member.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod1.service.common.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Slf4j
@RequiredArgsConstructor
@Component
public class Http403Handler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error(accessDeniedException.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("403")
                .message(accessDeniedException.getMessage())
                .build();

        response.setStatus(SC_FORBIDDEN);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
