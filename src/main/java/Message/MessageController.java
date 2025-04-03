package Message;

import Options.MainController;
import Options.Scene;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageController extends MainController implements Initializable {

    /**
     * This Label is used to display messages in the MessageController class.
     * It is a JavaFX UI element bound to the FXML component with `fx:id="MsjLbl"`.
     * The text in this Label is set through the `displayMsj` method and is intended to show
     * detailed or descriptive messages in the application interface.
     */
    @FXML
    public Label MsjLbl;

    /**
     * Represents a label in the user interface used to display warning or title messages.
     * This label is updated dynamically based on the context, such as displaying a title
     * for a message or a warning header.
     * It is associated with the FXML file and managed by the MessageController class.
     */
    @FXML
    private Label WarningTxt;

    /**
     * Represents a FontIcon component styled as a close button in the UI.
     *
     * It is used to trigger an action, typically to close a window or dialog.
     * The button is styled and configured via FXML and can be associated
     * with event handlers to perform specific actions, such as exiting a program.
     *
     * This component is defined in the Message.fxml file and linked to the
     * MessageController class for managing its behavior.
     */
    @FXML
    private FontIcon closeBtn;

    /**
     * The root container of the `MessageController` class.
     * This `StackPane` serves as the main layout for the scene
     * and incorporates all child nodes, including labels and icons.
     * It is responsible for handling key events such as the Escape key
     * to trigger the exit behavior and provides the base structure
     * for the associated FXML-defined UI.
     */
    @FXML
    private StackPane rootPane;

    
    /**
     * Handles the program exit event triggered by a keyboard key press.
     *
     * @param event the KeyEvent that triggers the exit action
     */
    @FXML
    void exit_Program(KeyEvent event) {
        Scene.closeScene(closeBtn);
    }

    /**
     * Handles a key event to check if the pressed key is Enter or Escape.
     * Exits the scene if the Escape key is pressed.
     *
     * @param event the KeyEvent triggered by a key press
     */
    @FXML
    void checkIfEnterorEscThenExit(KeyEvent event) {
 if (event.getCode() == KeyCode.ESCAPE) {
                Scene.closeScene(closeBtn);
            }
    }

    /**
     * Updates the labels in the message pane with the provided title and message text.
     *
     * @param title The text to be displayed on the title label (WarningTxt).
     * @param msj The text to be displayed on the message label (MsjLbl).
     */
    public void displayMsj(String title, String msj) {
        MsjLbl.setText(msj);
        WarningTxt.setText(title);
    }

    /**
     * Initializes the controller after its root element has been completely loaded.
     * Sets the initial focus to the label and adds an event handler to close the scene
     * when the "Escape" key is pressed.
     *
     * @param url the location of the FXML file that was used to initialize the object
     * @param resourceBundle the resources that were used to localize the object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MsjLbl.requestFocus();
         // Add an "Escape" key listener
        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Scene.closeScene(closeBtn);
            }
        });
    }
    
    /**
     * Retrieves the root pane of the message interface.
     *
     * @return The StackPane that serves as the root container for the interface elements.
     */
    public StackPane getRootPane() {
        return rootPane;
    }

    /**
     * Sets the root pane of the MessageController.
     *
     * @param rootPane the StackPane to be set as the root pane
     */
    public void setRootPane(StackPane rootPane) {
        this.rootPane = rootPane;
    }
}
