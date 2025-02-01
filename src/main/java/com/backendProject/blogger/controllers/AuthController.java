package com.backendProject.blogger.controllers;

import com.backendProject.blogger.exceptions.ApiException; // Import your ApiException
import com.backendProject.blogger.payloads.JwtAuthRequest;
import com.backendProject.blogger.payloads.JwtAuthResponse;
import com.backendProject.blogger.payloads.UserDto;
import com.backendProject.blogger.security.JwtTokenHelper;
import com.backendProject.blogger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) { // No throws Exception
        try {
            this.authenticate(request.getUsername(), request.getPassword());

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
            String token = this.jwtTokenHelper.generateToken(userDetails);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(token);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException ex) {
            throw new ApiException("Invalid username or password", HttpStatus.UNAUTHORIZED); // Throw ApiException
        } catch (Exception ex) {
            throw new ApiException("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR); // Throw ApiException
        }
    }

    private void authenticate(String username, String password) { // No throws Exception
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException ex) {
            throw new ApiException("Invalid username or password", HttpStatus.UNAUTHORIZED); // Re-throw as ApiException
        } catch (Exception ex) {
            System.out.println("Authentication failed");
        }
    }

    //register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }
}