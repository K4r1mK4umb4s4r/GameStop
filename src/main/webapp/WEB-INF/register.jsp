<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add User</title>
</head>
<body>
<h1>Add User</h1>
<form action="register" method="post">
    <% String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) { %>
    <p style="color:#7d15c3;"><%= error %></p>
    <% } %>
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>
    <label for="email">Email:</label><br>
    <input type="email" id="email" name="email"><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password"><br>
    <input type="submit" value="Add User">
</form>
</body>
</html>
