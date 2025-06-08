package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCredentialsDB extends MainConnection {
    
    /**
     * Inserts a new user credential into the database.
     *
     * @param username The username of the user.
     * @param password The encrypted password of the user.
     * @return true if the user credential was successfully inserted, false otherwise.
     */
    public static boolean insertUserCredential(int id, String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO user_credentials (user_id, username, password) VALUES (?, ?, ?)";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0; // true if at least one row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, pstmt, null);
        }
    }
    
    /**
     * Retrieves user credentials from the database based on the specified username.
     *
     * @param username The username to search for in the database.
     * @return A ResultSet containing the user credentials if the username exists;
     *         otherwise, returns null if an error occurs during the query execution.
     *         The caller is responsible for closing the ResultSet.
     */
    public static boolean findUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user_credentials WHERE username = ?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, pstmt, rs);
        }
    }

    /**
     * Retrieves user credentials from the database based on the given user ID.
     *
     * @param userId The ID of the user whose credentials are to be fetched.
     * @return A {@link ResultSet} containing the user credentials. Returns null if an error occurs.
     */
    public static ResultSet findUserById(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user_credentials WHERE user_id = ?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    
    /**
     * Updates the username and/or password for the specified user ID.
     *
     * @param userId   The ID of the user to update.
     * @param username The updated username (set null to keep it unchanged).
     * @param password The updated password (set null to keep it unchanged).
     * @return true if the user credential was successfully updated, false otherwise.
     */
    public static boolean updateUserCredential(int userId, String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        StringBuilder sql = new StringBuilder("UPDATE user_credentials SET ");
        
        // Dynamically build the query with only the provided fields
        if (username != null) sql.append("username = ?, ");
        if (password != null) sql.append("password = ?, ");
        sql = new StringBuilder(sql.substring(0, sql.length() - 2)); // Remove trailing comma
        sql.append(" WHERE user_id = ?");
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql.toString());
            
            int paramIndex = 1;
            if (username != null) pstmt.setString(paramIndex++, username);
            if (password != null) pstmt.setString(paramIndex++, password);
            pstmt.setInt(paramIndex, userId);
            
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, pstmt, null);
        }
    }
    
    /**
     * Deletes a user credential from the database by user ID.
     *
     * @param userId The ID of the user to delete.
     * @return true if the user credential was successfully deleted, false otherwise.
     */
    public static boolean deleteUserCredential(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM user_credentials WHERE user_id = ?";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0; // true if at least one row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, pstmt, null);
        }
    }
    
    
    
    /**
     * Fetches all user credentials from the database.
     *
     * @return An ObservableList containing all user credentials (as plain objects).
     */
    public static ObservableList<String[]> getAllUserCredentials() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ObservableList<String[]> userCredentialsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user_credentials";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String[] userCredential = new String[3];
                userCredential[0] = String.valueOf(rs.getInt("user_id"));
                userCredential[1] = rs.getString("username");
                userCredential[2] = rs.getString("password");
                userCredentialsList.add(userCredential);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, pstmt, rs);
        }
        
        return userCredentialsList;
    }
    
    /**
     * Validates user login credentials against the database.
     *
     * @param username The username to check
     * @param password The password to verify
     * @return true if credentials are valid, false otherwise
     */
    public static boolean checkLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user_credentials WHERE username = ? AND password = ?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, pstmt, rs);
        }
    }

    /**
     * Closes a database connection, statement, and result set.
     *
     * @param conn The database connection.
     * @param pstmt The prepared statement.
     * @param rs The result set.
     */
    private static void closeConnection(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}