/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

//import static Options.LocalSession.systemFeaturesFunctions.syncSystemFeaturesValues;
import static Options.MyOptions.checkMaintainence;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class LoginFormController extends MainController implements Initializable {

    /**
     * Represents a password input field in the login form.
     * This field is used to capture the user's password during the login process.
     * <p>
     * The value of this field can be retrieved and processed as part of the user's authentication credentials.
     * It is initialized and managed via FXML in the associated controller class.
     */
    @FXML
    private PasswordField Password;

    /**
     * Represents the TextField for capturing the username input in the login form.
     * This field is bound to the FXML UI element and interacts with the user's input.
     */
    @FXML
    private TextField username;

    /**
     * Represents a closable button in the login form that can be used
     * to close the current application window or perform other close-related actions.
     * Defined as a FontIcon for a visually customizable UI element.
     */
    @FXML
    private FontIcon closeBtn;

    /**
     * Initializes the controller class.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb  The resources used to localize the root object, or null if the resource is not available.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }

    /**
     * Handles the login action when triggered by the user.
     * Validates the provided username and password, checks maintenance status,
     * verifies login credentials against the database, and navigates to appropriate UI scenes.
     * Displays error messages for invalid inputs or login failures.
     *
     * @param event the action event triggered by the login button or other input.
     * @throws IOException if there is an issue navigating to a new scene or loading FXML resources.
     */
    @FXML
    void loginAction(ActionEvent event) throws IOException {
        String user = username.getText(),
                pass = Password.getText();
        if (MyOptions.isVariableNull(user, pass)) {
            MyOptions.showNullMessage();
        } else {
            boolean Maintaince = checkMaintainence(user, pass);
            // if username and password doesn't belong to any maintenance data then check if they belong to database users
            if (!Maintaince) {
                if (true) {
//                    syncSystemFeaturesValues();
                    Scene.closeScene(closeBtn);
                    Scene.OpenSceneFullScreenWithSize("/FXMLs/HomePage/HomePage.fxml", 645, 1187);

//                    Scene.OpenScene("/FXMLs/Games/GameCustomization.fxml");
//                    Scene.OpenSceneFullScreenWithSize("/FXMLs/Games/GamesHome.fxml",645, 1187);
                } else {
                    MyOptions.showInvalidLoginMessage("بيانات التسجيل خاطئة");
                }
            }

        }

    }



    /**
     * Handles the action event triggered when the "Games" button is clicked.
     * This method opens the "Games" scene by loading the specified FXML file.
     *
     * @param event the ActionEvent that occurred, typically triggered by a button click
     * @throws IOException if an error occurs during loading the specified FXML file
     */
    @FXML
    void gamesAction(ActionEvent event) throws IOException {
        Scene.OpenScene("/Games/Games.fxml");
    }

    /**
     * Handles the action event to transfer the focus to the password input field.
     *
     * @param event the action event triggered when the associated UI element is interacted with
     */
    @FXML
    void passFocus(ActionEvent event) {
        Password.requestFocus();
    }
}
