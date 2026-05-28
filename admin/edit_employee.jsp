<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Edit Employee</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f7fb; margin: 0; padding: 0; }
        .header { background: #1a73e8; color: white; padding: 16px 24px; display: flex; justify-content: space-between; align-items: center; }
        .header a { color: white; text-decoration: none; margin-left: 16px; }
        .container { padding: 24px; }
        .panel { background: white; border-radius: 8px; padding: 20px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); max-width: 700px; margin: 0 auto; }
        .panel h2 { margin-top: 0; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 6px; }
        .form-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
        .actions { margin-top: 20px; display: flex; gap: 12px; }
        .btn { padding: 10px 16px; border: none; border-radius: 4px; cursor: pointer; color: white; background: #1a73e8; text-decoration: none; }
        .btn.secondary { background: #5f6368; }
    </style>
</head>
<body>
<div class="header">
    <div>
        <h1>Edit Employee</h1>
    </div>
    <div>
        <a class="btn secondary" href="${pageContext.request.contextPath}/employee?action=list">Back to Dashboard</a>
    </div>
</div>
<div class="container">
    <div class="panel">
        <h2>Employee Details</h2>
        <form action="${pageContext.request.contextPath}/employee" method="post">
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="id" value="${employee.id}" />

            <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" value="${employee.name}" required />
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${employee.email}" required />
            </div>
            <div class="form-group">
                <label for="phone">Phone</label>
                <input type="text" id="phone" name="phone" value="${employee.phone}" />
            </div>
            <div class="form-group">
                <label for="department">Department</label>
                <input type="text" id="department" name="department" value="${employee.department}" />
            </div>
            <div class="form-group">
                <label for="designation">Designation</label>
                <input type="text" id="designation" name="designation" value="${employee.designation}" />
            </div>
            <div class="form-group">
                <label for="salary">Salary</label>
                <input type="number" step="0.01" id="salary" name="salary" value="${employee.salary}" />
            </div>

            <div class="actions">
                <button class="btn" type="submit">Update Employee</button>
                <a class="btn secondary" href="${pageContext.request.contextPath}/employee?action=list">Cancel</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
