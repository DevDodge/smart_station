package Options;

import com.example.HelloApplication;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Scene {

    /**
     * The `rootPane` is a static `StackPane` object intended to serve as the root container
     * for the graphical user interface components in a `Scene`. It acts as the fundamental
     * anchoring node for adding or managing child nodes in the scene graph.
     *
     * The `rootPane` is primarily responsible for:
     * 1. Providing a foundational structure for arranging graphical elements hierarchically.
     * 2. Supporting scene transitions or animations by acting as a central container.
     * 3. Allowing modifications like scaling, opacity changes, or adding/removing child nodes
     *    dynamically while maintaining a single point of reference.
     */
    public static StackPane rootPane;

    /**
     * Represents the horizontal offset value used in the `Scene` class for positioning or
     * translation processes during UI operations.
     *
     * Technical Details:
     * - This variable is a static field, meaning it is shared across all instances of the class.
     * - It is utilized to store or update the horizontal (x-axis) offset value for scene manipulation.
     * - The default value is initialized as `0`, indicating no horizontal offset initially.
     * - Its value may be modified dynamically during runtime to adapt scene movement or anchoring.
     */
    static double xOffeset = 0, /**
     * Represents the vertical offset value used in the positioning or layout of elements
     * within the scene. It is initialized to zero by default.
     *
     * The yOffset variable is typically used for adjusting or calculating the vertical displacement
     * of a component or stage in relation to its baseline position.
     *
     * Technical Details:
     * 1. `yOffeset` is declared as a mutable integer variable, initialized to zero.
     * 2. It holds the vertical offset value used during rendering or transformation logic.
     * 3. Changes to this variable's value directly impact how the dependent elements are vertically relocated.
     */
    yOffeset = 0;

    /**
     * Constructs a Scene object with a specified root pane.
     *
     * This constructor initializes the Scene instance by assigning the provided
     * StackPane object to the rootPane field. The rootPane serves as the base node
     * for the scene, allowing further customization and component additions.
     *
     * @param pane the StackPane to be used as the root pane of the scene
     */
    public Scene(StackPane pane) {
        rootPane = pane;
    }

    /**
     * Transitions the current scene to a faded state and then loads a new scene from the given URL.
     * This function creates and plays a fade-out effect before switching to the specified scene.
     *
     * @param url the URL of the scene to be loaded after the fade-out transition.
     *
     * The method workflow includes the following steps:
     * 1. Creates a {@link FadeTransition} object and configures it to target the `rootPane`.
     * 2. Sets the duration of the fade-out animation to 300 milliseconds and adjusts its opacity range from 1 to 0.5.
     * 3. Attaches an event handler to the transition's completion event that attempts to load the next scene using `loadNextScene`.
     * 4. Handles any {@link IOException} that may occur during scene loading by printing the exception stack trace.
     * 5. Initiates and plays the fade-out transition.
     */
    public static void fadeOutAndLoad(String url) {
        FadeTransition FT = new FadeTransition();
        FT.setDuration(Duration.millis(300));
        FT.setNode(rootPane);
        FT.setFromValue(1);
        FT.setToValue(0.5);
        FT.setOnFinished((ActionEvent event) -> {
            try {
                loadNextScene(url);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        FT.play();
    }

    /**
     * Fades out the current scene by decreasing its opacity to 0 over a fixed duration
     * and loads a new scene specified by the provided URL upon completion.
     *
     * The method uses a fade-out animation on the `rootPane` and, upon finishing, replaces
     * the current scene with the one provided in the URL. Any potential I/O exception
     * during scene loading is caught and logged.
     *
     * Technical steps:
     * 1. Creates a `FadeTransition` object for the fade effect, targeting the `rootPane`.
     * 2. Sets the initial (1.0) and final (0.0) opacity values for the fade transition.
     * 3. Plays the fade-out transition over 3 seconds.
     * 4. Defines an event handler using the `setOnFinished` method to execute once the fade transition is completed:
     *    a. Calls the `loadNextScene` method with the given URL to load and apply the next scene.
     *    b. Logs any IOException during scene loading using `printStackTrace`.
     *
     * @param url the path of the FXML file to load as the new scene after the fade-out animation
     */
    public void fadeOutAndLoadMain(String url) {
        FadeTransition FT = new FadeTransition(Duration.seconds(3), rootPane);

        FT.setFromValue(1.0);
        FT.setToValue(0);
        FT.play();
        FT.setOnFinished((ActionEvent event) -> {
            try {
                loadNextScene(url);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Loads and displays the next scene in the application.
     * This method initializes the new scene from an FXML file, sets it to the current stage,
     * and makes the stage draggable by mouse.
     *
     * @param url the relative path to the FXML file for the new scene
     * @throws IOException if the FXML file cannot be loaded
     *
     * Steps:
     * 1. The FXML file specified by the `url` parameter is loaded to create the root node of the scene.
     * 2. A new `javafx.scene.Scene` object is created using the loaded root node.
     * 3. The background of the scene is configured to be transparent.
     * 4. The current stage is retrieved from the `rootPane`'s current scene.
     * 5. The newly created scene is set on the current stage.
     * 6. The `setStageMovable` method is called to allow the stage to be repositioned using mouse drag events.
     */
    private static void loadNextScene(String url) throws IOException {
        Parent nextView = FXMLLoader.load(Scene.class.getResource(url));
        javafx.scene.Scene scene = new javafx.scene.Scene(nextView);
        scene.setFill(Color.TRANSPARENT);
        Stage curStage = (Stage) rootPane.getScene().getWindow();
        curStage.setScene(scene);
        setStageMovable(nextView, curStage);
    }

    /**
     * Executes a fade-in animation for the `rootPane` node, gradually increasing its opacity
     * from 0 to 1 over 300 milliseconds.
     *
     * The method implements the following steps:
     * 1. Sets the initial opacity of `rootPane` to 0, ensuring it starts fully transparent.
     * 2. Creates a `FadeTransition` instance configured to alter the opacity of `rootPane`.
     * 3. Configures the fade transition:
     *    - Duration is set to 300 milliseconds.
     *    - The target node of the transition is set to `rootPane`.
     *    - The starting opacity value for the fade (0).
     *    - The ending opacity value for the fade (1).
     * 4. Starts the fade transition using the `play()` method.
     */
    public static void SceneFadeIn() {
        rootPane.setOpacity(0);
        FadeTransition FT = new FadeTransition();
        FT.setDuration(Duration.millis(300));
        FT.setNode(rootPane);
        FT.setFromValue(0);
        FT.setToValue(1);
        FT.play();

    }

/**
 * Opens a new scene using the specified FXML file URL and displays it in an undecorated and transparent window.
 *
 * This method initializes a new stage, loads the FXML layout, creates a scene, and applies configurations like
 * transparency and icon setup to the stage. It also makes the stage draggable using a custom utility method.
 *
 * @param url the relative path to the FXML file that defines the layout of the scene
 *
 * The function logic is as follows:
 * 1. Loads the FXML file using FXMLLoader with the provided URL.
 * 2. Creates a new javafx.scene.Scene object with the loaded Parent layout.
 * 3. Sets up a new Stage with an undecorated and transparent style.
 * 4. Configures the scene to have a transparent fill and sets the stage's opacity to fully visible.
 * 5. Adds a custom application icon to the stage.
 * 6. Associates the created scene with the stage and displays it.
 * 7. Invokes the setStageMovable method to enable dragging of the stage based on mouse input.
 * 8. Catches and logs any IOException that occurs during FXML loading.
 */
//    public static void OpenScene(String url) {
//        try {
//            Parent root = FXMLLoader.load(Scene.class.getResource(url));
//            javafx.scene.Scene scene = new javafx.scene.Scene(root);
//            Stage primaryStage = new Stage();
//            primaryStage.initStyle(StageStyle.UNDECORATED);
//            scene.setFill(Color.TRANSPARENT);
//            primaryStage.initStyle(StageStyle.TRANSPARENT);  // Shows Stage Border Radius
//            primaryStage.setOpacity(1);
//            primaryStage.getIcons().add(new Image("/images/Logo.png"));
//            primaryStage.setScene(scene);
//            primaryStage.show();
//            setStageMovable(root, primaryStage);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
public static void OpenScene(String url) {
    try {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(url));
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);  // Shows Stage Border Radius
        primaryStage.setOpacity(1);
        primaryStage.getIcons().add(new Image(HelloApplication.class.getResource("/images/Logo.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.show();
        setStageMovable(root, primaryStage);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}


    /**
     * Opens a new scene on the screen based on the given URL. The method creates a new stage,
     * applies custom styling, and centers it on the screen.
     *
     * Steps:
     * 1. Loads the FXML file specified by the provided URL.
     * 2. Creates a new JavaFX Scene using the loaded FXML layout.
     * 3. Initializes a new Stage with the style set to UNDECORATED.
     * 4. Configures transparency for both the stage and the scene.
     * 5. Adds an application icon to the stage.
     * 6. Displays the stage and centers it on the screen.
     *
     * @param url the path to the FXML file, relative to the package location, used to define the scene layout.
     * @throws IOException if the specified FXML file cannot be loaded.
     */
    public static void OpenSceneOnScreen(String url) throws IOException {
        Parent root = FXMLLoader.load(Scene.class.getResource(url));
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);  // Shows Stage Border Radius
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(HelloApplication.class.getResource("/images/Logo.png").toExternalForm()));
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    /**
     * Loads a new scene from the specified FXML file, displays it in a new window,
     * and returns the FXMLLoader instance for further interaction with the scene's controller.
     *
     * The method handles the loading of the FXML file, initializes a new stage,
     * applies styling and properties to the stage, and makes the stage movable.
     *
     * @param url the relative path to the FXML file to be loaded.
     * @return the FXMLLoader used to load the FXML file, allowing access to the controller.
     * @throws RuntimeException if the FXML file cannot be loaded due to an IOException.
     */
    public static FXMLLoader OpenScene_getController(String url) {
        FXMLLoader loader = new FXMLLoader(Scene.class.getResource(url));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(new javafx.scene.Scene(root));
        primaryStage.getIcons().add(new Image(HelloApplication.class.getResource("/images/Logo.png").toExternalForm()));
        primaryStage.show();
        setStageMovable(root, primaryStage);
        return loader;
    }

    /**
     * Loads an FXML file, creates a new stage with the loaded scene, and displays it on the screen.
     * The method customizes the stage by making it undecorated, transparent, and centers it on the screen.
     *
     * @param url the path to the FXML file to be loaded. The URL is relative to the `Scene` class's resource location.
     * @return the FXMLLoader instance used to load the FXML file, which can be used to access the controller or manipulate the loaded content.
     *
     * Function Details:
     * 1. Initializes an FXMLLoader with the specified FXML file using `Scene.class.getResource(url)`.
     * 2. Attempts to load the FXML file into a Parent object. Throws a `RuntimeException` if an IOException occurs during loading.
     * 3. Creates a new Scene object using the loaded Parent root.
     * 4. Configures and initializes a new Stage with the following settings:
     *    - The window's title is set (currently empty).
     *    - The stage style is set to UNDECORATED and TRANSPARENT multiple times.
     * 5. Loads an application icon and assigns it to the stage.
     * 6. Displays the stage, ensuring that it is centered on the screen.
     * 7. Returns the initialized FXMLLoader for further use.
     */
    public static FXMLLoader OpenSceneOnScreen_getController(String url) {
        FXMLLoader loader = new FXMLLoader(Scene.class.getResource(url));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);  // Shows Stage Border Radius
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/images/Logo.png"));
        primaryStage.show();
        primaryStage.centerOnScreen();
        return loader;
    }

    /**
     * Loads and initializes a scene from the specified FXML file, setting up a new JavaFX stage
     * with predefined properties, and returns the FXMLLoader for additional control or access to
     * the FXML controller.
     *
     * This method performs the following steps:
     * 1. Constructs an FXMLLoader using the provided FXML file path.
     * 2. Loads the FXML into a Parent object while handling potential IO exceptions.
     * 3. Initializes a new Stage and sets its properties:
     *    - Title is set as empty.
     *    - Style is set as UNDECORATED and TRANSPARENT.
     *    - Adds a scene loaded from the Parent object.
     *    - Sets a default application icon.
     * 4. Makes the stage draggable by calling a utility function `setStageMovable`.
     * 5. Returns the FXMLLoader object.
     *
     * @param url the path to the FXML file to be loaded as a scene.
     * @return the FXMLLoader instance used to load the FXML, for accessing the loaded controller or resources.
     * @throws RuntimeException if there is an IOException while loading the FXML file.
     */
    public static FXMLLoader getSceneController(String url) {
        FXMLLoader loader = new FXMLLoader(Scene.class.getResource(url));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(new javafx.scene.Scene(root));
        primaryStage.getIcons().add(new Image("/images/Logo.png"));
        setStageMovable(root, primaryStage);
        return loader;
    }

    /**
     * Opens a new scene in a separate stage with specified dimensions and returns the FXMLLoader instance
     * associated with the loaded FXML file.
     *
     * This method initializes a new `Stage` and sets its minimum height and width according to the provided parameters.
     * It loads the specified FXML file, creates a scene with it, and applies specific stage configurations
     * such as transparency, custom icons, and user-defined window movability. The scene is then displayed.
     *
     * @param url The relative path to the FXML file to be loaded.
     * @param height The minimum height of the stage.
     * @param width The minimum width of the stage.
     * @return The `FXMLLoader` instance created for loading the specified FXML file. This can be used to access
     *         the controller class associated with the loaded FXML, if needed.
     * @throws IOException If loading the FXML file fails, this exception is thrown.
     */
    public static FXMLLoader OpenSceneWithSize_getController(String url, int height, int width) throws IOException {
        FXMLLoader loader = new FXMLLoader(Scene.class.getResource(url));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(new javafx.scene.Scene(root));
        primaryStage.getIcons().add(new Image("/images/Logo.png"));
        primaryStage.show();
        setStageMovable(root, primaryStage);
        return loader;
    }

    /**
     * Opens a new scene in full screen mode with specified height and width,
     * and returns the FXMLLoader instance associated with the loaded scene.
     *
     * The method initializes a new {@link Stage}, sets its dimensions, styles,
     * and ensures it is movable by invoking the helper method `setStageMovable`.
     * The method also disables the full screen exit hint for a smoother user experience.
     *
     * @param url the resource URL of the FXML file to load.
     * @param height the minimum height of the stage.
     * @param width the minimum width of the stage.
     * @return the FXMLLoader instance used to load the FXML file, allowing further controller interaction.
     * @throws RuntimeException if the FXML file cannot be loaded.
     */
    public static FXMLLoader OpenScene_FullSize_WithSize_getController(String url, int height, int width) {
        FXMLLoader loader = new FXMLLoader(Scene.class.getResource(url));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage primaryStage = new Stage();
        primaryStage.setMinHeight(height);
        primaryStage.setTitle("");
        primaryStage.setMinWidth(width);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); // Hide the exit hint message
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(new javafx.scene.Scene(root));
        primaryStage.getIcons().add(new Image("/images/Logo.png"));
        primaryStage.show();
        setStageMovable(root, primaryStage);
        return loader;
    }

    /**
     * Opens the main scene of the application in a new, customized stage with specific configurations.
     * This method loads the FXML file specified by the given URL and applies styling and behavior for the scene and stage.
     *
     * @param url The URL path to the FXML file that defines the layout of the scene.
     * @throws IOException If the specified FXML file cannot be loaded or found.
     *
     * Technical Workflow:
     * 1. Loads the FXML file as a `Parent` object using the provided `url`.
     * 2. Creates a new `Scene` object and associates it with the loaded root layout.
     * 3. Initializes a new `Stage` object and applies an undecorated style to it.
     * 4. Configures the scene and stage to use transparent backgrounds and removes window decorations.
     * 5. Sets an empty title for the stage and applies a custom icon to the window.
     * 6. Sets the scene to the stage and displays the stage.
     * 7. Makes the stage movable by calling the helper method `setStageMovable`, passing the root layout and stage.
     */
    public static void OpenMainScene(String url) throws IOException {
        Parent root = FXMLLoader.load(Scene.class.getResource(url));
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT); // Shows Stage Border Radius
        primaryStage.setTitle("");
        primaryStage.getIcons().add(new Image(HelloApplication.class.getResource("/images/Logo.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.show();
        setStageMovable(root, primaryStage);
    }

    /**
     * Opens a new stage (scene) with a specified FXML file, sets its properties, and displays it
     * at a given (x, y) screen position. The window is designed to be undecorated and fully transparent.
     * Additionally, it allows the stage to be movable by dragging the mouse.
     *
     * @param url The relative path to the FXML file used for loading the scene.
     * @param x The horizontal position (x-coordinate) where the stage should appear on the screen.
     * @param y The vertical position (y-coordinate) where the stage should appear on the screen.
     * @throws IOException If there is an error loading the FXML file from the specified URL.
     *
     * Technical Details:
     * 1. Loads the FXML file to create the root element of the scene.
     * 2. Creates a new `javafx.scene.Scene` object with the loaded root element.
     * 3. Initializes a new `Stage` object with a transparent and undecorated style.
     * 4. Sets the stage's title, icon, and the created scene.
     * 5. Positions the stage at the specified (x, y) coordinates on the screen.
     * 6. Makes the stage movable by implementing mouse press and drag events using `setStageMovable`.
     * 7. Displays the stage using `primaryStage.show()`.
     */
    public static void OpenDuckScene(String url, double x, double y) throws IOException {
        Parent root = FXMLLoader.load(Scene.class.getResource(url));
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT); // Shows Stage Border Radius
        primaryStage.setTitle("");
        primaryStage.getIcons().add(new Image("/images/Logo.png"));
        primaryStage.setScene(scene);

        // Set the stage's x and y coordinates
        primaryStage.setX(x);
        primaryStage.setY(y);  
        
        
        primaryStage.show();
        setStageMovable(root, primaryStage);
    }

    /**
     * Minimizes the current window (stage) associated with the given button.
     * This method retrieves the current application stage from the button's
     * scene and sets it to a minimized (iconified) state.
     *
     * @param btn the FontIcon button whose associated scene's window will be minimized
     *            This button must be linked to a valid scene and stage.
     *
     * Technical steps:
     * 1. Retrieve the scene associated with the specified button.
     * 2. Access the stage associated with the scene.
     * 3. Call `setIconified(true)` to minimize the stage.
     */
    public static void minimizeScene(FontIcon btn) {
        Stage Stage = (Stage) btn.getScene().getWindow();
        Stage.setIconified(true);
    }

    /**
     * Maximizes the current scene to full-screen mode.
     *
     * This method retrieves the current stage from the window associated with
     * the provided FontIcon's scene and sets the stage to full-screen mode.
     *
     * Technical steps:
     * 1. Access the scene associated with the provided FontIcon.
     * 2. Retrieve the window (Stage) from the scene.
     * 3. Set the Stage's full-screen property to true, enabling full-screen mode.
     *
     * @param btn The FontIcon element from which the scene and stage are derived.
     */
    public static void maximizeScene(FontIcon btn) {
        Stage Stage = (Stage) btn.getScene().getWindow();
        Stage.setFullScreen(true);
    }

    /**
     * Minimizes the window (stage) associated with the given ImageView button.
     *
     * This method is used to programmatically minimize the JavaFX Stage (window)
     * in which the provided ImageView resides. It retrieves the current Stage
     * from the ImageView's scene and sets its iconified property to true,
     * effectively minimizing the window.
     *
     * @param btn an ImageView instance that resides within the scene whose window is to be minimized.
     */
    public static void minimizeScene(ImageView btn) {
        Stage Stage = (Stage) btn.getScene().getWindow();
        Stage.setIconified(true);
    }

    /**
     * Closes the window associated with the FontIcon provided as a parameter.
     * This method retrieves the stage (window) from the FontIcon's scene
     * and closes it, effectively terminating the UI window.
     *
     * The following operations are performed:
     * 1. Accesses the FontIcon's current scene using `btn.getScene()`.
     * 2. Retrieves the window (Stage) associated with the scene via `getWindow()`.
     * 3. Closes the Stage using the `close()` method.
     *
     * @param btn A FontIcon object that is linked to the scene of the window to be closed.
     */
    public static void closeScene(FontIcon btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the current stage or window associated with the given ImageView.
     *
     * This method retrieves the current scene associated with the ImageView, accesses
     * its parent window (Stage), and invokes the `close()` method on the Stage
     * to terminate the window.
     *
     * @param closeBtn the ImageView component whose associated scene's stage will be closed
     */
    public static void closeScene(ImageView closeBtn) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();

    }

    /**
     * Closes the current scene associated with the provided ComboBox.
     * This method retrieves the Scene linked to the given ComboBox,
     * extracts the Stage associated with the Scene, and closes the Stage.
     *
     * @param combo the ComboBox used to identify the scene and window to be closed
     */
    public static void closeScene(ComboBox combo) {
        Stage stage = (Stage) combo.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the current scene associated with the provided button.
     *
     * This method retrieves the current window (Stage) from the button's scene
     * and invokes the `close()` method on the stage to terminate the display.
     *
     * Steps:
     * 1. Get the scene associated with the given button.
     * 2. Retrieve the window (Stage) tied to this scene.
     * 3. Call the `close()` method on the stage to close the window.
     *
     * @param btn The button whose associated scene's stage will be closed.
     */
    public static void closeScene(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens a new scene with the specified dimensions, style, and settings.
     *
     * This method loads a scene from the provided FXML file, initializes a new JavaFX
     * stage, applies specified minimum width and height, sets the scene styles, and makes the stage
     * movable. It also assigns a custom icon to the stage and displays it.
     *
     * @param url the relative path to the FXML file to be loaded for the scene
     * @param width the minimum width for the scene's stage
     * @param height the minimum height for the scene's stage
     * @throws IOException if the specified FXML file cannot be loaded
     */
    public static void OpenSceneWithSize(String url, int width, int height) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(url));
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT); // Shows Stage Border Radius
        primaryStage.setTitle("");
        primaryStage.getIcons().add(new Image("/images/Logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        setStageMovable(root, primaryStage);
    }

    /**
     * Opens a new JavaFX scene without adjusting size parameters and applies specific configurations
     * such as non-resizable and transparent stage settings.
     *
     * @param url The URL of the FXML file used to define the layout of the scene.
     * @param width The width parameter, which is not utilized in this method.
     * @param height The height parameter, which is not utilized in this method.
     * @throws IOException If the FXML file specified by the URL cannot be loaded.
     *
     * Technical Steps:
     * 1. Load the FXML file from the specified URL using FXMLLoader.
     * 2. Create a new instance of javafx.scene.Scene with the loaded FXML root.
     * 3. Initialize a new JavaFX Stage instance.
     * 4. Configure the stage:
     *    - Set resizability to false.
     *    - Apply an undecorated and transparent style for the stage.
     *    - Set an empty title for the stage.
     *    - Add an application icon using a default image path.
     * 5. Assign the created scene to the stage.
     * 6. Display the stage using the show() method.
     * 7. Invoke the setStageMovable method to allow the stage to be dragged around by clicking and dragging on the scene.
     */
    public static void OpenSceneNoSize(String url, int width, int height) throws IOException {
        Parent root = FXMLLoader.load(Scene.class.getResource(url));
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT); // Shows Stage Border Radius
        primaryStage.setTitle("");
        primaryStage.getIcons().add(new Image("/images/Logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        setStageMovable(root, primaryStage);
    }

    /**
     * Opens a new scene in full-screen mode with a specified size.
     * This method creates and displays a new JavaFX stage with customizable dimensions
     * while enforcing full-screen mode upon launch. The scene is loaded from the specified FXML file.
     *
     * @param url    The path to the FXML file used for the scene definition. It should be relative to the `Scene` class's resource package.
     * @param height The minimum height of the stage to be set before displaying it.
     * @param width  The minimum width of the stage to be set before displaying it.
     *
     * Steps:
     * 1. Attempts to load the FXML file to initialize the scene's root node.
     *    Throws a runtime exception if the file cannot be accessed or parsed.
     * 2. Creates a new `javafx.scene.Scene` object with the loaded root node.
     * 3. Instantiates a new `Stage` object and configures the following:
     *    - Sets the specified minimum height and width of the stage.
     *    - Enables full-screen mode.
     *    - Disables the default full-screen exit hint display.
     *    - Clears the window title.
     * 4. Sets the created scene to the new stage and displays it.
     */
    public static void OpenSceneFullScreenWithSize(String url, int height, int width) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Scene.class.getResource(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); // Hide the exit hint message
        primaryStage.setTitle("");
//        primaryStage.getIcons().add(new Image("/images/Logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Enables a given stage to be moved by dragging a specified root node.
     *
     * This method configures mouse event handlers on the provided root node to
     * allow users to drag and reposition the stage window by clicking and dragging
     * the root node.
     *
     * @param root  the root node of the scene whose mouse events will be used
     *              to track and handle stage movement
     * @param stage the Stage instance to be made draggable
     *
     * Steps:
     * - Sets an OnMousePressed event on the root node to capture the initial
     *   mouse click position relative to the scene (xOffset and yOffset).
     * - Sets an OnMouseDragged event on the root node to adjust the X and Y
     *   position of the stage based on the difference between the mouse position
     *   in screen coordinates and the offset values.
     */
    private static void setStageMovable(Parent root, Stage stage) {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffeset = event.getSceneX();
                yOffeset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffeset);
                stage.setY(event.getScreenY() - yOffeset);
            }
        });
    }
}
