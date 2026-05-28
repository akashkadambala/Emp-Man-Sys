<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Employee Management System - Login</title>
    <style>	
        body { font-family: Arial, sans-serif; background: #f5f5f5; margin:0; padding:0; }
        .container { width: 360px; margin: 100px auto; padding: 30px; background: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { margin-bottom: 20px; font-size: 24px; }
        .input-field { width: 100%; margin-bottom: 16px; }
        .input-field input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
        button { width: 100%; padding: 10px; border: none; background: #0073e6; color: white; font-size: 16px; border-radius: 4px; cursor: pointer; }
        .error { color: #b00020; margin-bottom: 16px; }
    </style>
</head>
<body>
<div class="container">
    <h1>Login</h1>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="input-field">
            <label for="username">Username</label><br />
            <input type="text" id="username" name="username" required />
        </div>
        <div class="input-field">
            <label for="password">Password</label><br />
            <input type="password" id="password" name="password" required />
        </div>
        <button type="submit">Sign In</button>
    </form>
</div>
</body>
</html>
