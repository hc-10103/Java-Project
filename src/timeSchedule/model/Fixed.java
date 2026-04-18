package timeSchedule.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Fixed extends Schedule {
    private DayOfWeek dayOfWeek;
    private String place;

    public Fixed(String title, String detail, Priority priority,
                 DayOfWeek dayOfWeek, LocalTime time, String place) {
        super(title, detail, priority, Category.FIXED, null, time);
        this.dayOfWeek = dayOfWeek;
        this.place = place;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getPlace() {
        return place;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String getDisplayString() {
        return String.format(
                "%-3d %-12s %-20s %-12s %-6s %-6s",
                getId(),
                getCategory(),
                getTitle(),
                getFormattedDate(),
                getTime(),
                getStatus()
        );
    }

    @Override
    public LocalDate getSortDate() {
        LocalDate today = LocalDate.now();
        int diff = dayOfWeek.getValue() - today.getDayOfWeek().getValue();
        if (diff < 0) diff += 7;
        return today.plusDays(diff);
    }
}