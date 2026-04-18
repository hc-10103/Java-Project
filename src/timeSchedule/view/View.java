package timeSchedule.view;

import timeSchedule.model.Priority;
import timeSchedule.model.Schedule;

import java.util.List;

import static timeSchedule.view.Color.*;

public class View {

    public void printTitle(String color) {
        System.out.println(color + BOLD + "==========================================" + RESET);
        System.out.println(color + BOLD + "          TIME SCHEDULER SYSTEM           " + RESET);
        System.out.println(color + BOLD + "==========================================" + RESET);
        System.out.println();
    }

    public void printMainMenu(String color) {
        printTitle(color);
        System.out.println(BOLD + "MAIN MENU" + RESET);
        System.out.println("----------------------------------");
        System.out.println("1. Add Schedule");
        System.out.println("2. View Schedules");
        System.out.println("3. Mark as Done");
        System.out.println("4. Edit Schedule");
        System.out.println("5. Delete Schedule");
        System.out.println("6. Change Colors");
        System.out.println("0. Exit");
        System.out.println();
    }

    public void printCategoryMenu(String color) {
        printTitle(color);
        System.out.println(BOLD + "SELECT CATEGORY" + RESET);
        System.out.println("----------------------------------");
        System.out.println("1. Exam");
        System.out.println("2. Assignment");
        System.out.println("3. Fixed");
        System.out.println("4. General");
        System.out.println("0. Back");
        System.out.println();
    }

    public void printViewMenu(String color) {
        printTitle(color);
        System.out.println(BOLD + "VIEW SCHEDULES" + RESET);
        System.out.println("----------------------------------");
        System.out.println("1. View All");
        System.out.println("2. View by Category");
        System.out.println("3. Sort Schedules");
        System.out.println("0. Back");
        System.out.println();
    }

    public void printViewCategoryMenu(String color) {
        printTitle(color);
        System.out.println(BOLD + "VIEW BY CATEGORY" + RESET);
        System.out.println("----------------------------------");
        System.out.println("1. Exam");
        System.out.println("2. Assignment");
        System.out.println("3. Fixed");
        System.out.println("4. General");
        System.out.println("0. Back");
        System.out.println();
    }

    public void printSortMenu(String color) {
        printTitle(color);
        System.out.println(BOLD + "SORT SCHEDULES" + RESET);
        System.out.println("----------------------------------");
        System.out.println("1. By Status");
        System.out.println("2. By Added Order");
        System.out.println("3. By Nearest Date");
        System.out.println("4. By Priority");
        System.out.println("0. Back");
        System.out.println();
    }

    public void printColorMenu(String color) {
        printTitle(color);
        System.out.println(BOLD + "CHANGE COLOR THEME" + RESET);
        System.out.println("----------------------------------");
        System.out.println("1. White");
        System.out.println("2. Cyan");
        System.out.println("3. Green");
        System.out.println("4. Yellow");
        System.out.println("5. Red");
        System.out.println("6. Purple");
        System.out.println("0. Back");
        System.out.println();
    }

    public void printPriorityGuide() {
        System.out.println("Priority:");
        System.out.println("1. HIGH");
        System.out.println("2. MEDIUM");
        System.out.println("3. LOW");
        System.out.println();
    }

    public void printDayOfWeekGuide() {
        System.out.println("Day of Week:");
        System.out.println("1. MONDAY");
        System.out.println("2. TUESDAY");
        System.out.println("3. WEDNESDAY");
        System.out.println("4. THURSDAY");
        System.out.println("5. FRIDAY");
        System.out.println("6. SATURDAY");
        System.out.println("7. SUNDAY");
        System.out.println();
    }

    public void printSchedules(String color, List<Schedule> schedules) {
        printTitle(color);

        if (schedules.isEmpty()) {
            System.out.println(YELLOW + "No schedules found." + RESET);
            System.out.println();
            return;
        }

        for (Schedule schedule : schedules) {
            printCard(schedule);
        }
    }

    public void printMessage(String color, String message) {
        System.out.println(color + BOLD + message + RESET);
        System.out.println();
    }

    private void printCard(Schedule s) {
        String statusText;
        if (s.getStatus().toString().equals("DONE")) {
            statusText = GREEN + BOLD + "V" + RESET;
        } else {
            statusText = RED + "X" + RESET;
        }

        String priorityText = formatPriority(s.getPriority());

        System.out.println(
                WHITE + BOLD +
                        "[" + s.getId() + "] " +
                        s.getTitle() +
                        RESET +
                        " " +
                        GRAY + "[" + s.getCategory() + "]" + RESET
        );

        System.out.println(
                "    " +
                        s.getFormattedDate() + " " +
                        (s.getTime() == null ? "" : s.getTime()) +
                        "   " +
                        priorityText +
                        "   " +
                        statusText
        );

        System.out.println();
    }

    private String formatPriority(Priority priority) {
        return switch (priority) {
            case HIGH -> "●●●";
            case MEDIUM -> "●●";
            case LOW -> "●";
        };
    }
}