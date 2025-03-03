package Options.PropertyItems;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.Optional;

public class SimpleCheckBoxProperty implements PropertySheet.Item {
    private final String name;
    private final String category;
    private final String tooltipDescription;
    private final SimpleBooleanProperty value;

    public SimpleCheckBoxProperty(String name, boolean initialValue, String category, String tooltipDescription) {
        this.name = name;
        this.category = category;
        this.tooltipDescription = tooltipDescription;
        this.value = new SimpleBooleanProperty(initialValue);
    }

    @Override
    public Class<?> getType() {
        return Boolean.class; // Represents boolean values
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
        return value.get(); // Returns the current value (true or false)
    }

    @Override
    public void setValue(Object value) {
        this.value.set((Boolean) value); // Updates the value of the CheckBox
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.of(value); // Exposes the observable value for bindings
    }

    @Override
    public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
        return Optional.of(CheckBoxPropertyEditor.class); // Custom property editor for CheckBox
    }

    @Override
    public boolean isEditable() {
        return true; // Allow editing
    }
}