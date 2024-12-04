package com.example.dao;

import java.sql.*;
import com.example.model.User;

public class UserDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/LoginSystem"; // Change DB details
    private static final String JDBC_USER = "root";  // Change username
    private static final String JDBC_PASSWORD = "";  // Change password

    // Method to verify if the user exists in the database with the correct password
    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";  // Simple query (plain-text password)

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);  // Remember: Hash passwords in a real system

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Create a user object if login is successful
                    int id = resultSet.getInt("id");
                    String dbUsername = resultSet.getString("username");
                    String dbPassword = resultSet.getString("password");
                    return new User(id, dbUsername, dbPassword);
                }
            }
        }
        return null;  // Return null if no matching user was found
    }
}
