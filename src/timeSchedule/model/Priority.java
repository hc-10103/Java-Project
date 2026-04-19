package timeSchedule.model;

public enum Priority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Priority fromMenuChoice(int choice) {
        return switch (choice) {
            case 1 -> HIGH;
            case 2 -> MEDIUM;
            case 3 -> LOW;
            default -> null;
        };
    }
}