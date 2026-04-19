package timeSchedule.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Abstraction for user input used by model-level code (e.g. editable fields).
 * Lives in the model package so model classes do not depend on any concrete
 * controller implementation — classic dependency inversion.
 */
public interface ScheduleInput {
    String readLine(String prompt);
    int readInt(String prompt);
    LocalDate readDateWithoutYear(String prompt);
    LocalTime readTime(String prompt);
    Priority readPriority();
    Status readStatus();
    DayOfWeek readDayOfWeek();
}