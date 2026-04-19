package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class Assignment extends Schedule {
    private String subject;
    private String submissionType;

    public Assignment(String subject, String detail, Priority priority, LocalDate date, LocalTime time, String submissionType) {
        super(subject, detail, priority, Category.ASSIGNMENT, date, time);
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
        setTitle(subject);
    }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    @Override
    public LocalDate getSortDate() {
        return getDate();
    }

    @Override
    public List<String[]> getDetailLines() {
        return Collections.singletonList(
                new String[]{"Type", submissionType}
        );
    }
}