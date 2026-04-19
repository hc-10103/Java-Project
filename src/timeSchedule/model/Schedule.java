package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Schedule {
    private int id;
    private int addedOrder;
    private Priority priority;
    private final Category category;
    private Status status;
    private String title;
    private String detail;
    private LocalDate date;
    private LocalTime time;

    protected Schedule(String title, String detail, Priority priority,
                       Category category, LocalDate date, LocalTime time) {
        this.title = title;
        this.detail = detail;
        this.priority = priority;
        this.category = category;
        this.status = Status.TODO;
        this.date = date;
        this.time = time;
    }

    public abstract LocalDate getSortDate();

    public abstract List<String[]> getDetailLines();

    protected abstract void addCategorySpecificFields(List<EditableField> fields);


    public final List<EditableField> getEditableFields() {
        List<EditableField> fields = new ArrayList<>();
        List<EditableField> specificFields = new ArrayList<>();

        addCategorySpecificFields(specificFields);
        if (!specificFields.isEmpty()) {
            fields.add(specificFields.remove(0));
        }

        fields.add(new EditableField("Detail", in -> setDetail(in.readLine("New Detail: "))));
        fields.add(new EditableField("Priority", in -> setPriority(in.readPriority())));
        fields.add(new EditableField("Status", in -> setStatus(in.readStatus())));

        fields.addAll(specificFields);
        return fields;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAddedOrder() { return addedOrder; }
    public void setAddedOrder(int addedOrder) { this.addedOrder = addedOrder; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Category getCategory() { return category; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public String getFormattedDate() {
        if (getSortDate() == null) return "-";
        LocalDate d = getSortDate();
        String day = switch (d.getDayOfWeek()) {
            case MONDAY    -> "Mon";
            case TUESDAY   -> "Tue";
            case WEDNESDAY -> "Wed";
            case THURSDAY  -> "Thu";
            case FRIDAY    -> "Fri";
            case SATURDAY  -> "Sat";
            case SUNDAY    -> "Sun";
        };
        return String.format("%02d-%02d(%s)", d.getMonthValue(), d.getDayOfMonth(), day);
    }
}