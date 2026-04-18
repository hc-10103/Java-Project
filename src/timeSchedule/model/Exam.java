package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exam extends Schedule {

    private String subject;
    private String location;

    public Exam(String subject, String detail, Priority priority,
                LocalDate date, LocalTime time,
                String location) {

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
        setTitle(subject); // ⭐ 중요: 부모 title도 같이 맞춰줌
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getDisplayString() {
        return String.format(
                "%-3d %-12s %-20s %-12s %-6s %-6s",
                getId(),
                getCategory(),
                getTitle(), // subject로 통일됨
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