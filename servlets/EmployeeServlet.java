package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
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
            if ("add".equals(action)) {
                request.getRequestDispatcher("/admin/add_employee.jsp").forward(request, response);
                return;
            }

            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Map<String, Object> employee = getEmployeeById(id);
                request.setAttribute("employee", employee);
                request.getRequestDispatcher("/admin/edit_employee.jsp").forward(request, response);
                return;
            }

            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                deleteEmployee(id);
                response.sendRedirect(request.getContextPath() + "/employee?action=list");
                return;
            }

            if ("list".equals(action) || "ADMIN".equals(role)) {
                List<Map<String, Object>> employees = getAllEmployees();
                request.setAttribute("employees", employees);
                request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
                return;
            }

            int userId = (Integer) session.getAttribute("userId");
            Map<String, Object> employee = getEmployeeByUserId(userId);
            request.setAttribute("employee", employee);
            request.getRequestDispatcher("/employee/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error in EmployeeServlet", e);
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
            if ("create".equals(action)) {
                createEmployee(request);
                response.sendRedirect(request.getContextPath() + "/employee?action=list");
                return;
            }

            if ("update".equals(action)) {
                updateEmployee(request);
                response.sendRedirect(request.getContextPath() + "/employee?action=list");
                return;
            }

            if ("viewOwn".equals(action)) {
                int userId = (Integer) session.getAttribute("userId");
                Map<String, Object> employee = getEmployeeByUserId(userId);
                request.setAttribute("employee", employee);
                request.getRequestDispatcher("/employee/dashboard.jsp").forward(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/employee?action=list");
        } catch (SQLException e) {
            throw new ServletException("Database error in EmployeeServlet", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Unable to hash password", e);
        }
    }

    private List<Map<String, Object>> getAllEmployees() throws SQLException {
        List<Map<String, Object>> employees = new ArrayList<>();
        String sql = "SELECT e.id, e.user_id, e.name, e.email, e.phone, e.department, e.designation, e.salary, u.username "
                + "FROM employees e JOIN users u ON e.user_id = u.id ORDER BY e.id";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> employee = new HashMap<>();
                    employee.put("id", rs.getInt("id"));
                    employee.put("user_id", rs.getInt("user_id"));
                    employee.put("name", rs.getString("name"));
                    employee.put("email", rs.getString("email"));
                    employee.put("phone", rs.getString("phone"));
                    employee.put("department", rs.getString("department"));
                    employee.put("designation", rs.getString("designation"));
                    employee.put("salary", rs.getBigDecimal("salary"));
                    employee.put("username", rs.getString("username"));
                    employees.add(employee);
                }
            }
        }
        return employees;
    }

    private Map<String, Object> getEmployeeById(int id) throws SQLException {
        String sql = "SELECT e.id, e.user_id, e.name, e.email, e.phone, e.department, e.designation, e.salary, u.username "
                + "FROM employees e JOIN users u ON e.user_id = u.id WHERE e.id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> employee = new HashMap<>();
                    employee.put("id", rs.getInt("id"));
                    employee.put("user_id", rs.getInt("user_id"));
                    employee.put("name", rs.getString("name"));
                    employee.put("email", rs.getString("email"));
                    employee.put("phone", rs.getString("phone"));
                    employee.put("department", rs.getString("department"));
                    employee.put("designation", rs.getString("designation"));
                    employee.put("salary", rs.getBigDecimal("salary"));
                    employee.put("username", rs.getString("username"));
                    return employee;
                }
            }
        }
        return null;
    }

    private Map<String, Object> getEmployeeByUserId(int userId) throws SQLException {
        String sql = "SELECT e.id, e.user_id, e.name, e.email, e.phone, e.department, e.designation, e.salary, u.username "
                + "FROM employees e JOIN users u ON e.user_id = u.id WHERE e.user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> employee = new HashMap<>();
                    employee.put("id", rs.getInt("id"));
                    employee.put("user_id", rs.getInt("user_id"));
                    employee.put("name", rs.getString("name"));
                    employee.put("email", rs.getString("email"));
                    employee.put("phone", rs.getString("phone"));
                    employee.put("department", rs.getString("department"));
                    employee.put("designation", rs.getString("designation"));
                    employee.put("salary", rs.getBigDecimal("salary"));
                    employee.put("username", rs.getString("username"));
                    return employee;
                }
            }
        }
        return null;
    }

    private void createEmployee(HttpServletRequest request)
            throws SQLException, NoSuchAlgorithmException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");
        String designation = request.getParameter("designation");
        String salaryValue = request.getParameter("salary");

        String hashedPassword = hashPassword(password);

        String insertUserSql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'EMPLOYEE')";
        String insertEmployeeSql = "INSERT INTO employees (user_id, name, email, phone, department, designation, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement userStmt = conn.prepareStatement(insertUserSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, username);
                userStmt.setString(2, hashedPassword);
                userStmt.executeUpdate();
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        try (PreparedStatement employeeStmt = conn.prepareStatement(insertEmployeeSql)) {
                            employeeStmt.setInt(1, userId);
                            employeeStmt.setString(2, name);
                            employeeStmt.setString(3, email);
                            employeeStmt.setString(4, phone);
                            employeeStmt.setString(5, department);
                            employeeStmt.setString(6, designation);
                            employeeStmt.setBigDecimal(7, salaryValue == null || salaryValue.isEmpty() ? java.math.BigDecimal.ZERO : new java.math.BigDecimal(salaryValue));
                            employeeStmt.executeUpdate();
                        }
                        conn.commit();
                    } else {
                        conn.rollback();
                        throw new SQLException("Failed to insert user record");
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private void updateEmployee(HttpServletRequest request) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");
        String designation = request.getParameter("designation");
        String salaryValue = request.getParameter("salary");

        String updateSql = "UPDATE employees SET name = ?, email = ?, phone = ?, department = ?, designation = ?, salary = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, department);
            stmt.setString(5, designation);
            stmt.setBigDecimal(6, salaryValue == null || salaryValue.isEmpty() ? java.math.BigDecimal.ZERO : new java.math.BigDecimal(salaryValue));
            stmt.setInt(7, id);
            stmt.executeUpdate();
        }
    }

    private void deleteEmployee(int id) throws SQLException {
        String deleteSql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
