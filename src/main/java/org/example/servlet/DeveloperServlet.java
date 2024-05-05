package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.DeveloperDTO;
import org.example.dao.impl.DeveloperRepositoryImpl;
import org.example.exception.InsertionException;
import org.example.mapper.DeveloperMapper;
import org.example.service.DeveloperService;
import org.example.util.DBUtil;
import org.example.util.PropertiesUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DeveloperServlet", urlPatterns = {"/developers"})
public class DeveloperServlet extends HttpServlet {
    private DeveloperService developerService;

    @Override
    public void init() throws ServletException {
        PropertiesUtil propertiesUtil = new PropertiesUtil("Data.properties");
        DBUtil dbUtil = new DBUtil(propertiesUtil);
        try {
            DeveloperRepositoryImpl developerRepository = new DeveloperRepositoryImpl(dbUtil.getConnection());
            this.developerService = new DeveloperService(developerRepository);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize developer repository", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/developer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            try {
                String website = request.getParameter("website");
                DeveloperDTO developer = new DeveloperDTO(null, name, website);
                developerService.saveDeveloper(developer);
                request.setAttribute("message", "Developer added successfully!");
            }
            catch (InsertionException e){
                request.setAttribute("message", e.getMessage());
            }
        } else if ("search".equals(action)) {
            List<DeveloperDTO> developers = developerService.findDevelopersByName(name);
            request.setAttribute("developers", developers.stream().map(DeveloperMapper::toSDTO).toList());
        }

        request.getRequestDispatcher("WEB-INF/developer.jsp").forward(request, response);
    }
}
