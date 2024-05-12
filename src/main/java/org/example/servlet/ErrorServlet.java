package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;


@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ErrorServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String error = req.getParameter("errorMessage");
        try {
            resp.getOutputStream().println(String.format("Error: %s", error));
        } catch (IOException e) {
            logger.error("Failed to write error message to response", e);
            throw e;
        }
    }
}