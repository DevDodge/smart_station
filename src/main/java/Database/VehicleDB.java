package Database;

import Model.VehicleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleDB extends MainConnection{

    // الحصول على مركبة بواسطة المعرف
    public static VehicleModel getVehicleById(int vehicleId) throws SQLException {
        String query = "SELECT * FROM vehicle WHERE Vehicle_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new VehicleModel(
                        rs.getInt("Vehicle_id"),
                        rs.getInt("Station_id"),
                        rs.getInt("user_id"),
                        rs.getString("car_liscence"),
                        rs.getString("palette_number"),
                        rs.getBoolean("inStation")
                );
            }
        }

        return null;
    }

    // الحصول على معرف المركبة بواسطة رقم اللوحة
    public static int getVehicleIdByPlate(String plateNumber) throws SQLException {
        String query = "SELECT Vehicle_id FROM vehicle WHERE palette_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, plateNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("Vehicle_id");
            }
        }

        return -1; // غير موجود
    }

    // الحصول على عدد المركبات داخل محطة
    public static int getVehiclesInStationCount(int stationId) throws SQLException {
        String query = "SELECT COUNT(*) FROM vehicle WHERE Station_id = ? AND inStation = 1";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    // تحديث حالة المركبة (داخل/خارج المحطة)
    public static boolean updateVehicleStatus(int vehicleId, boolean inStation) throws SQLException {
        String query = "UPDATE vehicle SET inStation = ? WHERE Vehicle_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, inStation);
            stmt.setInt(2, vehicleId);

            return stmt.executeUpdate() > 0;
        }
    }

    // الحصول على معلومات السائق لمركبة
    public static ResultSet getDriverInfoForVehicle(int vehicleId) throws SQLException {
        String query =
                "SELECT u.* FROM users u " +
                        "JOIN vehicle v ON u.user_id = v.user_id " +
                        "WHERE v.Vehicle_id = ?";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, vehicleId);

        return stmt.executeQuery();
    }
}