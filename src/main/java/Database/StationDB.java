package Database;

import Model.stationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class StationDB extends MainConnection {

    // Insert new station
    public static boolean insertStation(String stationName, String locGov, String locCity, String locRegion, int vehicleCapacity, boolean isPrimary) {
        String query = "INSERT INTO station (Staion_Name, Loc_Gov, Loc_city, Loc_region, Vehicle_capacity, isPrimary) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, stationName);
            ps.setString(2, locGov);
            ps.setString(3, locCity);
            ps.setString(4, locRegion);
            ps.setInt(5, vehicleCapacity);
            ps.setBoolean(6, isPrimary);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Insert and return generated Station_id
    public static int addStationGetKey(String stationName, String locGov, String locCity, String locRegion, int vehicleCapacity, boolean isPrimary) {
        String query = "INSERT INTO station (Staion_Name, Loc_Gov, Loc_city, Loc_region, Vehicle_capacity, isPrimary) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, stationName);
            ps.setString(2, locGov);
            ps.setString(3, locCity);
            ps.setString(4, locRegion);
            ps.setInt(5, vehicleCapacity);
            ps.setBoolean(6, isPrimary);

            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Get station by ID
    public static ResultSet getStationById(int stationId) {
        String query = "SELECT * FROM station WHERE Station_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, stationId);
            return ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update station
    public static boolean updateStation(stationModel station) {
        String query = "UPDATE station SET Staion_Name = ?, Loc_Gov = ?, Loc_city = ?, Loc_region = ?, Vehicle_capacity = ?, isPrimary = ? WHERE Station_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, station.getStationName());
            ps.setString(2, station.getLocGov());
            ps.setString(3, station.getLocCity());
            ps.setString(4, station.getLocRegion());
            ps.setInt(5, station.getVehicleCapacity());
            ps.setBoolean(6, station.isPrimary());
            ps.setInt(7, station.getStationId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete station
    public static boolean deleteStation(int stationId) {
        String query = "DELETE FROM station WHERE Station_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, stationId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all stations as ObservableList
    public static ObservableList<stationModel> getStations() {
        setConnection();
        ObservableList<stationModel> list = FXCollections.observableArrayList();

        try {
            PreparedStatement stmt = Con.prepareStatement("SELECT * FROM station");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new stationModel(
                        rs.getInt("Station_id"),
                        rs.getString("Staion_Name"),
                        rs.getString("Loc_Gov"),
                        rs.getString("Loc_city"),
                        rs.getString("Loc_region"),
                        rs.getInt("Vehicle_capacity"),
                        rs.getBoolean("isPrimary")
                ));
            }
            Con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Get primary station
    public static stationModel getPrimaryStation() {
        String query = "SELECT * FROM station WHERE isPrimary = true LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new stationModel(
                        rs.getInt("Station_id"),
                        rs.getString("Staion_Name"),
                        rs.getString("Loc_Gov"),
                        rs.getString("Loc_city"),
                        rs.getString("Loc_region"),
                        rs.getInt("Vehicle_capacity"),
                        rs.getBoolean("isPrimary")
                );
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
