package timeSchedule.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;


public interface ScheduleInput {
    String readLine(String prompt);
    int readInt(String prompt);
    LocalDate readDateWithoutYear(String prompt);
    LocalTime readTime(String prompt);
    Priority readPriority();
    Status readStatus();
    DayOfWeek readDayOfWeek();
}