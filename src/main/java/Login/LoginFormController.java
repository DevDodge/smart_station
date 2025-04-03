
package Login;

import Options.MainController;
import Options.MyOptions;
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
        String user = username.getText(),
                pass = Password.getText();
        Scene.OpenScene("/FXMLs/HomePage.fxml");

    }



    
    @FXML
    void gamesAction(ActionEvent event) throws IOException {
    }

    
    @FXML
    void passFocus(ActionEvent event) {
        Password.requestFocus();
    }
}
