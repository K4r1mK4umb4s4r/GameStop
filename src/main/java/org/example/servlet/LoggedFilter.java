package org.example.servlet;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter("/user/*")
public class LoggedFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LoggedFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            if (httpServletRequest.getAttribute("loggedUser") == null) {
                logger.warn("User not logged in, redirecting...");
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error?errorMessage=notLogged");
                return;
            }
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            logger.error("Error in LoggedFilter", e);
            throw e;
        }
    }
}
