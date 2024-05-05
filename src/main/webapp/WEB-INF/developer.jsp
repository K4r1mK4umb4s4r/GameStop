<%@ page import="org.example.DTO.DeveloperDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.ServletDTO.DeveloperSDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление разработчиками</title>
</head>
<body>
<h1>Управление разработчиками</h1>

<h2>Добавить нового разработчика</h2>
<form action="developers" method="post">
    <input type="hidden" name="action" value="add">
    <label>Имя: <input type="text" name="name" required></label><br>
    <label>Веб-сайт: <input type="text" name="website" required></label><br>
    <button type="submit">Добавить разработчика</button>
</form>

<hr>

<h2>Поиск разработчиков по имени</h2>
<form action="developers" method="post">
    <input type="hidden" name="action" value="search">
    <label>Имя: <input type="text" name="name" required></label>
    <button type="submit">Искать</button>
</form>

<% if (request.getAttribute("message") != null && !((String) request.getAttribute("message")).isEmpty()) { %>
<p><%= request.getAttribute("message") %></p>
<% } %>

<%
    List<DeveloperSDTO> developers = (List<DeveloperSDTO>) request.getAttribute("developers");
    if (developers != null && !developers.isEmpty()) {
%>
<h3>Результаты поиска:</h3>
<ul>
    <% for (DeveloperSDTO developer : developers) { %>
    <li><%= developer.getTitle() %> - <%= developer.getWebsite() %></li>
    <% } %>
</ul>
<% } %>

</body>
</html>
