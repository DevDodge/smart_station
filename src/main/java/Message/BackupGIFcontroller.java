/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import Options.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Mohamed
 */
public class BackupGIFcontroller extends MainController implements Initializable {

    /**
     * Represents the ImageView node used to display an animated GIF within the UI.
     * This element is associated with the FXML "BackupGIF.fxml" layout file and is
     * utilized within the BackupGIFcontroller class for setting and displaying GIF animations.
     * The GIF element is primarily used for providing visual feedback to the user,
     * such as a success animation or a loading process.
     */
    @FXML
    private ImageView GIF;

    /**
     * Initializes the controller after the root element has been completely processed.
     * This method sets the static `GIF` field in the `MainController` class to the local `GIF` ImageView instance,
     * enabling the GIF to be shared or referenced across different parts of the application.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainController.GIF = this.GIF;
    }

    /**
     * Updates the ImageView component to display a "Check mark animation.gif".
     * The method retrieves the GIF file from the specified file path, creates
     * an Image object by converting the file path to a URI, and then sets this
     * Image object to the ImageView named "GIF".
     */
    public void setImageSuccess() {
        File file = new File("/images/Check mark animation.gif");
        Image image = new Image(file.toURI().toString());
        GIF.setImage(image);
    }

}
