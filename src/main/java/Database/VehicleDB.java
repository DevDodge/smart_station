package Database;

import Model.VehicleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class VehicleDB extends MainConnection {

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

    // === إضافة الطرق الجديدة المطلوبة ===

    /**
     * إدراج مركبة جديدة في قاعدة البيانات
     *
     * @param stationId المحطة الرئيسية للمركبة
     * @param carLicense مسار صورة رخصة المركبة
     * @param plateNumber رقم لوحة المركبة
     * @param inStation حالة وجود المركبة في المحطة
     * @param userId معرف السائق المرتبط بالمركبة
     * @return true إذا تم الإدراج بنجاح، false في حالة الفشل
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static boolean insertVehicle(int stationId, String carLicense, String plateNumber, boolean inStation, int userId) throws SQLException {
        String query = "INSERT INTO vehicle (Station_id, car_liscence, palette_number, inStation, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stationId);
            stmt.setString(2, carLicense);
            stmt.setString(3, plateNumber);
            stmt.setBoolean(4, inStation);
            stmt.setInt(5, userId);

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * إدراج مركبة جديدة وإرجاع المعرف المولد
     *
     * @param stationId المحطة الرئيسية للمركبة
     * @param carLicense مسار صورة رخصة المركبة
     * @param plateNumber رقم لوحة المركبة
     * @param inStation حالة وجود المركبة في المحطة
     * @param userId معرف السائق المرتبط بالمركبة
     * @return معرف المركبة المولد تلقائيًا، أو -1 في حالة الفشل
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static int insertVehicleGetId(int stationId, String carLicense, String plateNumber, boolean inStation, int userId) throws SQLException {
        String query = "INSERT INTO vehicle (Station_id, car_liscence, palette_number, inStation, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, stationId);
            stmt.setString(2, carLicense);
            stmt.setString(3, plateNumber);
            stmt.setBoolean(4, inStation);
            stmt.setInt(5, userId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            return -1;
        }
    }

    /**
     * تحديث بيانات مركبة موجودة
     *
     * @param vehicle كائن المركبة المحتوي على البيانات المحدثة
     * @return true إذا تم التحديث بنجاح، false في حالة الفشل
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static boolean updateVehicle(VehicleModel vehicle) throws SQLException {
        String query = "UPDATE vehicle SET Station_id = ?, car_liscence = ?, palette_number = ?, inStation = ?, user_id = ? WHERE Vehicle_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicle.getStationId());
            stmt.setString(2, vehicle.getCarLicense());
            stmt.setString(3, vehicle.getPlateNumber());
            stmt.setBoolean(4, vehicle.isInStation());
            stmt.setInt(5, vehicle.getUserId());
            stmt.setInt(6, vehicle.getVehicleId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * حذف مركبة من قاعدة البيانات
     *
     * @param vehicleId معرف المركبة المراد حذفها
     * @return true إذا تم الحذف بنجاح، false في حالة الفشل
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static boolean deleteVehicle(int vehicleId) throws SQLException {
        String query = "DELETE FROM vehicle WHERE Vehicle_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * الحصول على جميع المركبات
     *
     * @return قائمة بجميع المركبات
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static ObservableList<VehicleModel> getAllVehicles() throws SQLException {
        ObservableList<VehicleModel> vehicles = FXCollections.observableArrayList();
        String query = "SELECT * FROM vehicle";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vehicles.add(new VehicleModel(
                        rs.getInt("Vehicle_id"),
                        rs.getInt("Station_id"),
                        rs.getInt("user_id"),
                        rs.getString("car_liscence"),
                        rs.getString("palette_number"),
                        rs.getBoolean("inStation")
                ));
            }
        }

        return vehicles;
    }

    /**
     * الحصول على المركبات حسب المحطة
     *
     * @param stationId معرف المحطة
     * @return قائمة بالمركبات في المحطة المحددة
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static ObservableList<VehicleModel> getVehiclesByStation(int stationId) throws SQLException {
        ObservableList<VehicleModel> vehicles = FXCollections.observableArrayList();
        String query = "SELECT * FROM vehicle WHERE Station_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicles.add(new VehicleModel(
                        rs.getInt("Vehicle_id"),
                        rs.getInt("Station_id"),
                        rs.getInt("user_id"),
                        rs.getString("car_liscence"),
                        rs.getString("palette_number"),
                        rs.getBoolean("inStation")
                ));
            }
        }

        return vehicles;
    }

    /**
     * الحصول على المركبات حسب السائق
     *
     * @param userId معرف السائق
     * @return قائمة بالمركبات المرتبطة بالسائق المحدد
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static ObservableList<VehicleModel> getVehiclesByDriver(int userId) throws SQLException {
        ObservableList<VehicleModel> vehicles = FXCollections.observableArrayList();
        String query = "SELECT * FROM vehicle WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicles.add(new VehicleModel(
                        rs.getInt("Vehicle_id"),
                        rs.getInt("Station_id"),
                        rs.getInt("user_id"),
                        rs.getString("car_liscence"),
                        rs.getString("palette_number"),
                        rs.getBoolean("inStation")
                ));
            }
        }

        return vehicles;
    }

    /**
     * التحقق من وجود مركبة برقم لوحة محدد
     *
     * @param plateNumber رقم اللوحة
     * @return true إذا كان رقم اللوحة موجودًا بالفعل، false إذا كان متاحًا
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static boolean isPlateNumberExists(String plateNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM vehicle WHERE palette_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, plateNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }

    /**
     * الحصول على معلومات المركبة والسائق
     * يُرجع معلومات مجمعة عن المركبة والسائق والمحطة
     *
     * @param vehicleId معرف المركبة
     * @return ResultSet يحتوي على بيانات المركبة والسائق والمحطة
     * @throws SQLException في حالة حدوث خطأ في قاعدة البيانات
     */
    public static ResultSet getVehicleDetails(int vehicleId) throws SQLException {
        String query =
                "SELECT v.*, u.full_name, u.phone_number, s.Staion_Name " +
                        "FROM vehicle v " +
                        "JOIN users u ON v.user_id = u.user_id " +
                        "JOIN station s ON v.Station_id = s.Station_id " +
                        "WHERE v.Vehicle_id = ?";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, vehicleId);

        return stmt.executeQuery();
    }
}