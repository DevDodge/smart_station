package Controllers;

import Options.MainController;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ScanCameraController extends MainController implements Initializable {
    
    Webcam webcam;  // Class-level webcam field
    
    @FXML
    private ComboBox<String> cameraChoiceBox;
    @FXML
    private Pane cameraCanvas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing HomePageController...");
        List<Webcam> webcams = Webcam.getWebcams();  // Get all available webcams
        cameraChoiceBox.getItems().addAll(webcams.stream().map(Webcam::getName).toList());
    }
    
    @FXML
    void closeCamera(ActionEvent event) {
        if (webcam != null) {
            webcam.close();  // Close the webcam
        }
    }
    
    @FXML
    void openCamera(ActionEvent event) {
        // Get the selected camera from the choice box
        String selectedCamera = cameraChoiceBox.getSelectionModel().getSelectedItem();
        
        // Check if a camera is selected
        if (selectedCamera != null && !selectedCamera.isEmpty()) {
            List<Webcam> webcams = Webcam.getWebcams();
            // Find the webcam matching the selected camera name
            for (Webcam cam : webcams) {
                if (cam.getName().equals(selectedCamera)) {
                    webcam = cam;  // Assign it to the class-level webcam field
                    break;
                }
            }
        }
        
        // If no camera is selected or the selected camera is not found, use the default webcam
        if (webcam == null) {
            webcam = Webcam.getDefault();
            System.out.println("No specific camera selected or found; opening default webcam.");
        }
        
        // Set up and open the webcam
        if (webcam != null) {
            // Open the webcam and set the resolution to VGA
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcam.open();
            
            // Adjust the size of the cameraCanvas to match the webcam resolution
            double cameraWidth = webcam.getViewSize().getWidth();
            double cameraHeight = webcam.getViewSize().getHeight();
            cameraCanvas.setPrefWidth(cameraWidth);
            cameraCanvas.setPrefHeight(cameraHeight);
            
            // Set up the WebcamPanel
            WebcamPanel panel = new WebcamPanel(webcam);
            panel.setFPSDisplayed(true);
            panel.setDisplayDebugInfo(true);
            panel.setImageSizeDisplayed(true);
            panel.setMirrored(true);
            
            System.out.println("Webcam Name: " + webcam.getName());
            System.out.println("Webcam Resolution: " + cameraWidth + "x" + cameraHeight);
            
            // Use SwingNode for JavaFX integration
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(panel);
            cameraCanvas.getChildren().clear();  // Clear previous content
            cameraCanvas.getChildren().add(swingNode);
        } else {
            System.err.println("No webcam available or found.");
        }
    }
    
    @FXML
    void screenshotNow(ActionEvent event) {
        try {
            // Ensure the webcam is open
            if (webcam != null && webcam.isOpen()) {
                // Get webcam image
                BufferedImage image = webcam.getImage();
                
                // Create the Screenshots directory if it doesn't exist
                File screenshotsDir = new File("Screenshots");
                if (!screenshotsDir.exists()) {
                    screenshotsDir.mkdir();
                }
                
                // Generate the file name with the current date and timestamp (12-hour format)
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ssa").format(new Date());
                String fileName = "Screenshots/" + timestamp + ".png";
                
                // Save the image as a PNG file
                ImageIO.write(image, "PNG", new File(fileName));
                
                System.out.println("Screenshot saved at: " + fileName);
            } else {
                System.err.println("Webcam is not open or is null. Please open a webcam first.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot: " + e.getMessage(), e);
        }
    }
}