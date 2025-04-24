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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

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
    private ImageView driveelisenceImgView, driveelisenceImgView1;
    
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
    
    private usersModel selectedUser;
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
            MyOptions.showCustomMessage("عملية ناجحة", "تم إضافة السائق بنجاح");
            TableData.GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
        } else {
            System.out.println("Data validation failed.");
        }
    }
    
    @FXML
    private void addManagement() {
        
    }
    
    // Method to update an existing user
    @FXML
    private void updateUser() {
        if (validateFields()) {
            // Get the selected user from the table
            selectedUser = userTable.getSelectionModel().getSelectedItem();
            
            if (selectedUser != null) {
                // Update the selected user with the new data from the fields
                selectedUser.setFullName(nameField.getText());
                selectedUser.setPhoneNumber(phoneFIeld.getText());
                selectedUser.setAddress(addressField.getText());
                selectedUser.setNid(Long.parseLong(NidFIeld.getText()));
                selectedUser.setType("Driver".equalsIgnoreCase(userType) ? usersModel.UserType.DRIVER : usersModel.UserType.MANAGER);
                selectedUser.setPersonalLicense(lisencePath);
                
                // Call the database method to update the user in the database
                boolean success = Database.UserManagementDB.updateUser(selectedUser);
                
                if (success) {
                    MyOptions.showCustomMessage("عملية ناجحة", "تم تعديل البيانات بنجاح");
                    TableData.GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
                } else {
                    MyOptions.showCustomMessage("تحذير", "هناك خطأ، لم يتم تعديل البيانات");
                }
            } else {
                MyOptions.showCustomMessage("تحذير", "يجب اختيار المستخدم و تعديل البيانات");
            }
        }
    }
    
    // Method to delete a user
    @FXML
    private void deleteUser() {
        // Get the selected user from the table
        usersModel selectedUser = userTable.getSelectionModel().getSelectedItem();
        
        if (selectedUser != null) {
            // Confirm before deletion using the updated showAlert method with Arabic text
            String confirmation = MyOptions.showAlert("ConfirmDialog",
                    "تأكيد الحذف",  // Title in Arabic: "Confirm Deletion"
                    "هل أنت متأكد أنك تريد حذف هذا المستخدم؟", // Header in Arabic: "Are you sure you want to delete this user?"
                    "اضغط على OK للحذف أو Cancel للإلغاء."); // Content in Arabic: "Click OK to delete or Cancel to abort."
            
            if ("OK".equals(confirmation)) {
                // Call the database method to delete the user from the database
                boolean success = Database.UserManagementDB.deleteUser(selectedUser.getUserId());
                
                if (success) {
                    MyOptions.showCustomMessage("تم الحذف بنجاح", "تم حذف المستخدم بنجاح."); // Success message in Arabic
                    TableData.GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
                } else {
                    MyOptions.showCustomMessage("فشل الحذف", "فشل في حذف المستخدم."); // Error message in Arabic
                }
            }
        } else {
            MyOptions.showCustomMessage("لا يوجد اختيار", "من فضلك اختر مستخدمًا للحذف."); // Error message in Arabic when no user is selected
        }
    }
    
    
    @FXML
    void uploadLicenseFile(ActionEvent event) {
        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();
        
        // Set the title of the FileChooser dialog
        fileChooser.setTitle("Select Front Side of ID");
        
        // Restrict the file extension to image formats (PNG, JPEG, etc.)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        
        // Ask the user to select the first file (front side)
        File frontFile = fileChooser.showOpenDialog(((javafx.scene.Node) event.getSource()).getScene().getWindow());
        
        if (frontFile != null) {
            String frontPath = frontFile.getAbsolutePath();
            System.out.println("Selected Front File Path: " + frontPath);
            
            // Display the front image in driveelisenceImgView
            Image frontImage = new Image(frontFile.toURI().toString());
            driveelisenceImgView.setImage(frontImage);
            
            // Update lisencePath with the front file path
            lisencePath = frontPath;
        } else {
            System.out.println("No front file selected.");
            return; // Exit if the first file is not selected
        }
        
        // Ask the user to select the second file (back side)
        fileChooser.setTitle("Select Back Side of ID");
        File backFile = fileChooser.showOpenDialog(((javafx.scene.Node) event.getSource()).getScene().getWindow());
        
        if (backFile != null) {
            String backPath = backFile.getAbsolutePath();
            System.out.println("Selected Back File Path: " + backPath);
            
            // Display the back image in driveelisenceImgView1
            Image backImage = new Image(backFile.toURI().toString());
            driveelisenceImgView1.setImage(backImage);
            
            // Append the back file path to lisencePath, separated by a comma
            lisencePath += "," + backPath;
        } else {
            System.out.println("No back file selected.");
        }
        
        System.out.println("Final lisencePath: " + lisencePath);
    }
    
    @FXML
    void tableMouseClicked(MouseEvent event) {
        lisencePath = "";
        // Get the selected row from the table
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        
        // Check if a row is selected
        if (selectedUser != null) {
            // Populate the fields with the selected user's data
            NidFIeld.setText(selectedUser.getNid() + "");
            nameField.setText(selectedUser.getFullName());
            phoneFIeld.setText(selectedUser.getPhoneNumber());
            addressField.setText(selectedUser.getAddress());
//            usernameField.setText(selectedUser.getUsername());
//            passField.setText(selectedUser.getPassword());
            
            // Set user type based on selection and update checkboxes
            userType = String.valueOf(selectedUser.getUserType());
            if ("Driver".equalsIgnoreCase(userType)) {
                driverChkBox.setSelected(true);
                managerChkBox.setSelected(false);
            } else if ("Manager".equalsIgnoreCase(userType)) {
                managerChkBox.setSelected(true);
                driverChkBox.setSelected(false);
            }
            
            // Handle license image paths (front and back) if relevant
            String personalLicense = selectedUser.getPersonalLicense();
            
            if (personalLicense != null && !personalLicense.trim().isEmpty()) {
                String[] licensePaths = personalLicense.split(",");
                
                if (licensePaths.length > 0 && !licensePaths[0].isEmpty()) {
                    // Front side of ID
                    Image frontImage = new Image(new File(licensePaths[0]).toURI().toString());
                    driveelisenceImgView.setImage(frontImage);
                } else {
                    // Clear the front image
                    driveelisenceImgView.setImage(null);
                }
                
                if (licensePaths.length > 1 && !licensePaths[1].isEmpty()) {
                    // Back side of ID
                    Image backImage = new Image(new File(licensePaths[1]).toURI().toString());
                    driveelisenceImgView1.setImage(backImage);
                } else {
                    // Clear the back image
                    driveelisenceImgView1.setImage(null);
                }
            } else {
                // No license paths, clear both images
                driveelisenceImgView.setImage(null);
                driveelisenceImgView1.setImage(null);
            }
        } else {
            System.out.println("No row selected!");
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
//        if (passField.getText().isEmpty()) {
//            isValid = false;
//            errorMessage.append("Password is required.\n");
//        }
        if (phoneFIeld.getText().isEmpty()) {
            isValid = false;
            errorMessage.append("Phone number is required.\n");
        }
//        if (usernameField.getText().isEmpty()) {
//            isValid = false;
//            errorMessage.append("Username is required.\n");
//        }
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