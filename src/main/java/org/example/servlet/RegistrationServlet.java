package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.UserDTO;
import org.example.dao.UserRepository;
import org.example.dao.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.util.DBUtil;
import org.example.util.PropertiesUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        PropertiesUtil propertiesUtil = new PropertiesUtil("Data.properties");
        DBUtil dbUtil = new DBUtil(propertiesUtil);
        UserRepositoryImpl userRepository = null;
        try {
            userRepository = new UserRepositoryImpl(dbUtil.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        userService = new UserService(userRepository, propertiesUtil.getProperty("salt"));
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
            request.setAttribute("error", "Internal server error. Please try again later.");
            request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
        }
    }
}
