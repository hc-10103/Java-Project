package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exam extends Schedule {
    private String subject;
    private String location;

    public Exam(String title, String detail, Priority priority,
                LocalDate date, LocalTime time,
                String subject, String location) {
        super(title, detail, priority, Category.EXAM, date, time);
        this.subject = subject;
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public String getLocation() {
        return location;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getDisplayString() {
        return "[" + getId() + "] [EXAM] "
                + getTitle()
                + " | Subject: " + subject
                + " | Date: " + getDate()
                + " | Time: " + getTime()
                + " | Location: " + location
                + " | Priority: " + getPriority()
                + " | Status: " + getStatus()
                + " | Detail: " + getDetail();
    }

    @Override
    public LocalDate getSortDate() {
        return getDate();
    }
}