package Options.PropertyItems;

import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.Optional;

public class SimpleBooleanPropertyItem implements PropertySheet.Item {

    private String name;
    private Boolean value;

    public SimpleBooleanPropertyItem(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Class<?> getType() {
        return Boolean.class;
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
        return "Toggle " + name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (Boolean) value;
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
        return Optional.empty();
    }

    @Override
    public boolean isEditable() {
        return true;
    }
}
