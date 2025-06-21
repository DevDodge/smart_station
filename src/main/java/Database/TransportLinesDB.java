package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Database.MainConnection.getConnection;

public class TransportLinesDB {

    // Get transport lines with destination station names
    public static ObservableList<String> getTransportLinesForStation(int stationId) throws SQLException {
        ObservableList<String> lines = FXCollections.observableArrayList();

        String query =
                "SELECT tl.Vehicle_id, s.Staion_Name as ToName " +
                        "FROM transport_lines tl " +
                        "JOIN station s ON tl.To_Station = s.Station_id " +
                        "WHERE tl.From_Station = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int vehicleId = rs.getInt("Vehicle_id");
                String toName = rs.getString("ToName");

                // Format: "1: Sayda Aisha"
                String lineInfo = vehicleId + ": " + toName;
                lines.add(lineInfo);
            }
        }

        return lines;
    }

    // Extract vehicle ID from formatted line string
    public static int extractVehicleIdFromLineString(String lineString) {
        if (lineString == null || lineString.isEmpty() || lineString.equals("--")) {
            return -1;
        }

        try {
            return Integer.parseInt(lineString.split(":")[0].trim());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }

    // Get destination name for a specific line/vehicle
    public static String getDestinationNameForLine(int vehicleId) throws SQLException {
        String query =
                "SELECT s.Staion_Name as ToName " +
                        "FROM transport_lines tl " +
                        "JOIN station s ON tl.To_Station = s.Station_id " +
                        "WHERE tl.Vehicle_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("ToName");
            }
        }

        return "غير معروف";
    }

}