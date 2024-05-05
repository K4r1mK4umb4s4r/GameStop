package org.example.servlet;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/user/*")
public class LoggedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (httpServletRequest.getAttribute("loggedUser") == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error?errorMessage=notLogged");
            return;

        }
        chain.doFilter(request, response);
    }
}
