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

    
    public static Connection getConnection(){
        setConnection();
        return Con;
    }
}
  