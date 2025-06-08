package Controllers;

import Database.StationDB;
import Model.stationModel;
import Options.MainController;
import Options.MyOptions;
import Options.Scene;
import TableData.GetStationTableData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Options.MyOptions.showCustomMessage;

public class StationManagementController extends MainController implements Initializable {

    @FXML
    private TableColumn<stationModel, String> addressCol;

    @FXML
    private TableColumn<stationModel, String> capacityCol;

    @FXML
    private TextField capacityTxt;

    @FXML
    private TextField cityTxt;

    @FXML
    private FontIcon closeBtn;

    @FXML
    private TextField govTxt;

    @FXML
    private TableColumn<stationModel, String> idCol;

    @FXML
    private CheckBox isPrimaryCB;

    @FXML
    private TableColumn<stationModel, String> nameCol;

    @FXML
    private TextField regTxt;

    @FXML
    private TableView<stationModel> stationModelTable;

    @FXML
    private TextField stationNameTxt;

    private stationModel selectedStation;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
        MyOptions.applyRegexToFields("\\d*", capacityTxt);
    }

    @FXML
    void AddStation(ActionEvent event) {
        if (!validateFields()) return;

        String name = stationNameTxt.getText();
        String gov = govTxt.getText();
        String city = cityTxt.getText();
        String region = regTxt.getText();
        int capacity = Integer.parseInt(capacityTxt.getText());
        boolean isPrimary = isPrimaryCB.isSelected();

        boolean success = StationDB.insertStation(name, gov, city, region, capacity, isPrimary);
        if (success) {
            showCustomMessage("تمت العملية", "تم إضافة المحطة بنجاح.");
            clearFields();
            refreshTable();
        } else {
            showCustomMessage("خطأ", "فشل في إضافة المحطة.");
        }
    }

    @FXML
    void deleteStation(ActionEvent event) {
        selectedStation = (stationModel) stationModelTable.getSelectionModel().getSelectedItem();
        if (selectedStation == null) {
            showCustomMessage("خطأ", "يرجى اختيار محطة لحذفها.");
            return;
        }

        boolean success = StationDB.deleteStation(selectedStation.getStationId());
        if (success) {
            showCustomMessage("تم الحذف", "تم حذف المحطة بنجاح.");
            clearFields();
            refreshTable();
        } else {
            showCustomMessage("خطأ", "فشل في حذف المحطة.");
        }
    }

    @FXML
    void editStation(ActionEvent event) {
        if (selectedStation == null) {
            showCustomMessage("خطأ", "يرجى اختيار محطة لتعديلها.");
            return;
        }

        if (!validateFields()) return;

        selectedStation.setStationName(stationNameTxt.getText());
        selectedStation.setLocGov(govTxt.getText());
        selectedStation.setLocCity(cityTxt.getText());
        selectedStation.setLocRegion(regTxt.getText());
        selectedStation.setVehicleCapacity(Integer.parseInt(capacityTxt.getText()));
        selectedStation.setPrimary(isPrimaryCB.isSelected());

        boolean success = StationDB.updateStation(selectedStation);
        if (success) {
            showCustomMessage("تم التعديل", "تم تعديل المحطة بنجاح.");
            clearFields();
            refreshTable();
        } else {
            showCustomMessage("خطأ", "فشل في تعديل المحطة.");
        }
    }


    @FXML
    void ifShiftDelSoRemove(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE && event.isShiftDown()) {
            deleteStation(null);
        }
    }

    @FXML
    void isPrimarySelection(ActionEvent event) {
        // Get primary station
        if (isPrimaryCB.isSelected()) {
            stationModel primaryStation = StationDB.getPrimaryStation();
            if (primaryStation != null) {
                isPrimaryCB.setSelected(false);
                showCustomMessage("خطأ", "لا يمكن تحديد اكثر من موقف ليكون موقف اساسي، الموقف الاساسي الحالي هو: " +
                        primaryStation.getStationName() + "\n" +
                        "المحافظة: " + primaryStation.getLocGov() + "\n" +
                        "المدينة: " + primaryStation.getLocCity() + "\n" +
                        "المنطقة: " + primaryStation.getLocRegion());
                return;
            }
        }
    }

    @FXML
    private void onCapacityKeyTyped(KeyEvent event) {
//        capacityTxt.setTextFormatter(new TextFormatter<>(change -> {
//            String newText = change.getControlNewText();
//            if (newText.matches("\\d*")) {
//                return change; // Accept if it’s digits only
//            }
//            return null; // Reject the change
//        }));

    }

    @FXML
    void tableSelectionAction(MouseEvent event) {
        selectedStation = (stationModel) stationModelTable.getSelectionModel().getSelectedItem();

        if (selectedStation != null) {
            stationNameTxt.setText(selectedStation.getStationName());
            govTxt.setText(selectedStation.getLocGov());
            cityTxt.setText(selectedStation.getLocCity());
            regTxt.setText(selectedStation.getLocRegion());
            capacityTxt.setText(String.valueOf(selectedStation.getVehicleCapacity()));
            isPrimaryCB.setSelected(selectedStation.isPrimary());
        }
    }

    // Helper methods

    private boolean validateFields() {
        String name = stationNameTxt.getText();
        String gov = govTxt.getText();
        String city = cityTxt.getText();
        String region = regTxt.getText();
        String capStr = capacityTxt.getText();

        if (name.isEmpty() || gov.isEmpty() || city.isEmpty() || region.isEmpty() || capStr.isEmpty()) {
            showCustomMessage("خطأ", "يرجى ملء جميع الحقول.");
            return false;
        }

        try {
            Integer.parseInt(capStr);
        } catch (NumberFormatException e) {
            showCustomMessage("خطأ", "سعة المركبات يجب أن تكون رقمًا.");
            return false;
        }

        return true;
    }

    private void refreshTable() {
        GetStationTableData.getStationModelTable(
                idCol, nameCol, addressCol, capacityCol, stationModelTable
        );
    }

    private void clearFields() {
        stationNameTxt.clear();
        govTxt.clear();
        cityTxt.clear();
        regTxt.clear();
        capacityTxt.clear();
        isPrimaryCB.setSelected(false);
        selectedStation = null;
    }
}