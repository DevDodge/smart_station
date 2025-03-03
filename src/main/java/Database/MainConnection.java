package Database;

import Options.MyOptions;

import java.sql.*;


public class MainConnection {
    public static String url = "";
    public static String dbName = "smartstationdatabase";
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
}
  