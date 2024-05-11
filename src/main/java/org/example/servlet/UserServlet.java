package org.example.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DTO.UserDTO;
import org.example.ServletDTO.UserSDTO;
import org.example.dao.UserRepository;
import org.example.dao.impl.UserRepositoryImpl;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.util.DBUtil;
import org.example.util.PropertiesUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;


@WebServlet(name = "UserServlet", urlPatterns = {"/users"})
public class UserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserServlet.class);
    private UserService userService;

    @Override
    public void init() {
        PropertiesUtil propertiesUtil = new PropertiesUtil("Data.properties");
        DBUtil dbUtil = new DBUtil(propertiesUtil);
        try {
            UserRepositoryImpl userRepository = new UserRepositoryImpl(dbUtil.getConnection());
            userService = new UserService(userRepository, propertiesUtil.getProperty("salt"));
        } catch (SQLException e) {
            logger.error("Failed to initialize UserRepository in UserServlet", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<UserDTO> users = userService.getAllUsers();
            List<UserSDTO> usersDTO = users.stream().map(UserMapper::toSDTO).toList();
            request.setAttribute("users", usersDTO);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/users.jsp");
            dispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            logger.error("Error forwarding to users.jsp", e);
            throw e;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            UserSDTO user = new UserSDTO(null, name, email, password, LocalDate.now());
            userService.saveUser(UserMapper.toDTO(user));
            response.sendRedirect("users");
        } catch (Exception e) {
            logger.error("Error saving user in UserServlet", e);
            throw new ServletException("Error processing POST request", e);
        }
    }
}