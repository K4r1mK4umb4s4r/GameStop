package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DTO.UserDTO;
import org.example.dao.UserRepository;
import org.example.dao.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.util.DBUtil;
import org.example.util.PropertiesUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);
    private UserService userService;

    @Override
    public void init() {
        try {
            PropertiesUtil propertiesUtil = new PropertiesUtil("Data.properties");
            DBUtil dbUtil = new DBUtil(propertiesUtil);
            UserRepositoryImpl userRepository = new UserRepositoryImpl(dbUtil.getConnection());
            userService = new UserService(userRepository, propertiesUtil.getProperty("salt"));
        } catch (SQLException e) {
            logger.error("Failed to initialize in RegistrationServlet", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        LocalDate joinDate = LocalDate.now();

        UserDTO userDTO = new UserDTO(null, username, email, password, joinDate);
        try {
            boolean success = userService.registerNewUser(userDTO);
            if (success) {
                response.sendRedirect("login");
            } else {
                request.setAttribute("error", "Email already exists or other registration error");
                request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error during registration process", e);
            request.setAttribute("error", "Internal server error. Please try again later.");
            request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
        }
    }
}