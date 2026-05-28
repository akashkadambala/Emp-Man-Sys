<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Apply for Leave</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f7fb; margin: 0; padding: 0; }
        .header { background: #1a73e8; color: white; padding: 16px 24px; display: flex; justify-content: space-between; align-items: center; }
        .header a { color: white; text-decoration: none; margin-left: 16px; }
        .container { padding: 24px; }
        .panel { max-width: 700px; margin: 0 auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); }
        .panel h2 { margin-top: 0; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 6px; }
        .form-group input, .form-group textarea { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
        .actions { margin-top: 20px; display: flex; gap: 12px; }
        .btn { padding: 10px 16px; border: none; border-radius: 4px; cursor: pointer; color: white; background: #1a73e8; text-decoration: none; }
        .btn.secondary { background: #5f6368; }
    </style>
</head>
<body>
<div class="header">
    <div>
        <h1>Leave Application</h1>
    </div>
    <div>
        <a class="btn secondary" href="${pageContext.request.contextPath}/employee/dashboard.jsp">Back to Dashboard</a>
    </div>
</div>
<div class="container">
    <div class="panel">
        <h2>Apply for Leave</h2>
        <form action="${pageContext.request.contextPath}/leave" method="post">
            <input type="hidden" name="action" value="apply" />

            <div class="form-group">
                <label for="start_date">Start Date</label>
                <input type="date" id="start_date" name="start_date" required />
            </div>
            <div class="form-group">
                <label for="end_date">End Date</label>
                <input type="date" id="end_date" name="end_date" required />
            </div>
            <div class="form-group">
                <label for="reason">Reason</label>
                <textarea id="reason" name="reason" rows="5" required></textarea>
            </div>

            <div class="actions">
                <button class="btn" type="submit">Submit Request</button>
                <a class="btn secondary" href="${pageContext.request.contextPath}/employee/dashboard.jsp">Cancel</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
