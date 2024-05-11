<%@ page import="org.example.model.User" %>
<%@ page import="org.example.ServletDTO.UserSDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
</head>
<body>
<h1>User Profile</h1>
<% if (session.getAttribute("user") != null) { %>
<% UserSDTO user = (UserSDTO) session.getAttribute("user"); %>
<p><strong>Name:</strong> <%= user.getName() %></p>
<p><strong>Email:</strong> <%= user.getEmail() %></p>
<p><strong>Date of Registration:</strong> <%= user.getDateOfRegistration() %></p>
<% } else { %>
<p>User not logged in.</p>
<% } %>
</body>
</html>
