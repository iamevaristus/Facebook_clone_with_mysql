package com.fb.facebook.controllers;

import com.fb.facebook.service.AuthService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/",
        "/home"
}) // Specify the URL patterns to intercept
public class AuthenticationController implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Check if the user is authenticated (you can use your session verification logic here)
        if (AuthService.verifySession(request)) {
            // User is authenticated, proceed to the requested resource
            chain.doFilter(request, response);
        } else {
            // User is not authenticated, redirect to the login page or perform other actions
            response.sendRedirect("/login");
        }
    }
}

