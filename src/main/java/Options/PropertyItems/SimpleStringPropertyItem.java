package Options.PropertyItems;

import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.Optional;

public class SimpleStringPropertyItem implements PropertySheet.Item {
    private String name;
    private String value;

    public SimpleStringPropertyItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public String getCategory() {
        return "Custom Properties";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Enter a value for " + name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object o) {
        this.value = (String) o;  // Use method parameter 'o'
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty(); // You can implement this if needed for property binding
    }

    @Override
    public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
        return Optional.empty();  // Let ControlsFX choose the default editor
    }

    @Override
    public boolean isEditable() {
        return true; // Return true explicitly if you want this to always be editable
    }


}
