module com.example.projectwith {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.gemsfx;
    requires org.apache.commons.validator;

    requires java.sql;
    requires java.mail;
    requires jasperreports;

    requires json;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;
    requires org.kordamp.ikonli.dashicons;
    requires org.controlsfx.controls;

    requires java.desktop;
    requires static lombok;
    requires mysql.connector.j;
    
    
    opens com.example to javafx.fxml;
    opens Options to javafx.fxml; // This allows the 'Options' package to be accessible by FXML loader
    opens Login to javafx.fxml;
    opens com.main to javafx.graphics;
    exports com.example;
//    exports Games.GameSessions;  // Export the package to be accessible by other modules


}
