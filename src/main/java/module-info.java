module com.example.projectwith {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.gemsfx;
    requires org.apache.commons.validator;

    requires java.sql;

    opens Model to javafx.base;



    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;
    requires org.kordamp.ikonli.dashicons;
    requires org.controlsfx.controls;
    
    requires mysql.connector.j;
    requires webcam.capture;
    requires javafx.swing;
    requires java.management;
    requires java.net.http;

    opens com.example to javafx.fxml;
    opens Options to javafx.fxml; // This allows the 'Options' package to be accessible by FXML loader
    opens com.main to javafx.graphics;
    opens Controllers to javafx.fxml;
    exports com.example;


}
