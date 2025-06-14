package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Database.MainConnection.getConnection;

public class TransportLinesDB {

    // الحصول على جميع خطوط النقل لمحطة
    public static ObservableList<String> getTransportLinesForStation(int stationId) throws SQLException {
        ObservableList<String> lines = FXCollections.observableArrayList();

        String query =
                "SELECT DISTINCT Vehicle_id FROM transport_lines " +
                        "WHERE From_Station = ? OR To_Station = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stationId);
            stmt.setInt(2, stationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lines.add(String.valueOf(rs.getInt("Vehicle_id")));
            }
        }

        return lines;
    }
}