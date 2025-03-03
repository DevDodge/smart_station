    package Options;

//    import de.jensd.fx.glyphs.fontawesome.*;

    import javafx.application.Platform;
    import javafx.fxml.FXML;
    import javafx.geometry.Pos;
    import javafx.scene.control.TextField;
    import javafx.scene.image.ImageView;
    import javafx.scene.input.MouseEvent;
    import javafx.util.Duration;
    import org.controlsfx.control.Notifications;
    import org.kordamp.ikonli.javafx.FontIcon;

    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.Date;

    import static Options.Scene.OpenSceneFullScreenWithSize;
    import static Options.Scene.closeScene;

public class MainController {

    /**
     * Represents the number of seconds that have passed since a specific event or action.
     * This variable is used to track elapsed time within the application.
     */
    private int SecPassed = 0;
    /**
     * A private instance of the Thread class used to manage and handle a clock functionality
     * within the application. This thread is likely used for time-related operations or
     * tasks requiring periodic updates or background execution within the MainController class.
     */
    private Thread clock;
    /**
     * A static ImageView variable representing a GIF element in the user interface.
     * It is used to display animated graphical content in the application.
     * Typically associated with visual feedback or decorative animations.
     */
    public static ImageView GIF;
    /**
     * A static TextField instance used to handle or display code-related input.
     * This field can be utilized across various methods within the MainController class.
     */
    public static TextField codeField;
    /**
     * Represents the state of displaying a background GIF animation.
     * When true, the background GIF is enabled; when false, it is disabled.
     */
    private boolean BckGif = false;

    /**
     * Manages a background thread responsible for performing periodic tasks
     * such as showing and closing scenes, exporting a database, and exiting the system.
     * This method initializes and starts a thread that executes tasks at intervals
     * of one second using a loop.
     *
     * Tasks performed by the thread:
     * - On the first iteration, it opens a backup form and tracks the passage of time.
     * - After two iterations, it attempts to export the database to a specified location.
     *   If the export is successful, a success message is displayed; otherwise, the system exits.
     * - After seven iterations, the backup form is closed and the system exits.
     *
     * The thread utilizes `Platform.runLater` for JavaFX operations to ensure
     * that the UI updates occur on the JavaFX application thread.
     *
     * The method also handles thread interruptions during the sleep periods.
     */
    private void ThreadFunc() {
        clock = new Thread() {
            public void run() {
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!BckGif) {
                                Scene.closeScene(closeBtn);
                                // show backup Form
//                                Scene.OpenScene("/FXMLs/SubForms/BackupGIF.fxml");
                                SecPassed++;
                                BckGif = true;
                            }
                            if (SecPassed == 2) {
                                Date now = new Date();
                                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                                String ret = MyOptions.exportDB(
                                        "\\Backup\\" + f.format(now) + ".sql");
                                if (ret.equals("Success")) {
                                    // close backup Form
                                    Scene.closeScene(GIF);
                                    Scene.OpenScene("/SubForms/Message/BackupSuccess.fxml");
                                    SecPassed++;
                                } else {
                                    // close system
                                    System.exit(0);
                                }
                            } else if (SecPassed == 7) {
                                // close backup Form
                                Scene.closeScene(GIF);
                                // close system
                                System.exit(0);
                            } else {
                                SecPassed++;
                            }
                        }
                    });
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        clock.start();
    }



    /**
     * The FontIcon representing the maximize button in the application's UI.
     * This button, when interacted with, triggers the maximize functionality of the application window.
     * It is associated with the maximizeProgram method to handle its behavior during user interaction.
     */
    @FXML
    private FontIcon maximizedBTN, /**
     * The minimizeBtn field represents the button used to minimize the application window.
     * It is typically associated with an action that reduces the application to the taskbar.
     * This field is likely linked to an event handler that defines the minimize behavior.
     */
    minimizeBtn, /**
     * Represents a button to close the application window or terminate the program.
     * Typically linked to an event handler that performs the exit operation.
     */
    closeBtn,maximizeBtn, /**
     * Represents the back arrow functionality or UI component within the MainController class.
     * This variable is likely tied to a graphical user interface element used for navigation purposes.
     * Its specific behavior could be associated with methods that handle returning to previous screens
     * or menus when a user interacts with the back arrow, such as backToStudentMain or BackToMain.
     */
    backArrow;

    /**
     * Handles the event of exiting the program. This method is triggered
     * when the associated UI element is interacted with by the user.
     *
     * @param event the MouseEvent that occurs when the exit button is clicked
     */
    @FXML
    void exitProgram(MouseEvent event) {
        Scene.closeScene(closeBtn);
    }

    @FXML
    void maximizeScreen(MouseEvent event) {
        Scene.maximizeScene(maximizeBtn);
    }

    /**
     * Handles the action of navigating back to the student main form when triggered by a mouse event.
     *
     * @param event The MouseEvent triggering this navigation.
     */
    @FXML
    void backToStudentMain(MouseEvent event) {
        Scene.closeScene(closeBtn);
        Scene.OpenSceneFullScreenWithSize("/Student/MainForm/MainForm.fxml", 1000, 1200);
    }

    /**
     * Handles the action to navigate back to the main form of the application.
     * This method closes the current scene and opens the main form with specified dimensions in full-screen mode.
     *
     * @param event  the MouseEvent triggered by the user interaction, typically a mouse click
     * @throws IOException  if loading the specified FXML resource fails
     */
    @FXML
    void BackToMain(MouseEvent event) throws IOException {
        closeScene(closeBtn);
        OpenSceneFullScreenWithSize("/FXMLs/Teacher/MainForm/MainForm.fxml", 1000, 1200);
    }

    /**
     * Handles the system exit process when a mouse event is triggered.
     * This method initiates a sequence of operations to safely exit the system
     * by invoking the ThreadFunc method, which manages the necessary background
     * tasks and cleanup operations.
     *
     * @param event the MouseEvent triggered when the user interacts with the UI
     *              to exit the system
     */
    @FXML
    void exitSystem(MouseEvent event) {
        ThreadFunc();
        Scene.closeScene(closeBtn);
        System.exit(0);
    }

    /**
     * Minimizes the current application window when the minimize button is clicked.
     *
     * @param event the MouseEvent that triggers the minimize action
     */
    @FXML
    void minimizeProgram(MouseEvent event) {
        Scene.minimizeScene(minimizeBtn);
    }

    /**
     * Maximizes the current program window when triggered by a mouse event.
     *
     * @param event the MouseEvent that triggers the maximize action
     */
    @FXML
    void maximizeProgram(MouseEvent event) {
        Scene.maximizeScene(maximizedBTN);
    }
    /**
     * Displays a notification to inform the user that required input data is incomplete.
     * The notification includes a title, a message, and a graphic, and it is shown
     * in the bottom-right corner of the screen for a limited duration.
     */
    public void showNullMessage(){
        ImageView icon = new ImageView(getClass().getResource("/images/Notificaiton4.png").toExternalForm());

        Notifications.create()
                .title("بيانات غير كاملة !!")
                .text("الرجاء ادخال البيانات كاملة")
                .graphic(icon)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(6))
                .show();
    }
}
