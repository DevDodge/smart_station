package TableData;

import Model.stationModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import static Database.StationDB.getStations;

public class GetStationTableData {

    public static void getStationModelTable(
            TableColumn<stationModel, String> idCol,
            TableColumn<stationModel, String> nameCol,
            TableColumn<stationModel, String> addressCol,
            TableColumn<stationModel, String> capacityCol,
            TableView<stationModel> stationModelTable
    ) {
        // Set cell value factories
        idCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStationId())));

        nameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStationName()));

        // Combine address parts with "<-"
        addressCol.setCellValueFactory(cellData -> {
            String fullAddress = String.join(" <- ",
                    cellData.getValue().getLocGov(),
                    cellData.getValue().getLocCity(),
                    cellData.getValue().getLocRegion());
            return new SimpleStringProperty(fullAddress);
        });

        capacityCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getVehicleCapacity())));

        // Get station list and bind to table
        ObservableList<stationModel> stationList = getStations();
        stationModelTable.getItems().clear();
        stationModelTable.setItems(stationList);

        // Highlight rows in yellow if isPrimary is true
        stationModelTable.setRowFactory(tableView -> new TableRow<stationModel>() {
            @Override
            protected void updateItem(stationModel station, boolean empty) {
                super.updateItem(station, empty);
                if (station == null || empty) {
                    setStyle("");
                } else if (station.isPrimary()) {
                    setStyle("-fx-background-color: yellow;");
                } else {
                    setStyle("");
                }
            }
        });
    }
}
