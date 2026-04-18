package timeSchedule.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class Fixed extends Schedule {

    private String place;

    public Fixed(String title, String detail, Priority priority,
                 DayOfWeek dayOfWeek, LocalTime time, String place) {

        super(title, detail, priority, Category.FIXED);

        // ⭐ 핵심: 가장 가까운 해당 요일 날짜로 변환
        LocalDate nextDate = LocalDate.now()
                .with(TemporalAdjusters.nextOrSame(dayOfWeek));

        setDate(nextDate);
        setTime(time);
        this.place = place;
    }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
}