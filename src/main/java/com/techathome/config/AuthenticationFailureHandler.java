package com.techathome.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
      throws IOException, ServletException {
    	// TODO errorInfo sayfada kullanıcıya gösterilmeli
    	request.setAttribute("errorInfo", exception.getMessage());
    	super.setDefaultFailureUrl("/login?error=true");

    	super.onAuthenticationFailure(request, response, exception);
        
    }

}
