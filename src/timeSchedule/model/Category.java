package timeSchedule.model;

public enum Category {
    EXAM("Exam"),
    ASSIGNMENT("Assignment"),
    FIXED("Fixed"),
    GENERAL("General");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    public static Category fromMenuChoice(int choice) {
        Category[] values = values();
        if (choice >= 1 && choice <= values.length) {
            return values[choice - 1];
        }
        return null;
    }
}