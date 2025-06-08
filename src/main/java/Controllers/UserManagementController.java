package Controllers;

import Database.UserCredentialsDB;
import Database.UserManagementDB;
import Model.usersModel;
import Options.MainController;
import Options.MyOptions;
import TableData.GetUserTableData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import jdk.internal.net.http.common.Log;
import org.controlsfx.validation.ValidationResult;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Options.MyOptions.showCustomMessage;

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
    private Button addButton;


    @FXML
    private RowConstraints imageRowContsraint;
    // Global variable to track the selected user type
    private String userType = "", lisencePath = "";

    private usersModel selectedUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MyOptions.setPrefHeightZero(imageRowContsraint);
        GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
        MyOptions.applyRegexToFields("\\d{0,14}", NidFIeld);
        MyOptions.applyRegexToFields("\\d{0,12}", phoneFIeld);
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
            if (managerChkBox.isSelected()) {
                // Check empty fields
                if (username.isEmpty()) {
                    showCustomMessage("خطأ", "يرجى إدخال اسم المستخدم");
                    usernameField.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    showCustomMessage("خطأ", "يرجى إدخال كلمة المرور");
                    passField.requestFocus();
                    return;
                }

                // Basic validation
                if (username.length() < 3) {
                    showCustomMessage("خطأ", "اسم المستخدم يجب أن يكون 3 أحرف على الأقل");
                    usernameField.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    showCustomMessage("خطأ", "كلمة المرور يجب أن تكون 6 أحرف على الأقل");
                    passField.requestFocus();
                    return;
                }

                // Check if username already exists (optional - you can remove this if not needed)
                if (isUserExists(username)) {
                    showCustomMessage("خطأ", "اسم المستخدم موجود بالفعل");
                    usernameField.requestFocus();
                    return;
                }

                int key = UserManagementDB.addUserGetKey(name, phone, Long.parseLong(Nid), address, userType, lisencePath);

                // Insert user to database
                Database.UserCredentialsDB.insertUserCredential(key, username, password);

                // Success message
                showCustomMessage("عملية ناجحة", "تم إضافة المستخدم بنجاح");

                // Clear fields
                usernameField.clear();
                passField.clear();
                NidFIeld.clear();
                addressField.clear();
                nameField.clear();
                passField.clear();
                phoneFIeld.clear();
                usernameField.clear();
                lisencePath = "";

                GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);

                return;
            }
            UserManagementDB.insertUser(name, phone, Long.parseLong(Nid), address, userType, lisencePath);
            showCustomMessage("عملية ناجحة", "تم إضافة السائق بنجاح");
            GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
        } else {
            System.out.println("Data validation failed.");
        }
    }

    @FXML
    void driverCB(ActionEvent event) {
        addButton.setText("إضافة سائق");
    }

    @FXML
    void ManagerCB(ActionEvent event) {
        addButton.setText("إضافة مستخدم");
    }

    // Simple method to check if user exists (optional)
    private boolean isUserExists(String username) {
        try {
            // Add this method to your Database class if you want to check duplicates
            return Database.UserCredentialsDB.findUserByUsername(username);
        } catch (Exception e) {
            System.err.println("Error checking user existence: " + e.getMessage());
            return false; // If we can't check, assume user doesn't exist
        }
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
                boolean success = UserManagementDB.updateUser(selectedUser);

                if (success) {
                    showCustomMessage("عملية ناجحة", "تم تعديل البيانات بنجاح");
                    GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
                } else {
                    showCustomMessage("تحذير", "هناك خطأ، لم يتم تعديل البيانات");
                }
            } else {
                showCustomMessage("تحذير", "يجب اختيار المستخدم و تعديل البيانات");
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
                boolean success = UserManagementDB.deleteUser(selectedUser.getUserId());

                if (success) {
                    showCustomMessage("تم الحذف بنجاح", "تم حذف المستخدم بنجاح."); // Success message in Arabic
                    GetUserTableData.getusersModelTable(codeColumn, nameColumn, phoneColumn, NidColumn, addressColumn, userTypeColumn, userTable);
                } else {
                    showCustomMessage("فشل الحذف", "فشل في حذف المستخدم."); // Error message in Arabic
                }
            }
        } else {
            showCustomMessage("لا يوجد اختيار", "من فضلك اختر مستخدمًا للحذف."); // Error message in Arabic when no user is selected
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
        File frontFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

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
        File backFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

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
    void tableMouseClicked(MouseEvent event) throws SQLException {
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
                ResultSet userById = UserCredentialsDB.findUserById(selectedUser.getUserId());
                // Move the cursor to the first row before getting data
                if (userById.next()) {
                    usernameField.setText(userById.getString("username"));
                    passField.setText(userById.getString("password"));
                }else{
                    usernameField.setText("");
                    passField.setText("");
                }
                userById.close();

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

    // Check if username already exists
    private boolean isUsernameExists(String username) {
        try {
            return UserCredentialsDB.findUserByUsername(username);
        } catch (Exception e) {
            Log Logger = null;
            Logger.logError("Error checking username existence", e);
            return false; // Assume it doesn't exist if we can't check
        }
    }


}