package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class Exam extends Schedule {
    private String subject;
    private String location;

    public Exam(String subject, String detail, Priority priority,
                LocalDate date, LocalTime time, String location) {
        super(subject, detail, priority, Category.EXAM, date, time);
        this.subject = subject;
        this.location = location;
    }

    public String getSubject()  { return subject;  }
    public String getLocation() { return location; }

    public void setSubject(String subject) {
        this.subject = subject;
        setTitle(subject);         // keep title auto-synced with subject
    }

    public void setLocation(String location) { this.location = location; }

    @Override
    public LocalDate getSortDate() { return getDate(); }

    @Override
    public List<String[]> getDetailLines() {
        return Collections.singletonList(new String[]{"Location", location});
    }

    @Override
    protected void addCategorySpecificFields(List<EditableField> fields) {
        fields.add(new EditableField("Subject",
                in -> setSubject(in.readLine("New Subject: "))));
        fields.add(new EditableField("Location",
                in -> setLocation(in.readLine("New Location: "))));
        fields.add(new EditableField("Date",
                in -> setDate(in.readDateWithoutYear("New Date (MM-dd): "))));
        fields.add(new EditableField("Time",
                in -> setTime(in.readTime("New Time (HH:mm): "))));
    }
}