package Options.PropertyItems;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

public class IntegerPropertyEditor implements PropertyEditor<Integer> {
    private final TextField textField;

    public IntegerPropertyEditor(PropertySheet.Item item) {
        textField = new TextField();
        textField.setMaxWidth(50);  // Set max-width to 50px
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                int val = Integer.parseInt(newValue);
                item.setValue(val);
            } catch (NumberFormatException e) {
                // Handle invalid input (non-integer)
                textField.setText(oldValue);
            }
        });
    }

    @Override
    public Node getEditor() {
        HBox box = new HBox(textField);
        return box;
    }

    @Override
    public Integer getValue() {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return null; // Return null for invalid input
        }
    }

    @Override
    public void setValue(Integer value) {
        textField.setText(value == null ? "" : value.toString());
    }
}
