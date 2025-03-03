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
    opens Login to javafx.fxml;   // If 'LoginForm.fxml' also requires reflection access
//    opens SubForms.Message to javafx.fxml;
//    opens HomePage to javafx.fxml;
//    opens center to javafx.fxml, javafx.graphics;
//    opens Teacher.Subjects to javafx.fxml;
//    opens Teacher.Appointements to javafx.fxml;
//    opens Teacher.Users to javafx.fxml;
//    opens Teacher.Assignment to javafx.fxml;
//    opens Teacher.Income to javafx.fxml;
//    opens Teacher.Income.DetailsPopUp to javafx.fxml;
//    opens Teacher.DeActiveStudent to javafx.fxml;
//    opens Teacher.Groups to javafx.fxml;
//    opens Teacher.Exams to javafx.fxml;
//    opens Teacher.Remember to javafx.fxml;
//    opens SystemFeatures to javafx.fxml;
//    opens TablesClasses to javafx.base, javafx.fxml;
//    opens Maintainence.Trial to javafx.fxml, java.base;
//    opens Student.AddStudent to javafx.fxml;
//    opens Student.Assignment to javafx.fxml;
//    opens Student.Analysis to javafx.fxml;
//    opens Student.StudentDegree to javafx.fxml;
//    opens Student.BarcodeGenerator to javafx.fxml;
//    opens Student.AssignmentDetails to javafx.fxml;
//    opens Student.Attendance to javafx.fxml;
//    opens Student.AttendanceDetails to javafx.fxml;
//    opens Student.Exam to javafx.fxml;
//    opens Student.ExamsDetails to javafx.fxml;
//    opens Student.IdGenerator to javafx.fxml;
//    opens Student.QuizDetails to javafx.fxml;
//    opens Student.SessionDetails to javafx.fxml;
//    opens Student.WalletTransactions to javafx.fxml;
//    opens Student.Whatsapp to javafx.fxml;
//    opens Student.Division to javafx.fxml;
//    opens Games.Questions to javafx.fxml;
//    opens Games.gettingTables to javafx.fxml;
//    opens Games.QuestionCategories to javafx.fxml;
//    opens Games.GameSessions to javafx.fxml;
//    opens Games.Classes to javafx.fxml, javafx.base;
//    opens Games to javafx.fxml, javafx.base;
//    opens Games.Game to javafx.fxml, javafx.base;
//    opens Animation to javafx.fxml, javafx.base, javafx.graphics;
    opens Options.PropertyItems to javafx.fxml, javafx.base, javafx.graphics;
    // Export the DemoTimer package to javafx.fxml

    // Opens the package if reflection or deep access is required
//    opens DemoTimer to javafx.fxml;

    exports com.example;
    exports Options.PropertyItems;  // Export the package to be accessible by other modules
//    exports Games.GameSessions;  // Export the package to be accessible by other modules


}
