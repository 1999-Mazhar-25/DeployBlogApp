package com.mazhar.blogs.app.config;

import com.mazhar.blogs.app.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticatorFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //get token
        String requestToken = request.getHeader("Authorization");

        System.out.println("this is request Header " + requestToken);

        String token = null;
        String userName = null;

        //Bearer 8348239 (trim the Bearer_ and after that token is present)

        if ((requestToken != null) && requestToken.startsWith("Bearer")) {
            token = requestToken.substring(7);


            try {
                userName = this.jwtTokenHelper.getUsernameFromToken(token);

            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token !!!");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired !!!");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT token !!!");
            }

        } else {
            System.out.println("JWT token does not begin with Bearer");
        }


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (this.jwtTokenHelper.validateToken(token, userDetails))
            {
                UsernamePasswordAuthenticationToken upat = new
                        UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
            else
            {
                System.out.println("Invalid JWT token !!!");
            }
        } else
        {
            System.out.println("username is null or context is null !!!");
        }
        filterChain.doFilter(request, response);




    }
}
