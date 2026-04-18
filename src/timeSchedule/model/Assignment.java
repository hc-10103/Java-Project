package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Assignment extends Schedule {
    private String subject;
    private String submissionType;

    public Assignment(String title, String detail, Priority priority,
                      LocalDate date, LocalTime time,
                      String subject, String submissionType) {
        super(title, detail, priority, Category.ASSIGNMENT, date, time);
        this.subject = subject;
        this.submissionType = submissionType;
    }

    public String getSubject() {
        return subject;
    }

    public String getSubmissionType() {
        return submissionType;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
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