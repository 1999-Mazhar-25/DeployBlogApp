package com.mazhar.blogs.app.controllers;


import com.mazhar.blogs.app.exceptions.ApiException;
import com.mazhar.blogs.app.payloads.JwtAuthRequest;
import com.mazhar.blogs.app.payloads.JwtAuthResponse;
import com.mazhar.blogs.app.payloads.UserDto;
import com.mazhar.blogs.app.services.UserService;
import com.mazhar.blogs.app.utils.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest jwtAuthRequest
    ) throws Exception {
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(jwtAuthRequest.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(upat);
        } catch (BadCredentialsException e) {

            System.out.println("Invalid Details !!!");
            throw new ApiException("Invalid Username or password !!");

        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto)
    {
        UserDto userDto1 = this.userService.registerNewUser(userDto);

        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }
}
