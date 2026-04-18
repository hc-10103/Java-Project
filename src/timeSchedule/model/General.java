package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class General extends Schedule {
    private String place;

    public General(String title, String detail, Priority priority,
                   LocalDate date, LocalTime time, String place) {
        super(title, detail, priority, Category.GENERAL, date, time);
        this.place = place;
    }

    public String getPlace() {
        return place;
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
        return getDate();
    }
}