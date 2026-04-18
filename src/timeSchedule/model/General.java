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
        return "[" + getId() + "] [GENERAL] "
                + getTitle()
                + " | Date: " + getDate()
                + " | Time: " + getTime()
                + " | Place: " + place
                + " | Priority: " + getPriority()
                + " | Status: " + getStatus()
                + " | Detail: " + getDetail();
    }

    @Override
    public LocalDate getSortDate() {
        return getDate();
    }
}