package TableData;

import Model.usersModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static Database.UserManagementDB.getUsers;

public class GetUserTableData {
    public static void getusersModelTable(
            TableColumn<usersModel, String> IDCol,
            TableColumn<usersModel, String> nameCol,
            TableColumn<usersModel, String> phoneCol,
            TableColumn<usersModel, String> nidCol,
            TableColumn<usersModel, String> addressCol,
            TableColumn<usersModel, String> typeCol,
            TableView<usersModel> usersModelTable
    ) {
        // Set cell value factories for each column
        IDCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUserId())));
        
        nameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFullName()));
        
        phoneCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        
        nidCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNid())));
        
        addressCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress()));
        typeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getType()));
        
        // Get the list of usersModel from the database
        ObservableList<usersModel> usersModelList = getUsers();
        
        // Clear existing data and set new data
        usersModelTable.getItems().clear();
        usersModelTable.setItems(usersModelList);
    }
}
