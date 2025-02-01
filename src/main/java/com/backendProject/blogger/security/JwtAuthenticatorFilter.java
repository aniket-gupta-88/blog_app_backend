package com.backendProject.blogger.security;

import com.backendProject.blogger.exceptions.ApiException; // Import your ApiException
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticatorFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestToken = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer")) {

            token = requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                throw new ApiException("Unable to get Jwt token", HttpStatus.BAD_REQUEST);
            } catch (ExpiredJwtException e) {
                throw new ApiException("Jwt token has expired", HttpStatus.UNAUTHORIZED);
            } catch (MalformedJwtException e) {
                throw new ApiException("Invalid Jwt", HttpStatus.BAD_REQUEST);
            }
        } else {
            System.out.println("Jwt token does not found or is incorrect");
//            throw new ApiException("Jwt token does not found or is incorrect", HttpStatus.BAD_REQUEST);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new ApiException("Invalid jwt token", HttpStatus.UNAUTHORIZED);
            }
        } else {
            System.out.println("username is null");
//            if (username == null) {
//                throw new ApiException("Username is null", HttpStatus.BAD_REQUEST);
//            } else { // Context is not null (user is already authenticated)
//                throw new ApiException("User is already authenticated", HttpStatus.CONFLICT); // Or another appropriate status
//            }
        }

        filterChain.doFilter(request, response);
    }
}