package com.golapadeok.fluo.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golapadeok.fluo.common.jwt.exception.JwtErrorException;
import com.golapadeok.fluo.domain.task.exception.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtErrorException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
        }
    }

    private void setErrorResponse(HttpStatus httpStatus, HttpServletResponse response, JwtErrorException e) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json; charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse errorResponse = new ErrorResponse(e.getJwtErrorStatus().getMessage());

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
