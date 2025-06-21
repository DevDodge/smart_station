package Controllers;

import Database.StationDB;
import Database.VehicleDB;
import Database.UserManagementDB;
import Model.stationModel;
import Model.usersModel;
import Options.MainController;
import Options.MyOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VehicleRegistrationController extends MainController implements Initializable {

    // Vehicle Information Fields
    @FXML private TextField vehicleIdField;
    @FXML private TextField plateNumberField;
    @FXML private ComboBox<stationModel> stationComboBox;

    // Vehicle License Fields
    @FXML private Rectangle licensePreview;
    @FXML private Label licensePathLabel;

    // Driver Selection Fields
    @FXML private TextField driverSearchField;
    @FXML private TableView<usersModel> driversTableView;
    @FXML private TableColumn<usersModel, Integer> driverIdColumn;
    @FXML private TableColumn<usersModel, String> driverNameColumn;
    @FXML private TableColumn<usersModel, String> driverPhoneColumn;
    @FXML private TableColumn<usersModel, Long> driverNIDColumn;
    @FXML private Label selectedDriverLabel;

    // Action Buttons
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    // State variables
    private File selectedLicenseFile;
    private usersModel selectedDriver;
    private FilteredList<usersModel> filteredDrivers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load stations for ComboBox
        loadStations();

        // Initialize driver table
        initializeDriversTable();

        // Set up event handlers
        setupEventHandlers();
    }

    private void loadStations() {
        try {
            ObservableList<stationModel> stations = StationDB.getStations();

            // Set up a custom string converter to display station as "ID: Name"
            stationComboBox.setConverter(new StringConverter<stationModel>() {
                @Override
                public String toString(stationModel station) {
                    return station == null ? "" : station.getStationId() + ": " + station.getStationName();
                }

                @Override
                public stationModel fromString(String string) {
                    // This method is needed but not used in this case
                    return null;
                }
            });

            stationComboBox.setItems(stations);

            // Set Tahrir station (ID 4) as default if available
            stations.stream()
                    .filter(station -> station.getStationId() == 4)
                    .findFirst()
                    .ifPresent(station -> stationComboBox.setValue(station));

        } catch (Exception e) {
            MyOptions.showCustomMessage("خطأ", "فشل في تحميل بيانات المحطات: " + e.getMessage());
        }
    }

    private void initializeDriversTable() {
        // Set up columns
        driverIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        driverNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        driverPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        driverNIDColumn.setCellValueFactory(new PropertyValueFactory<>("nid"));

        // Load drivers
        try {
            ObservableList<usersModel> drivers = UserManagementDB.getDrivers();
            filteredDrivers = new FilteredList<>(drivers, p -> true);
            driversTableView.setItems(filteredDrivers);
        } catch (Exception e) {
            MyOptions.showCustomMessage("خطأ", "فشل في تحميل بيانات السائقين: " + e.getMessage());
        }
    }

    private void setupEventHandlers() {
        // Setup table selection listener
        driversTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedDriver = newSelection;
                selectedDriverLabel.setText(selectedDriver.getFullName());
            }
        });
    }

    @FXML
    private void handleDriverSearch() {
        String searchText = driverSearchField.getText().toLowerCase();

        filteredDrivers.setPredicate(driver -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            // Convert the NID (long) to String before calling toLowerCase()
            String nidString = String.valueOf(driver.getNid());

            return driver.getFullName().toLowerCase().contains(searchText) ||
                    nidString.toLowerCase().contains(searchText) ||
                    driver.getPhoneNumber().toLowerCase().contains(searchText);
        });
    }

    @FXML
    private void handleUploadLicense() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("اختر صورة رخصة المركبة");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("صور", "*.png", "*.jpg", "*.jpeg")
        );

        // Set initial directory to project folder/Nids Images
        File initialDirectory = new File(System.getProperty("user.dir") + "/Nids Images");

        // Create directory if it doesn't exist
        if (!initialDirectory.exists()) {
            initialDirectory.mkdirs();
        }

        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedLicenseFile = selectedFile;
            licensePathLabel.setText(selectedFile.getAbsolutePath());

            // Load the image into the rectangle
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                licensePreview.setFill(new ImagePattern(image));
            } catch (FileNotFoundException e) {
                MyOptions.showCustomMessage("خطأ", "فشل في تحميل الصورة: " + e.getMessage());
            }
        }
    }

    

    @FXML
    private void handleSave() {
        // Validate input
        if (!validateInput()) {
            return;
        }

        try {
            // Get values from form
            String plateNumber = plateNumberField.getText();
            int stationId = stationComboBox.getValue().getStationId();
            String licensePath = selectedLicenseFile != null ? selectedLicenseFile.getAbsolutePath() : null;
            int driverId = selectedDriver.getUserId();

            // Save vehicle to database
            boolean success = VehicleDB.insertVehicle(stationId, licensePath, plateNumber, false, driverId);

            if (success) {
                MyOptions.showCustomMessage("نجاح", "تم تسجيل المركبة بنجاح");
                clearForm();
            } else {
                MyOptions.showCustomMessage("خطأ", "فشل في تسجيل المركبة");
            }

        } catch (Exception e) {
            MyOptions.showCustomMessage("خطأ", "حدث خطأ أثناء حفظ المركبة: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        // Close the form/window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        if (plateNumberField.getText().isEmpty()) {
            errors.append("- يجب إدخال رقم اللوحة\n");
        }

        if (stationComboBox.getValue() == null) {
            errors.append("- يجب اختيار المحطة الرئيسية\n");
        }

        if (selectedLicenseFile == null) {
            errors.append("- يجب تحميل صورة رخصة المركبة\n");
        }

        if (selectedDriver == null) {
            errors.append("- يجب اختيار سائق للمركبة\n");
        }

        if (errors.length() > 0) {
            MyOptions.showCustomMessage("تحقق من البيانات", errors.toString());
            return false;
        }

        return true;
    }

    private void clearForm() {
        plateNumberField.clear();
        licensePathLabel.setText("لم يتم اختيار ملف");
        selectedLicenseFile = null;
        selectedDriver = null;
        selectedDriverLabel.setText("لم يتم اختيار سائق");
        driversTableView.getSelectionModel().clearSelection();
        driverSearchField.clear();

        // Reset the license preview
        licensePreview.setFill(null);

        // Reset filtered list
        handleDriverSearch();
    }
}