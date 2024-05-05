<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .container { max-width: 400px; margin: auto; padding: 20px; border: 1px solid #a55ab1; border-radius: 5px; }
        input[type="email"], input[type="password"] { width: 100%; padding: 10px; margin: 5px 0 20px 0; display: inline-block; border: 1px solid #7e2695; box-sizing: border-box; }
        button { background-color: #4cafa3; color: #32438e; padding: 14px 20px; margin: 8px 0; border: none; cursor: pointer; width: 100%; }
        button:hover { opacity: 0.8; }
        .error { color: #4d8857; }
    </style>
</head>
<body>
<div class="container">
    <h2>Login</h2>
    <% String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) { %>
    <p class="error"><%= error %></p>
    <% } %>
    <form action="login" method="post">
        <div>
            <label> Email:</label>
            <input type="email" name="email" required>
        </div>
        <div>
            <label>Password:</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit">Login</button>
    </form>
    <p>Don't have an account? <a href="register">Register here</a></p>
</div>
</body>
</html>
