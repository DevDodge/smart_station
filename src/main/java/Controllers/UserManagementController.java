package Controllers;

import Model.usersModel;
import Options.MainController;
import Options.MyOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.swing.text.View;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UserManagementController extends MainController implements Initializable {
    
    @FXML
    private TableColumn<usersModel, String> NidColumn;
    
    @FXML
    private TextField NidFIeld;
    
    @FXML
    private TableColumn<usersModel, String> addressColumn;
    
    @FXML
    private StackPane imageContainerStackPane;
    
    @FXML
    private TextField addressField;
    
    @FXML
    private FontIcon closeBtn;
    
    @FXML
    private TableColumn<usersModel, String> codeColumn;
    
    @FXML
    private ImageView driveelisenceImgView;
    
    @FXML
    private CheckBox driverChkBox;
    
    @FXML
    private CheckBox managerChkBox;
    
    @FXML
    private TableColumn<usersModel, String> nameColumn;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField passField;
    
    @FXML
    private TableColumn<usersModel, String> phoneColumn;
    
    @FXML
    private TextField phoneFIeld;
    
    @FXML
    private TableView<usersModel> userTable;
    
    @FXML
    private TableColumn<usersModel, String> userTypeColumn;
    
    @FXML
    private TextField usernameField;
    
    
    @FXML
    private RowConstraints imageRowContsraint;
    // Global variable to track the selected user type
    private String userType = "", lisencePath = "";
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MyOptions.setPrefHeightZero(imageRowContsraint);
        TableData.GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
    }
    
    // Method to prepare data for database insertion
    @FXML
    private void addDriver() {
        if (validateFields()) {
            // If validation passes, prepare data (e.g., set up a user object)
            String Nid = NidFIeld.getText();
            String address = addressField.getText();
            String name = nameField.getText();
            String password = passField.getText();
            String phone = phoneFIeld.getText();
            String username = usernameField.getText();
            
            Database.UserManagementDB.insertUser(name, phone, Long.parseLong(Nid), address, userType, lisencePath);
            MyOptions.showCustomMessage("عملية ناجحة","تم إضافة السائق بنجاح");
            TableData.GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
        } else {
            System.out.println("Data validation failed.");
        }
    }
    
    @FXML
    private void addManagement() {
        
    }
    
    @FXML
    void uploadLicenseFile(ActionEvent event) {
        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();
        
        // Set the title of the FileChooser dialog
        fileChooser.setTitle("Select License File");
        
        // Restrict the file extension to image formats (optional - PNG, JPEG, etc.)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        
        // Open the FileChooser dialog
        File selectedFile = fileChooser.showOpenDialog(((javafx.scene.Node) event.getSource()).getScene().getWindow());
        
        // If a file is selected, get its system path and print or use it
        if (selectedFile != null) {
            lisencePath = selectedFile.getAbsolutePath();
            System.out.println("Selected File Path: " + lisencePath);
            
            // Display the image in driveelisenceImgView
            Image licenseImage = new Image(selectedFile.toURI().toString());
            driveelisenceImgView.setImage(licenseImage);
            imageRowContsraint.setPrefHeight(licenseImage.getHeight()+10);
        } else {
            System.out.println("No file selected.");
        }
    }
    
    // On checkbox click, select one and deselect the other
    @FXML
    private void onDriverChkBoxClicked(MouseEvent event) {
        if (driverChkBox.isSelected()) {
            managerChkBox.setSelected(false);
            userType = "Driver"; // Set userType to Driver
        } else {
            userType = ""; // Reset if unchecked
        }
    }
    
    @FXML
    private void onManagerChkBoxClicked(MouseEvent event) {
        if (managerChkBox.isSelected()) {
            driverChkBox.setSelected(false);
            userType = "Manager"; // Set userType to Manager
        } else {
            userType = ""; // Reset if unchecked
        }
    }
    
    // Method to validate fields
    @FXML
    private boolean validateFields() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
        // Validate if the fields are filled correctly
        if (NidFIeld.getText().isEmpty()) {
            isValid = false;
            errorMessage.append("NID is required.\n");
        }
        
        if (addressField.getText().isEmpty()) {
            isValid = false;
            errorMessage.append("Address is required.\n");
        }
        if (nameField.getText().isEmpty()) {
            isValid = false;
            errorMessage.append("Name is required.\n");
        }
        if (passField.getText().isEmpty()) {
            isValid = false;
            errorMessage.append("Password is required.\n");
        }
        if (phoneFIeld.getText().isEmpty()) {
            isValid = false;
            errorMessage.append("Phone number is required.\n");
        }
        if (usernameField.getText().isEmpty()) {
            isValid = false;
            errorMessage.append("Username is required.\n");
        }
        if (userType.isEmpty()) {
            isValid = false;
            errorMessage.append("Please select a user type (Driver/Manager).\n");
        }
        if (lisencePath.isEmpty()) {
            isValid = false;
            errorMessage.append("License file path is required.\n");
        }
        
        // Display error messages if validation fails
        if (!isValid) {
            System.out.println("Validation failed:\n" + errorMessage.toString());
        }
        
        return isValid;
    }
    
    @FXML
    void ifShiftDelSoRemove(KeyEvent event) {
        
    }
    
}