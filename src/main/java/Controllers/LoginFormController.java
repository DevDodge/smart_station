
package Controllers;

import Options.MainController;
import Options.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Options.MyOptions.showCustomMessage;


public class LoginFormController extends MainController implements Initializable {

    
    @FXML
    private PasswordField Password;

    
    @FXML
    private TextField username;

    
    @FXML
    private FontIcon closeBtn;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }

    
    @FXML
    void loginAction(ActionEvent event) throws IOException {
        String user = username.getText().trim();
        String pass = Password.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            showCustomMessage("خطأ", "من فضلك ادخل اسم المستخدم و كلمة المرور"); // Error message in Arabic when no user is selected
            return;
        }

        try {
            if (Database.UserCredentialsDB.checkLogin(user, pass)) {
                Scene.OpenScene("/FXMLs/HomePage.fxml");
            } else {
                showCustomMessage("خطأ", "بيانات اسم المستخدم و كلمة المرور غير صحيحة"); // Error message in Arabic when no user is selected
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }



    
    @FXML
    void gamesAction(ActionEvent event) throws IOException {
    }

    
    @FXML
    void passFocus(ActionEvent event) {
        Password.requestFocus();
    }
}
