package timeSchedule.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScheduleManager {
    private final ArrayList<Schedule> schedules;
    private int nextId;
    private int nextOrder;

    public ScheduleManager() {
        schedules = new ArrayList<>();
        nextId = 1;
        nextOrder = 1;
    }

    public void addSchedule(Schedule schedule) {
        schedule.setId(nextId++);
        schedule.setAddedOrder(nextOrder++);
        schedules.add(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(schedules);
    }

    public List<Schedule> getSchedulesSortedByStatus() {
        ArrayList<Schedule> list = new ArrayList<>(schedules);
        list.sort(
                Comparator.comparing(Schedule::getStatus)
                        .thenComparing(Schedule::getSortDate, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Schedule::getTime, Comparator.nullsLast(Comparator.naturalOrder()))
        );
        return list;
    }

    public List<Schedule> getSchedulesSortedByAddedOrder() {
        ArrayList<Schedule> list = new ArrayList<>(schedules);
        list.sort(Comparator.comparingInt(Schedule::getAddedOrder));
        return list;
    }

    public List<Schedule> getSchedulesSortedByNearestDate() {
        ArrayList<Schedule> list = new ArrayList<>(schedules);
        list.sort(
                Comparator.comparing(Schedule::getSortDate, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Schedule::getTime, Comparator.nullsLast(Comparator.naturalOrder()))
        );
        return list;
    }

    public List<Schedule> getSchedulesSortedByPriority() {
        ArrayList<Schedule> list = new ArrayList<>(schedules);
        list.sort(
                Comparator.comparingInt((Schedule s) -> s.getPriority().getValue())
                        .thenComparing(Schedule::getSortDate, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Schedule::getTime, Comparator.nullsLast(Comparator.naturalOrder()))
        );
        return list;
    }

    public List<Schedule> getSchedulesByCategory(Category category) {
        ArrayList<Schedule> list = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getCategory() == category) {
                list.add(schedule);
            }
        }
        list.sort(
                Comparator.comparing(Schedule::getSortDate, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Schedule::getTime, Comparator.nullsLast(Comparator.naturalOrder()))
        );
        return list;
    }

    public Schedule findById(int id) {
        for (Schedule schedule : schedules) {
            if (schedule.getId() == id) {
                return schedule;
            }
        }
        return null;
    }

    public boolean markAsDone(int id) {
        Schedule schedule = findById(id);
        if (schedule == null) {
            return false;
        }
        schedule.setStatus(Status.DONE);
        return true;
    }

    public boolean deleteSchedule(int id) {
        Schedule schedule = findById(id);
        if (schedule == null) {
            return false;
        }
        schedules.remove(schedule);
        return true;
    }
}