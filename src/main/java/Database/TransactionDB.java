package Database;

import Model.TransactionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDB extends MainConnection{

    // تسجيل عملية جديدة
    public static long recordTransaction(int vehicleId, int stationId, int lineNumber, String type, LocalDateTime timestamp) throws SQLException {
        String query = "INSERT INTO transactions (Vehicle_id, Station_id, inline_no, type, timestamp) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, vehicleId);
            stmt.setInt(2, stationId);
            stmt.setInt(3, lineNumber);
            stmt.setString(4, type);
            stmt.setTimestamp(5, Timestamp.valueOf(timestamp));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        }

        return -1; // فشل في الإدراج
    }

    // الحصول على موضع الطابور التالي لخط
    public static int getNextQueuePosition(int stationId, int lineNumber) throws SQLException {
        String query =
                "SELECT COUNT(*) + 1 AS next_position FROM transactions " +
                        "WHERE Station_id = ? AND inline_no = ? AND type = 'enter' " +
                        "AND NOT EXISTS (SELECT 1 FROM transactions t2 WHERE t2.Vehicle_id = transactions.Vehicle_id AND t2.type = 'exit' AND t2.timestamp > transactions.timestamp)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stationId);
            stmt.setInt(2, lineNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("next_position");
            }
        }

        return 1; // الافتراضي إلى 1 إذا لم توجد إدخالات
    }

    // الحصول على جميع معاملات الطابور النشطة (المركبات في المحطة)
    public static ObservableList<TransactionModel> getActiveQueueTransactions(int stationId) throws SQLException {
        ObservableList<TransactionModel> transactions = FXCollections.observableArrayList();

        String query =
                "SELECT t.*, v.palette_number, u.full_name FROM transactions t " +
                        "JOIN vehicle v ON t.Vehicle_id = v.Vehicle_id " +
                        "JOIN users u ON v.user_id = u.user_id " +
                        "WHERE t.Station_id = ? AND t.type = 'enter' " +
                        "AND NOT EXISTS (SELECT 1 FROM transactions t2 WHERE t2.Vehicle_id = t.Vehicle_id AND t2.type = 'exit' AND t2.timestamp > t.timestamp) " +
                        "ORDER BY t.inline_no, t.timestamp";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(new TransactionModel(
                        rs.getInt("Transaction_id"),
                        rs.getInt("Vehicle_id"),
                        rs.getInt("Station_id"),
                        rs.getInt("inline_no"),
                        rs.getString("type"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getString("palette_number"),
                        rs.getString("full_name")
                ));
            }
        }

        return transactions;
    }

    // الحصول على معاملات الطابور النشطة مصفاة حسب رقم الخط
    public static ObservableList<TransactionModel> getActiveQueueTransactionsByLine(int stationId, int lineNumber) throws SQLException {
        // التنفيذ مشابه للطريقة السابقة مع إضافة شرط الخط
        // يُنفَّذ حسب المتطلبات
        return FXCollections.observableArrayList();
    }

    // الحصول على المعاملات الحديثة
    public static ObservableList<TransactionModel> getRecentTransactions(int stationId, int limit) throws SQLException {
        // استرجاع أحدث المعاملات في المحطة
        // يُنفَّذ حسب المتطلبات
        return FXCollections.observableArrayList();
    }

    // الحصول على المعاملات حسب التاريخ
    public static ObservableList<TransactionModel> getTransactionsByDate(int stationId, LocalDate date) throws SQLException {
        // تصفية المعاملات حسب تاريخ محدد
        // يُنفَّذ حسب المتطلبات
        return FXCollections.observableArrayList();
    }

    // الحصول على رقم خط المركبة الحالي
    public static int getVehicleLineNumber(int vehicleId, int stationId) throws SQLException {
        String query =
                "SELECT inline_no FROM transactions " +
                        "WHERE Vehicle_id = ? AND Station_id = ? AND type = 'enter' " +
                        "ORDER BY timestamp DESC LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            stmt.setInt(2, stationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("inline_no");
            }
        }

        return -1; // غير موجود
    }
}