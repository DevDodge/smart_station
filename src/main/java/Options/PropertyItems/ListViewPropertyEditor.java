package Options.PropertyItems;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.List;

public class ListViewPropertyEditor implements PropertyEditor<List<String>> {

    private int gameId;  // Add a field for gameId
    private VBox editor;  // Can include multiple components if needed
    private ListView<String> listView;
    private ObservableList<String> selectedItems;  // Accessible via getter
    // Constructor that accepts a PropertySheet.Item instance
    public ListViewPropertyEditor(PropertySheet.Item item) {

        listView = new ListView<>(FXCollections.observableArrayList((List<String>) item.getValue()));
        listView.setEditable(false);
        listView.setPrefWidth(180);
        listView.setMaxWidth(220);
        listView.setFixedCellSize(30);
        int numberOfItems = listView.getItems().size();
        listView.setPrefHeight(numberOfItems * listView.getFixedCellSize() + 2);

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        selectedItems = listView.getSelectionModel().getSelectedItems();

        editor = new VBox(listView);
    }


    @Override
    public Node getEditor() {
        return editor;
    }

    @Override
    public List<String> getValue() {
        return selectedItems;
    }

    @Override
    public void setValue(List<String> value) {
        if (value != null) {
            listView.getItems().addAll(value);  // Set the available items in ListView
        }
    }

    public ObservableList<String> getSelectedItems() {
        return selectedItems;
    }

}
