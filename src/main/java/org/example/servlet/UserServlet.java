package org.example.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@WebServlet(name = "UserServlet", urlPatterns = {"/users"})
public class UserServlet extends HttpServlet {
    private UserService userService;

    UserRepository userRepository;

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
        List<UserDTO> users = userService.getAllUsers();
        List<UserSDTO> usersDTO = users.stream().map(UserMapper::toSDTO).toList();
        request.setAttribute("users", usersDTO);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/users.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserSDTO user = new UserSDTO(null, name, email, password, LocalDate.now());
        userService.saveUser(UserMapper.toDTO(user));
        response.sendRedirect("users");
    }
}
