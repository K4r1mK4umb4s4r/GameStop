package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DTO.DeveloperDTO;
import org.example.dao.impl.DeveloperRepositoryImpl;
import org.example.mapper.DeveloperMapper;
import org.example.service.DeveloperService;
import org.example.util.DBUtil;
import org.example.util.PropertiesUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "DeveloperServlet", urlPatterns = {"/developers"})
public class DeveloperServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DeveloperServlet.class);
    private DeveloperService developerService;

    @Override
    public void init() throws ServletException {
        PropertiesUtil propertiesUtil = new PropertiesUtil("Data.properties");
        DBUtil dbUtil = new DBUtil(propertiesUtil);
        try {
            DeveloperRepositoryImpl developerRepository = new DeveloperRepositoryImpl(dbUtil.getConnection());
            this.developerService = new DeveloperService(developerRepository);
        } catch (SQLException e) {
            logger.error("Failed to initialize developer repository", e);
            throw new RuntimeException("Failed to initialize developer repository", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getRequestDispatcher("WEB-INF/developer.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error forwarding to developer.jsp", e);
            throw e;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                String website = request.getParameter("website");
                DeveloperDTO developer = new DeveloperDTO(null, name, website);
                developerService.saveDeveloper(developer);
                request.setAttribute("message", "Developer added successfully!");
            } else if ("search".equals(action)) {
                List<DeveloperDTO> developers = developerService.findDevelopersByName(name);
                request.setAttribute("developers", developers.stream().map(DeveloperMapper::toSDTO).toList());
            }
        } catch (Exception e) {
            logger.error("Error processing POST request", e);
            request.setAttribute("message", "Error processing request: " + e.getMessage());
        }

        request.getRequestDispatcher("WEB-INF/developer.jsp").forward(request, response);
    }
}
