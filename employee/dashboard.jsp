<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Employee Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f7fb; margin: 0; padding: 0; }
        .header { background: #1a73e8; color: white; padding: 16px 24px; display: flex; justify-content: space-between; align-items: center; }
        .header a { color: white; text-decoration: none; margin-left: 16px; }
        .container { padding: 24px; }
        .panel { background: white; border-radius: 8px; padding: 20px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); max-width: 800px; margin: 0 auto; }
        .panel h2 { margin-top: 0; }
        .profile-item { margin-bottom: 12px; }
        .profile-item strong { display: inline-block; width: 140px; }
        .btn { padding: 10px 16px; border: none; border-radius: 4px; cursor: pointer; color: white; background: #1a73e8; text-decoration: none; }
    </style>
</head>
<body>
<div class="header">
    <div>
        <h1>Employee Dashboard</h1>
        <p>Welcome, Employee</p>
    </div>
    <div>
        <a class="btn" href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="panel">
        <h2>Your Profile</h2>
        <%
            Map<String, Object> employee = (Map<String, Object>) request.getAttribute("employee");
        %>
        <div class="profile-item"><strong>Name:</strong> <%= employee != null ? employee.get("name") : "N/A" %></div>
        <div class="profile-item"><strong>Email:</strong> <%= employee != null ? employee.get("email") : "N/A" %></div>
        <div class="profile-item"><strong>Phone:</strong> <%= employee != null ? employee.get("phone") : "N/A" %></div>
        <div class="profile-item"><strong>Department:</strong> <%= employee != null ? employee.get("department") : "N/A" %></div>
        <div class="profile-item"><strong>Designation:</strong> <%= employee != null ? employee.get("designation") : "N/A" %></div>
        <div class="profile-item"><strong>Salary:</strong> <%= employee != null ? employee.get("salary") : "N/A" %></div>
        <div class="profile-item"><strong>Username:</strong> <%= employee != null ? employee.get("username") : "N/A" %></div>

        <div style="margin-top: 24px;">
            <a class="btn" href="${pageContext.request.contextPath}/leave?action=apply">Apply for Leave</a>
        </div>
    </div>
</div>
</body>
</html>
