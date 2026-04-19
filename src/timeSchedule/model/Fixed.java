package timeSchedule.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Fixed extends Schedule {
    private DayOfWeek dayOfWeek;
    private String place;

    public Fixed(String title, String detail, Priority priority,
                 DayOfWeek dayOfWeek, LocalTime time, String place) {
        super(title, detail, priority, Category.FIXED, null, time);
        this.dayOfWeek = dayOfWeek;
        this.place = place;
    }

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public String    getPlace()     { return place;     }

    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setPlace(String place)            { this.place = place; }

    @Override
    public LocalDate getSortDate() {
        LocalDate today = LocalDate.now();
        int diff = dayOfWeek.getValue() - today.getDayOfWeek().getValue();
        if (diff < 0) diff += 7;
        return today.plusDays(diff);
    }

    @Override
    public List<String[]> getDetailLines() {
        List<String[]> lines = new ArrayList<>();
        lines.add(new String[]{"Day",   String.valueOf(dayOfWeek)});
        lines.add(new String[]{"Place", place});
        return lines;
    }

    @Override
    protected void addCategorySpecificFields(List<EditableField> fields) {
        fields.add(new EditableField("Title",
                in -> setTitle(in.readLine("New Title: "))));
        fields.add(new EditableField("Day Of Week",
                in -> setDayOfWeek(in.readDayOfWeek())));
        fields.add(new EditableField("Time",
                in -> setTime(in.readTime("New Time (HH:mm): "))));
        fields.add(new EditableField("Place",
                in -> setPlace(in.readLine("New Place: "))));
    }
}