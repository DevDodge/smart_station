package Options.PropertyItems;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.List;
import java.util.Optional;
// The coming comment lines is for you ChatGPT
// This Class In which I need to access the variable which is  private ObservableList<String> selectedItems; IN ListViewPropertyEditor class
// accessing this variable here will be as cool as than now, it will be very helpful and will solve our problem, So we should share it here with manual way or modern one
public class ListViewPropertyItem implements PropertySheet.Item {
    private String name;
    private String category;
    private String tooltipDescription;
    private List<String> value;  // List of values
    private ListViewPropertyEditor editor;  // Reference to ListViewPropertyEditor

    public ListViewPropertyItem(String name, List<String> value, String category, String tooltipDescription) {
        this.name = name;
        this.value = value;
        this.tooltipDescription = tooltipDescription;
        this.category = category;
    }

    // Method to link the editor to the item
    public void setEditor(ListViewPropertyEditor editor) {
        this.editor = editor;
    }
    @Override
    public Class<?> getType() {
        return List.class;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return tooltipDescription;
    }
    @Override
    public Object getValue() {
        if (editor != null) {
            return editor.getSelectedItems();  // Dynamically get the selected items from the editor
        }
        return value;  // If editor is not set, return the initial value
    }

    @Override
    public void setValue(Object value) {
        this.value = (List<String>) value;
        if (editor != null) {
            editor.setValue(this.value);  // Sync the value with the editor
        }
    }


    // New method to get the selected items in a manual or modern way
    public ObservableList<String> getSelectedItems() {
        if (editor != null) { // after debuging I found that this because editor is null
            return editor.getSelectedItems();  // Access the selectedItems from the editor
        }
        return FXCollections.observableArrayList();  // Return an empty list if editor is null
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
        return Optional.of(ListViewPropertyEditor.class);  // Custom editor
    }

    @Override
    public boolean isEditable() {
        return true;
    }

}
