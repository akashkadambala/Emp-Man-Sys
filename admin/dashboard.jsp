<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f7fb; }
        .header { background: #1a73e8; color: white; padding: 16px 24px; display: flex; justify-content: space-between; align-items: center; }
        .header a { color: white; text-decoration: none; margin-left: 16px; }
        .container { padding: 24px; }
        .panel { background: white; border-radius: 8px; padding: 20px; margin-bottom: 24px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); }
        .panel h2 { margin-top: 0; }
        .btn { padding: 10px 14px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; color: white; background: #1a73e8; }
        .btn.secondary { background: #5f6368; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td { text-align: left; padding: 12px; border-bottom: 1px solid #e0e0e0; }
        th { background: #f1f3f4; }
        .action-links a { margin-right: 8px; }
    </style>
</head>
<body>
<div class="header">
    <div>
        <h1>Admin Dashboard</h1>
        <p>Welcome, Admin</p>
    </div>
    <div>
        <a class="btn secondary" href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="panel">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <div>
                <h2>Employee Directory</h2>
                <p>View and manage employee records.</p>
            </div>
            <a class="btn" href="${pageContext.request.contextPath}/employee?action=add">Add Employee</a>
        </div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Department</th>
                <th>Designation</th>
                <th>Salary</th>
                <th>Username</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Map<String, Object>> employees = (List<Map<String, Object>>) request.getAttribute("employees");
                if (employees != null) {
                    for (Map<String, Object> emp : employees) {
            %>
            <tr>
                <td><%= emp.get("id") %></td>
                <td><%= emp.get("name") %></td>
                <td><%= emp.get("email") %></td>
                <td><%= emp.get("phone") %></td>
                <td><%= emp.get("department") %></td>
                <td><%= emp.get("designation") %></td>
                <td><%= emp.get("salary") %></td>
                <td><%= emp.get("username") %></td>
                <td class="action-links">
                    <a href="<%= request.getContextPath() %>/employee?action=edit&id=<%= emp.get("id") %>">Edit</a>
                    <a href="<%= request.getContextPath() %>/employee?action=delete&id=<%= emp.get("id") %>" onclick="return confirm('Delete this employee?');">Delete</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
    <div class="panel">
        <h2>Leave Requests</h2>
        <p>Review requests and approve or reject them.</p>
        <a class="btn" href="${pageContext.request.contextPath}/leave?action=employeeRequests">View Leave Requests</a>
    </div>
</div>
</body>
</html>
