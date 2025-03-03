/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import Options.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class BackupSuccessController implements Initializable {

    /**
     * Represents an ImageView component in the FXML file, which is used
     * to display a success GIF animation as part of the user interface.
     * This field is linked to the FXML file via the @FXML annotation
     * and is used to handle and manipulate the success GIF functionality
     * in the BackupSuccessController class.
     */
    @FXML
    private ImageView successGIF;
    
    /**
     * Initializes the controller after the root element has been completely processed.
     * This method sets the `successGIF` ImageView instance from the current controller
     * to the static `GIF` variable in the `MainController` class.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb the ResourceBundle for localizing the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainController.GIF = this.successGIF;
        
    }    
    
}
