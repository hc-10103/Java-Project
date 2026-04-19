package timeSchedule.model;

import java.util.Comparator;

/**
 * Sort strategies for schedule lists. Each value carries its own Comparator,
 * so {@link ScheduleManager} no longer needs a separate method per sort mode —
 * classic Strategy-pattern-as-enum.
 */
public enum SortOption {
    STATUS(
            Comparator.comparing(Schedule::getStatus)
                    .thenComparing(Schedule::getSortDate, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(Schedule::getTime, Comparator.nullsLast(Comparator.naturalOrder()))
    ),
    ADDED_ORDER(
            Comparator.comparingInt(Schedule::getAddedOrder)
    ),
    NEAREST_DATE(
            Comparator.comparing(Schedule::getSortDate, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(Schedule::getTime, Comparator.nullsLast(Comparator.naturalOrder()))
    ),
    PRIORITY(
            Comparator.comparingInt((Schedule s) -> s.getPriority().getValue())
                    .thenComparing(Schedule::getSortDate, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(Schedule::getTime, Comparator.nullsLast(Comparator.naturalOrder()))
    );

    private final Comparator<Schedule> comparator;

    SortOption(Comparator<Schedule> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Schedule> comparator() {
        return comparator;
    }

    public static SortOption fromMenuChoice(int choice) {
        return switch (choice) {
            case 1 -> STATUS;
            case 2 -> ADDED_ORDER;
            case 3 -> NEAREST_DATE;
            case 4 -> PRIORITY;
            default -> null;
        };
    }
}