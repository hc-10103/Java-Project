package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Schedule {
    private int id;
    private int addedOrder;
    private Priority priority;
    private Category category;
    private Status status;
    private String title;
    private String detail;
    private LocalDate date;
    private LocalTime time;

    public Schedule(String title, String detail, Priority priority, Category category, LocalDate date, LocalTime time) {
        this.title = title;
        this.detail = detail;
        this.priority = priority;
        this.category = category;
        this.status = Status.TODO;
        this.date = date;
        this.time = time;
    }

    public abstract String getDisplayString();

    public abstract LocalDate getSortDate();

    public int getId() {
        return id;
    }

    public int getAddedOrder() {
        return addedOrder;
    }

    public Priority getPriority() {
        return priority;
    }

    public Category getCategory() {
        return category;
    }

    public Status getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddedOrder(int addedOrder) {
        this.addedOrder = addedOrder;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getFormattedDate() {
        if (getSortDate() == null) return "-";

        LocalDate d = getSortDate();
        String day = switch (d.getDayOfWeek()) {
            case MONDAY -> "Mon";
            case TUESDAY -> "Tue";
            case WEDNESDAY -> "Wed";
            case THURSDAY -> "Thu";
            case FRIDAY -> "Fri";
            case SATURDAY -> "Sat";
            case SUNDAY -> "Sun";
        };

        return String.format("%02d-%02d(%s)", d.getMonthValue(), d.getDayOfMonth(), day);
    }
}