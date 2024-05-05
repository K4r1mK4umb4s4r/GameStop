package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.DTO.UserDTO;
import org.example.dao.UserRepository;
import org.example.dao.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.util.DBUtil;
import org.example.mapper.UserMapper;
import org.example.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserDTO user = userService.getUserByCredentials(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", UserMapper.toSDTO(user));
                response.sendRedirect("users");
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
            }
        } catch (Exception e){
            request.setAttribute("error", "Internal server error. Please try again later.");
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
        }
    }
}
