package backend;

import java.sql.*;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/restoran";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found: " + e.getMessage());
        }
    }
    
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✓ Koneksi database berhasil!");
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal: " + e.getMessage());
        }
    }
}