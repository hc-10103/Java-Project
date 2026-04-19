package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class Exam extends Schedule {
    private String subject;
    private String location;

    public Exam(String subject, String detail, Priority priority, LocalDate date, LocalTime time, String location) {
        super(subject, detail, priority, Category.EXAM, date, time);
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
        setTitle(subject);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public LocalDate getSortDate() {
        return getDate();
    }

    @Override
    public List<String[]> getDetailLines() {
        return Collections.singletonList(
                new String[]{"Location", location}
        );
    }
}