package Database;

import Model.usersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManagementDB extends MainConnection{
    /**
     * Inserts a new user into the database with the provided details.
     *
     * @param fullName         The full name of the user.
     * @param phoneNumber      The phone number of the user.
     * @param nid              The national ID of the user.
     * @param address          The address of the user.
     * @param userType         The type of the user (e.g., driver, manager).
     * @param personalLicense  The personal license of the user.
     * @return true if the user was successfully inserted into the database, false otherwise.
     */
    public static boolean insertUser(String fullName, String phoneNumber, long nid, String address, String userType, String personalLicense) {
        // Prepare the SQL query
        String query = "INSERT INTO users (full_name, phone_number, NID, address, type, personal_liscence) VALUES (?, ?, ?, ?, ?, ?)";
        
        // Establish a connection and prepare the statement
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            
            // Set parameters
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setLong(3, nid);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, userType);
            preparedStatement.setString(6, personalLicense);
            
            // Execute the query and check if the insertion was successful
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Return true if rows were affected (successful insertion)
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if any exception occurs
        }
    }
    
    // Method to get user by user_id (for validation or fetching)
    public static ResultSet getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean updateUser(usersModel user) {
        String query = "UPDATE users SET full_name = ?, phone_number = ?, NID = ?, address = ?, type = ?, personal_liscence = ? WHERE user_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            
            // Set the parameters using the fields from the usersModel object
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setLong(3, user.getNid());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getType().toString()); // Use the UserType enum and convert to string
            preparedStatement.setString(6, user.getPersonalLicense());
            preparedStatement.setInt(7, user.getUserId()); // Use the userId to identify which user to update
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            return rowsAffected > 0;  // Return true if update is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurs
        }
    }
    
    // Method to delete a user (optional, for later usage)
    public static boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Method to fetch users from the database and return as ObservableList
    public static ObservableList<usersModel> getUsers() {
        setConnection();
        ObservableList<usersModel> list = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = Con.prepareStatement("SELECT * FROM `users`");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Get the userType from database and convert to the enum UserType
                usersModel.UserType userType = usersModel.UserType.valueOf(rs.getString("type").toUpperCase());
                
                list.add(new usersModel(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getLong("NID"),
                        rs.getString("address"),
                        userType,
                        rs.getString("personal_liscence")
                ));
            }
            Con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
