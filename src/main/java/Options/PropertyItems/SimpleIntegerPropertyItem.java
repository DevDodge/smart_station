package Options.PropertyItems;

import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.Optional;

public class SimpleIntegerPropertyItem implements PropertySheet.Item{
    private String name;
    private String category;
    private String tooltipDescription;
    private Integer value;

    public SimpleIntegerPropertyItem(String name, Integer value, String category,String tooltipDescription) {
        this.name = name;
        this.value = value;
        this.tooltipDescription = tooltipDescription;
        this.category = category;
    }

    @Override
    public Class<?> getType() {
        return Integer.class;
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
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (Integer) value;
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
        return Optional.of(IntegerPropertyEditor.class);
    }

    @Override
    public boolean isEditable() {
        return true;
    }
}
