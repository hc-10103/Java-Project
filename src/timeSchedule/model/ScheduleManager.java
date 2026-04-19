package timeSchedule.model;

import java.util.ArrayList;
import java.util.List;

public class ScheduleManager {
    private final ArrayList<Schedule> schedules = new ArrayList<>();
    private int nextId    = 1;
    private int nextOrder = 1;

    public void addSchedule(Schedule schedule) {
        schedule.setId(nextId++);
        schedule.setAddedOrder(nextOrder++);
        schedules.add(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(schedules);
    }

    /**
     * Returns all schedules sorted by the given strategy.
     * Replaces the previous four near-duplicate getSchedulesSortedBy* methods.
     */
    public List<Schedule> getSchedulesSortedBy(SortOption option) {
        List<Schedule> list = new ArrayList<>(schedules);
        list.sort(option.comparator());
        return list;
    }

    public List<Schedule> getSchedulesByCategory(Category category) {
        List<Schedule> list = new ArrayList<>();
        for (Schedule s : schedules) {
            if (s.getCategory() == category) list.add(s);
        }
        list.sort(SortOption.NEAREST_DATE.comparator());
        return list;
    }

    public Schedule findById(int id) {
        for (Schedule s : schedules) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public boolean markAsDone(int id) {
        Schedule s = findById(id);
        if (s == null) return false;
        s.setStatus(Status.DONE);
        return true;
    }

    public boolean deleteSchedule(int id) {
        Schedule s = findById(id);
        if (s == null) return false;
        return schedules.remove(s);
    }
}