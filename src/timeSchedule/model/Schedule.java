package timeSchedule.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private static int counter = 1;

    private int id;
    private Priority priority;
    private Category category;
    private Status status;
    private String title;
    private String detail;
    private LocalDate date;
    private LocalTime time;

    public Schedule(String title, String detail, Priority priority, Category category) {
        this.id = counter++;
        this.title = title;
        this.detail = detail;
        this.priority = priority;
        this.category = category;
        this.status = Status.TODO;
    }

    public int getId() { return id; }
    public Priority getPriority() { return priority; }
    public Category getCategory() { return category; }
    public Status getStatus() { return status; }
    public String getTitle() { return title; }
    public String getDetail() { return detail; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }

    public void setPriority(Priority priority) { this.priority = priority; }
    public void setStatus(Status status) { this.status = status; }
    public void setTitle(String title) { this.title = title; }
    public void setDetail(String detail) { this.detail = detail; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(LocalTime time) { this.time = time; }

    // ⭐ 핵심: 날짜 + 요일 포맷
    public String getFormattedDate() {
        if (date == null) return "-";

        String day = switch (date.getDayOfWeek()) {
            case MONDAY -> "Mon";
            case TUESDAY -> "Tue";
            case WEDNESDAY -> "Wed";
            case THURSDAY -> "Thu";
            case FRIDAY -> "Fri";
            case SATURDAY -> "Sat";
            case SUNDAY -> "Sun";
        };

        return String.format("%02d-%02d(%s)",
                date.getMonthValue(),
                date.getDayOfMonth(),
                day);
    }
}