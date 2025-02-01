package com.backendProject.blogger.security;

import com.backendProject.blogger.exceptions.ApiException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied !!");
//    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            throw new ApiException("Authentication failed", org.springframework.http.HttpStatus.UNAUTHORIZED); // Or a more specific message and status if needed

        } catch (ApiException apiException) { // Catch your ApiException
            String json = String.format("{\"message\": \"%s\"}", apiException.getMessage());
            response.getWriter().write(json);

        } catch (Exception e) { // Catch any other unexpected exceptions
            e.printStackTrace(); // Log the error for debugging
            String json = "{\"message\": \"An unexpected error occurred during authentication\"}"; // Generic message to the client
            response.getWriter().write(json);
        }
    }
}
