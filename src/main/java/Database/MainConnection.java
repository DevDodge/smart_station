package Database;

import Options.HardwareData;
import Options.MyOptions;

import java.sql.*;


public class MainConnection {
    public static String url = "";
    public static String dbName = "center";
    public static String dbUser = "root";
    public static String dbPass = "Mohamed.may.12";//root
    public static Connection Con;
    public static void setConnection() {

        try {
            url = "jdbc:mysql://localhost:3306/"
                    + dbName + "?useUnicode=true&characterEncoding="
                    + "UTF-8";
//                    + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Con = DriverManager.getConnection(url, dbUser, dbPass);
        } catch (Exception ex) {
            MyOptions.showCustomMessage("Database Failure","Open Xampp Or MySQL Workbench to reconnect your database");
            ex.printStackTrace();
        }//CommunicationsException #2195
    }

    /**
     * Establishes a connection to the database and returns the active Connection object.
     * This method internally calls {@code setConnection()} to set up the connection if not already done.
     *
     * @return an active {@link Connection} object connected to the specified database, or {@code null} if the connection cannot be established.
     */
    public static Connection getConnection(){
        setConnection();
        return Con;
    }
    
    /**
     * Verifies if the current hardware data exists in the database by comparing
     * the processor ID, baseboard serial, and BIOS serial number retrieved
     * from the local system with the stored records in the "controlhd" table.
     *
     * @return true if the hardware data matches records in the database,
     *         false otherwise
     */
    public static boolean checkHardWareData() {
        String hardwareData[] = HardwareData.getHardwareData();
        try {

            setConnection();
            PreparedStatement ps;
            ps = Con.prepareStatement("select * from controlhd where "
                    + "PP ='" + hardwareData[0] + "' AND "
                    + "BB ='" + hardwareData[2] + "' AND "
                    + "Bs='" + hardwareData[1] + "';");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * Inserts device hardware data into the `controlhd` database table.
     *
     * @param ProcessorID The processor ID to be stored in the `PP` column.
     * @param BIOSid The BIOS ID to be stored in the `BS` column.
     * @param BaseboardID The baseboard ID to be stored in the `BB` column.
     * @return true if the data was successfully inserted into the database, false otherwise.
     */
    public static boolean addDeviceHardwareData(String ProcessorID, String BIOSid, String BaseboardID) {
        try {
            setConnection();
            PreparedStatement stmt;
            stmt = Con.prepareStatement("INSERT INTO `controlhd` (`PP`,`BB`,`BS`) VALUES (?,?,?)");
            stmt.setString(1, ProcessorID);
            stmt.setString(2, BaseboardID);
            stmt.setString(3, BIOSid);
            stmt.executeUpdate();
            Con.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * Adds hardware data retrieved from the system to the database.
     * It extracts the hardware data (Processor ID, Baseboard Serial Number, and BIOS Serial Number)
     * and inserts it into the `controlhd` table in the database.
     *
     * @return true if the hardware data is successfully added to the database, false otherwise
     */
    public static boolean AddWareData() {
        String hardwareData[] = HardwareData.getHardwareData();
        try {
            setConnection();
            PreparedStatement ps;
            ps = Con.prepareStatement("INSERT INTO `controlhd`(`PP`, `BS`, `BB`) VALUES (?,?,?)");
            ps.setString(1, hardwareData[0]);
            ps.setString(2, hardwareData[1]);
            ps.setString(3, hardwareData[2]);
            if (ps.executeUpdate() > 0) {
                Con.close();
                return true;
            }

            Con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
  