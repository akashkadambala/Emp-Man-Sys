<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Leave Requests</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f7fb; margin: 0; padding: 0; }
        .header { background: #1a73e8; color: white; padding: 16px 24px; display: flex; justify-content: space-between; align-items: center; }
        .header a { color: white; text-decoration: none; margin-left: 16px; }
        .container { padding: 24px; }
        .panel { background: white; border-radius: 8px; padding: 20px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); }
        .panel h2 { margin-top: 0; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td { text-align: left; padding: 12px; border-bottom: 1px solid #e0e0e0; }
        th { background: #f1f3f4; }
        .actions a { margin-right: 8px; color: #1a73e8; text-decoration: none; }
        .actions form { display: inline; }
        .btn { padding: 8px 14px; border: none; border-radius: 4px; cursor: pointer; background: #1a73e8; color: white; text-decoration: none; }
        .btn.secondary { background: #5f6368; }
    </style>
</head>
<body>
<div class="header">
    <div>
        <h1>Leave Requests</h1>
    </div>
    <div>
        <a class="btn secondary" href="${pageContext.request.contextPath}/employee?action=list">Back to Dashboard</a>
        <a class="btn" href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="panel">
        <h2>Pending and Processed Requests</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Employee</th>
                <th>Email</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Map<String, Object>> requests = (List<Map<String, Object>>) request.getAttribute("requests");
                if (requests != null) {
                    for (Map<String, Object> leave : requests) {
            %>
            <tr>
                <td><%= leave.get("id") %></td>
                <td><%= leave.get("name") %></td>
                <td><%= leave.get("email") %></td>
                <td><%= leave.get("start_date") %></td>
                <td><%= leave.get("end_date") %></td>
                <td><%= leave.get("reason") %></td>
                <td><%= leave.get("status") %></td>
                <td class="actions">
                    <% if ("PENDING".equals(leave.get("status"))) { %>
                        <form action="<%= request.getContextPath() %>/leave" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="approve" />
                            <input type="hidden" name="id" value="<%= leave.get("id") %>" />
                            <button class="btn" type="submit">Approve</button>
                        </form>
                        <form action="<%= request.getContextPath() %>/leave" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="reject" />
                            <input type="hidden" name="id" value="<%= leave.get("id") %>" />
                            <button class="btn secondary" type="submit">Reject</button>
                        </form>
                    <% } else { %>
                        <span>—</span>
                    <% } %>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
