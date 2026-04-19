package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class General extends Schedule {
    private String place;

    public General(String title, String detail, Priority priority,
                   LocalDate date, LocalTime time, String place) {
        super(title, detail, priority, Category.GENERAL, date, time);
        this.place = place;
    }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    @Override
    public LocalDate getSortDate() { return getDate(); }

    @Override
    public List<String[]> getDetailLines() {
        return Collections.singletonList(new String[]{"Place", place});
    }

    @Override
    protected void addCategorySpecificFields(List<EditableField> fields) {
        fields.add(new EditableField("Title",
                in -> setTitle(in.readLine("New Title: "))));
        fields.add(new EditableField("Date",
                in -> setDate(in.readDateWithoutYear("New Date (MM-dd): "))));
        fields.add(new EditableField("Time",
                in -> setTime(in.readTime("New Time (HH:mm): "))));
        fields.add(new EditableField("Place",
                in -> setPlace(in.readLine("New Place: "))));
    }
}