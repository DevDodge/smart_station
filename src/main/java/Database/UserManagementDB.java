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
    public static int addUserGetKey(String fullName, String phoneNumber, long nid, String address, String userType, String personalLicense) {
        // Prepare the SQL query
        String query = "INSERT INTO users (full_name, phone_number, NID, address, type, personal_liscence) VALUES (?, ?, ?, ?, ?, ?)";

        // Establish a connection and prepare the statement
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setLong(3, nid);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, userType);
            preparedStatement.setString(6, personalLicense);


            // Execute the query and check if the insertion was successful
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 if any exception occurs
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
    /**
     * Retrieves all users with driver type from the database.
     *
     * @return An ObservableList containing all driver users.
     */
    public static ObservableList<usersModel> getDrivers() {
        setConnection();
        ObservableList<usersModel> list = FXCollections.observableArrayList();

        try {
            // Query specifically for users with type = 'driver'
            PreparedStatement stmt = Con.prepareStatement("SELECT * FROM `users` WHERE type = 'driver'");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Create driver model objects from the result set
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

    /**
     * Searches for drivers based on a search term matching name, phone, or NID.
     *
     * @param searchTerm The search term to match against driver data.
     * @return An ObservableList containing matching driver users.
     */
    public static ObservableList<usersModel> searchDrivers(String searchTerm) {
        setConnection();
        ObservableList<usersModel> list = FXCollections.observableArrayList();

        try {
            // Prepare query with search conditions
            String query = "SELECT * FROM `users` WHERE type = 'driver' AND " +
                    "(full_name LIKE ? OR phone_number LIKE ? OR NID LIKE ?)";

            PreparedStatement stmt = Con.prepareStatement(query);
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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

    /**
     * Checks if a driver has assigned vehicles.
     *
     * @param driverId The ID of the driver to check.
     * @return true if the driver has vehicles assigned, false otherwise.
     */
    public static boolean driverHasVehicles(int driverId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM vehicle WHERE user_id = ?")) {

            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Gets available drivers that are not assigned to any vehicle.
     *
     * @return An ObservableList of available drivers.
     */
    public static ObservableList<usersModel> getAvailableDrivers() {
        setConnection();
        ObservableList<usersModel> list = FXCollections.observableArrayList();

        try {
            // Query for drivers not assigned to any vehicle
            String query = "SELECT u.* FROM users u WHERE u.type = 'driver' " +
                    "AND NOT EXISTS (SELECT 1 FROM vehicle v WHERE v.user_id = u.user_id)";

            PreparedStatement stmt = Con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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
