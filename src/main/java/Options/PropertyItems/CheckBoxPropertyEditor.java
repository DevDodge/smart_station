package Options.PropertyItems;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

public class CheckBoxPropertyEditor implements PropertyEditor<Boolean> {
    private final CheckBox checkBox;

    public CheckBoxPropertyEditor(PropertySheet.Item property) {
        checkBox = new CheckBox();

        // Initialize the CheckBox with the property's current value
        checkBox.setSelected((Boolean) property.getValue());

        // Update the property's value when the CheckBox is toggled
        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> property.setValue(newVal));
    }

    @Override
    public Node getEditor() {
        return new HBox(checkBox); // Return the CheckBox wrapped in a container
    }

    @Override
    public Boolean getValue() {
        return checkBox.isSelected(); // Return the CheckBox value
    }

    @Override
    public void setValue(Boolean value) {
        checkBox.setSelected(value); // Set the CheckBox value
    }
}