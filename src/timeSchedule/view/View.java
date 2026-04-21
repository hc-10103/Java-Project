package timeSchedule.view;

import timeSchedule.model.EditableField;
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
        printMenu("MAIN MENU", new String[]{
                "1. Add Schedule",
                "2. View Schedules",
                "3. Mark as Done",
                "4. Edit Schedule",
                "5. Delete Schedule",
                "6. Change Colors",
                "0. Exit"
        });
    }

    public void printCategoryMenu(String color) {
        printTitle(color);
        printMenu("SELECT CATEGORY", new String[]{
                "1. Exam",
                "2. Assignment",
                "3. Fixed",
                "4. General",
                "0. Back"
        });
    }

    public void printViewMenu(String color) {
        printTitle(color);
        printMenu("VIEW SCHEDULES", new String[]{
                "1. View All",
                "2. View by Category",
                "3. Sort Schedules",
                "0. Back"
        });
    }

    public void printViewCategoryMenu(String color) {
        printTitle(color);
        printMenu("VIEW BY CATEGORY", new String[]{
                "1. Exam",
                "2. Assignment",
                "3. Fixed",
                "4. General",
                "0. Back"
        });
    }

    public void printSortMenu(String color) {
        printTitle(color);
        printMenu("SORT SCHEDULES", new String[]{
                "1. By Status",
                "2. By Added Order",
                "3. By Nearest Date",
                "4. By Priority",
                "0. Back"
        });
    }

    public void printColorMenu(String color) {
        printTitle(color);
        printMenu("CHANGE COLOR THEME", new String[]{
                "1. White",
                "2. Cyan",
                "3. Green",
                "4. Yellow",
                "5. Red",
                "6. Purple",
                "0. Back"
        });
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

        System.out.println(GRAY + "Enter an ID to view details, or 0 to go back. " + RESET);
        System.out.println();
    }

    public void printScheduleDetail(String color, Schedule schedule) {
        printTitle(color);

        String statusText = schedule.getStatus().toString().equals("DONE")
                ? GREEN + BOLD + "V" + RESET
                : RED + "X" + RESET;

        System.out.println(
                WHITE + BOLD + schedule.getTitle() + RESET +
                        " " +
                        GRAY + "[" + schedule.getCategory() + "]" + RESET
        );
        System.out.println("----------------------------------------");
        System.out.println("ID       : " + schedule.getId());
        System.out.println("Date     : " + schedule.getFormattedDate());
        System.out.println("Time     : " + (schedule.getTime() == null ? "-" : schedule.getTime()));
        System.out.println("Priority : " + formatPriority(schedule.getPriority()));
        System.out.println("Status   : " + statusText);
        System.out.println("Detail   : " + schedule.getDetail());

        for (String[] line : schedule.getDetailLines()) {
            System.out.println(String.format("%-8s : %s", line[0], line[1]));
        }

        System.out.println();
    }


    public void printEditMenu(String color, Schedule schedule, List<EditableField> fields) {
        printTitle(color);
        System.out.println(
                BOLD + "Editing: " + schedule.getTitle() + RESET +
                        " " +
                        GRAY + "[" + schedule.getCategory() + "]" + RESET
        );
        System.out.println("----------------------------------");
        for (int i = 0; i < fields.size(); i++) {
            System.out.println((i + 1) + ". " + fields.get(i).getLabel());
        }
        System.out.println("0. Finish");
        System.out.println();
    }

    public void printMessage(String color, String message) {
        System.out.println(color + BOLD + message + RESET);
        System.out.println();
    }

    private void printMenu(String header, String[] items) {
        System.out.println(BOLD + header + RESET);
        System.out.println("----------------------------------");
        for (String item : items) {
            System.out.println(item);
        }
        System.out.println();
    }

    private void printCard(Schedule schedule) {
        String statusText = schedule.getStatus().toString().equals("DONE")
                ? GREEN + BOLD + "V" + RESET
                : RED + "X" + RESET;

        System.out.println(
                WHITE + BOLD +
                        "[" + schedule.getId() + "] " +
                        schedule.getTitle() +
                        RESET +
                        " " +
                        GRAY + "[" + schedule.getCategory() + "]" + RESET
        );

        System.out.println(
                "    " +
                        schedule.getFormattedDate() + " " +
                        (schedule.getTime() == null ? "" : schedule.getTime()) +
                        "   " +
                        formatPriority(schedule.getPriority()) +
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