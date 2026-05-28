package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.DBConnection;

@WebServlet("/leave")
public class LeaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");

        try {
            if ("employeeRequests".equals(action) || "ADMIN".equals(role)) {
                List<Map<String, Object>> requests = getAllLeaveRequests();
                request.setAttribute("requests", requests);
                request.getRequestDispatcher("/admin/leave_requests.jsp").forward(request, response);
                return;
            }

            request.setAttribute("action", "apply");
            request.getRequestDispatcher("/employee/apply_leave.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error in LeaveServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("apply".equals(action)) {
                applyLeave(request, session);
                response.sendRedirect(request.getContextPath() + "/employee/dashboard.jsp");
                return;
            }

            if ("approve".
equals(action) || "reject".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String status = "approve".equals(action) ? "APPROVED" : "REJECTED";
                updateLeaveStatus(id, status);
                response.sendRedirect(request.getContextPath() + "/leave?action=employeeRequests");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/employee/dashboard.jsp");
        } catch (SQLException e) {
            throw new ServletException("Database error in LeaveServlet", e);
        }
    }

    private void applyLeave(HttpServletRequest request, HttpSession session) throws SQLException {
        int userId = (Integer) session.getAttribute("userId");
        int employeeId = getEmployeeIdByUserId(userId);
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String reason = request.getParameter("reason");

        String sql = "INSERT INTO leave_requests (employee_id, start_date, end_date, reason, status) VALUES (?, ?, ?, ?, 'PENDING')";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            stmt.setString(4, reason);
            stmt.executeUpdate();
        }
    }

    private int getEmployeeIdByUserId(int userId) throws SQLException {
        String sql = "SELECT id FROM employees WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("Employee record not found for user_id=" + userId);
    }

    private List<Map<String, Object>> getAllLeaveRequests() throws SQLException {
        List<Map<String, Object>> requests = new ArrayList<>();
        String sql = "SELECT lr.id, lr.employee_id, lr.start_date, lr.end_date, lr.reason, lr.status, e.name, e.email "
                + "FROM leave_requests lr JOIN employees e ON lr.employee_id = e.id ORDER BY lr.created_at DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> requestData = new HashMap<>();
                    requestData.put("id", rs.getInt("id"));
                    requestData.put("employee_id", rs.getInt("employee_id"));
                    requestData.put("start_date", rs.getDate("start_date"));
                    requestData.put("end_date", rs.getDate("end_date"));
                    requestData.put("reason", rs.getString("reason"));
                    requestData.put("status", rs.getString("status"));
                    requestData.put("name", rs.getString("name"));
                    requestData.put("email", rs.getString("email"));
                    requests.add(requestData);
                }
            }
        }
        return requests;
    }

    private void updateLeaveStatus(int id, String status) throws SQLException {
        String sql = "UPDATE leave_requests SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
}
