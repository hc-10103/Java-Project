package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class Assignment extends Schedule {
    private String submissionType;

    public Assignment(String title, String detail, Priority priority,
                      LocalDate date, LocalTime time, String submissionType) {
        super(title, detail, priority, Category.ASSIGNMENT, date, time);
        this.submissionType = submissionType;
    }

    public String getSubmissionType() { return submissionType; }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    @Override
    public LocalDate getSortDate() { return getDate(); }

    @Override
    public List<String[]> getDetailLines() {
        return Collections.singletonList(new String[]{"Type", submissionType});
    }

    @Override
    protected void addCategorySpecificFields(List<EditableField> fields) {
        fields.add(new EditableField("Title",
                in -> setTitle(in.readLine("New Title: "))));
        fields.add(new EditableField("Submission Type",
                in -> setSubmissionType(in.readLine("New Submission Type: "))));
        fields.add(new EditableField("Due Date",
                in -> setDate(in.readDateWithoutYear("New Due Date (MM-dd): "))));
        fields.add(new EditableField("Due Time",
                in -> setTime(in.readTime("New Due Time (HH:mm): "))));
    }
}
