package timeSchedule.view;

public class Color {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";

    public static final String WHITE = "\u001B[97m";
    public static final String RED = "\u001B[91m";
    public static final String GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[93m";
    public static final String BLUE = "\u001B[94m";
    public static final String PURPLE = "\u001B[95m";
    public static final String CYAN = "\u001B[96m";
    public static final String GRAY = "\u001B[90m";

    /**
     * Maps a menu choice (1..6) to a theme color code.
     * Returns {@code null} for any other value so the caller can treat it as invalid.
     */
    public static String fromMenuChoice(int choice) {
        return switch (choice) {
            case 1 -> WHITE;
            case 2 -> CYAN;
            case 3 -> GREEN;
            case 4 -> YELLOW;
            case 5 -> RED;
            case 6 -> PURPLE;
            default -> null;
        };
    }
}