package timeSchedule.model;

import java.util.function.Consumer;

public class EditableField {
    private final String label;
    private final Consumer<ScheduleInput> editor;

    public EditableField(String label, Consumer<ScheduleInput> editor) {
        this.label = label;
        this.editor = editor;
    }

    public String getLabel() {
        return label;
    }

    public void edit(ScheduleInput input) {
        editor.accept(input);
    }
}