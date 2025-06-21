package Controllers;

import Options.MainController;
import Options.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController extends MainController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    void openScanCamera(ActionEvent event) {
        Scene.OpenSceneFullScreenWithSize("/FXMLs/TrackMovement.fxml",575,852 );
    }
    
    @FXML
    void openStationManagement(ActionEvent event) {
        Scene.OpenSceneFullScreenWithSize("/FXMLs/StationsManagement.fxml",575,852 );
    }

    @FXML
    void openUserManagement(ActionEvent event) {
        Scene.OpenSceneFullScreenWithSize("/FXMLs/usersManagement.fxml",575,852 );
    }
    @FXML
    void vehicleRegistrationAction(ActionEvent event) {
        Scene.OpenScene("/FXMLs/VehicleRegistration.fxml");
    }
    
}