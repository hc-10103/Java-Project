package timeSchedule.controller;

import timeSchedule.model.Assignment;
import timeSchedule.model.Category;
import timeSchedule.model.Exam;
import timeSchedule.model.Fixed;
import timeSchedule.model.General;
import timeSchedule.model.Priority;
import timeSchedule.model.Schedule;
import timeSchedule.model.ScheduleManager;
import timeSchedule.model.Status;
import timeSchedule.view.Color;
import timeSchedule.view.View;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Controller {
    private final Scanner sc;
    private final View view;
    private final ScheduleManager manager;
    private String color;

    public Controller() {
        sc = new Scanner(System.in);
        view = new View();
        manager = new ScheduleManager();
        color = Color.CYAN;
    }

    public void start() {
        while (true) {
            view.printMainMenu(color);
            int menu = inputInt("Select: ");

            switch (menu) {
                case 1 -> addSchedule();
                case 2 -> viewSchedules();
                case 3 -> markAsDone();
                case 4 -> editSchedule();
                case 5 -> deleteSchedule();
                case 6 -> changeColors();
                case 0 -> {
                    view.printMessage(color, "Program terminated.");
                    return;
                }
                default -> {
                    view.printMessage(color, "Invalid menu.");
                    waitEnter();
                }
            }
        }
    }

    private void addSchedule() {
        view.printCategoryMenu(color);
        int categoryChoice = inputInt("Select category: ");

        if (categoryChoice == 0) {
            return;
        }

        switch (categoryChoice) {
            case 1 -> addExam();
            case 2 -> addAssignment();
            case 3 -> addFixed();
            case 4 -> addGeneral();
            default -> {
                view.printMessage(color, "Invalid category.");
                waitEnter();
            }
        }
    }

    private void addExam() {
        String subject = input("Subject: ");
        String detail = input("Detail: ");
        String location = input("Location: ");
        LocalDate date = inputDateWithoutYear("Date (MM-dd): ");
        LocalTime time = inputTime("Time (HH:mm): ");
        Priority priority = inputPriority();

        Exam exam = new Exam(subject, detail, priority, date, time, location);
        manager.addSchedule(exam);

        view.printMessage(color, "Exam schedule added.");
        waitEnter();
    }

    private void addAssignment() {
        String subject = input("Subject: ");
        String detail = input("Detail: ");
        String submissionType = input("Submission Type: ");
        LocalDate date = inputDateWithoutYear("Due Date (MM-dd): ");
        LocalTime time = inputTime("Due Time (HH:mm): ");
        Priority priority = inputPriority();

        Assignment assignment = new Assignment(subject, detail, priority, date, time, submissionType);
        manager.addSchedule(assignment);

        view.printMessage(color, "Assignment schedule added.");
        waitEnter();
    }

    private void addFixed() {
        String title = input("Title: ");
        String detail = input("Detail: ");
        DayOfWeek dayOfWeek = inputDayOfWeek();
        LocalTime time = inputTime("Time (HH:mm): ");
        String place = input("Place: ");
        Priority priority = inputPriority();

        Fixed fixed = new Fixed(title, detail, priority, dayOfWeek, time, place);
        manager.addSchedule(fixed);

        view.printMessage(color, "Fixed schedule added.");
        waitEnter();
    }

    private void addGeneral() {
        String title = input("Title: ");
        String detail = input("Detail: ");
        LocalDate date = inputDateWithoutYear("Date (MM-dd): ");
        LocalTime time = inputTime("Time (HH:mm): ");
        String place = input("Place: ");
        Priority priority = inputPriority();

        General general = new General(title, detail, priority, date, time, place);
        manager.addSchedule(general);

        view.printMessage(color, "General schedule added.");
        waitEnter();
    }

    private void viewSchedules() {
        view.printViewMenu(color);
        int menu = inputInt("Select: ");

        switch (menu) {
            case 1 -> {
                view.printSchedules(color, manager.getAllSchedules());
                waitEnter();
            }
            case 2 -> viewSchedulesByCategory();
            case 3 -> viewSortedSchedules();
            case 0 -> {
                return;
            }
            default -> {
                view.printMessage(color, "Invalid menu.");
                waitEnter();
            }
        }
    }

    private void viewSchedulesByCategory() {
        view.printViewCategoryMenu(color);
        int menu = inputInt("Select category: ");

        switch (menu) {
            case 1 -> {
                view.printSchedules(color, manager.getSchedulesByCategory(Category.EXAM));
                waitEnter();
            }
            case 2 -> {
                view.printSchedules(color, manager.getSchedulesByCategory(Category.ASSIGNMENT));
                waitEnter();
            }
            case 3 -> {
                view.printSchedules(color, manager.getSchedulesByCategory(Category.FIXED));
                waitEnter();
            }
            case 4 -> {
                view.printSchedules(color, manager.getSchedulesByCategory(Category.GENERAL));
                waitEnter();
            }
            case 0 -> {
                return;
            }
            default -> {
                view.printMessage(color, "Invalid category.");
                waitEnter();
            }
        }
    }

    private void viewSortedSchedules() {
        view.printSortMenu(color);
        int menu = inputInt("Select sort option: ");

        switch (menu) {
            case 1 -> {
                view.printSchedules(color, manager.getSchedulesSortedByStatus());
                waitEnter();
            }
            case 2 -> {
                view.printSchedules(color, manager.getSchedulesSortedByAddedOrder());
                waitEnter();
            }
            case 3 -> {
                view.printSchedules(color, manager.getSchedulesSortedByNearestDate());
                waitEnter();
            }
            case 4 -> {
                view.printSchedules(color, manager.getSchedulesSortedByPriority());
                waitEnter();
            }
            case 0 -> {
                return;
            }
            default -> {
                view.printMessage(color, "Invalid sort option.");
                waitEnter();
            }
        }
    }

    private void markAsDone() {
        if (manager.getAllSchedules().isEmpty()) {
            view.printMessage(color, "No schedules to update.");
            waitEnter();
            return;
        }

        view.printSchedules(color, manager.getAllSchedules());
        int id = inputInt("Enter schedule ID to mark as done: ");
        boolean result = manager.markAsDone(id);

        if (result) {
            view.printMessage(color, "Schedule marked as DONE.");
        } else {
            view.printMessage(color, "Schedule not found.");
        }

        waitEnter();
    }

    private void editSchedule() {
        if (manager.getAllSchedules().isEmpty()) {
            view.printMessage(color, "No schedules to edit.");
            waitEnter();
            return;
        }

        view.printSchedules(color, manager.getAllSchedules());
        int id = inputInt("Enter schedule ID to edit: ");
        Schedule schedule = manager.findById(id);

        if (schedule == null) {
            view.printMessage(color, "Schedule not found.");
            waitEnter();
            return;
        }

        while (true) {
            printEditMenu(schedule);
            int choice = inputInt("Select field to edit: ");

            if (choice == 0) {
                view.printMessage(color, "Schedule updated.");
                waitEnter();
                return;
            }

            boolean edited = editSelectedField(schedule, choice);

            if (!edited) {
                view.printMessage(color, "Invalid menu.");
                waitEnter();
            }
        }
    }

    private void printEditMenu(Schedule schedule) {
        view.printTitle(color);
        System.out.println("Editing: " + schedule.getTitle() + " [" + schedule.getCategory() + "]");
        System.out.println("----------------------------------");
        System.out.println("1. Detail");
        System.out.println("2. Priority");
        System.out.println("3. Status");

        if (schedule instanceof Exam) {
            System.out.println("4. Subject");
            System.out.println("5. Location");
            System.out.println("6. Date");
            System.out.println("7. Time");
        } else if (schedule instanceof Assignment) {
            System.out.println("4. Subject");
            System.out.println("5. Submission Type");
            System.out.println("6. Due Date");
            System.out.println("7. Due Time");
        } else if (schedule instanceof Fixed) {
            System.out.println("4. Title");
            System.out.println("5. Day Of Week");
            System.out.println("6. Time");
            System.out.println("7. Place");
        } else if (schedule instanceof General) {
            System.out.println("4. Title");
            System.out.println("5. Date");
            System.out.println("6. Time");
            System.out.println("7. Place");
        }

        System.out.println("0. Finish");
        System.out.println();
    }

    private boolean editSelectedField(Schedule schedule, int choice) {
        switch (choice) {
            case 1 -> {
                schedule.setDetail(input("New Detail: "));
                return true;
            }
            case 2 -> {
                schedule.setPriority(inputPriority());
                return true;
            }
            case 3 -> {
                schedule.setStatus(inputStatus());
                return true;
            }
        }

        if (schedule instanceof Exam exam) {
            return editExamField(exam, choice);
        } else if (schedule instanceof Assignment assignment) {
            return editAssignmentField(assignment, choice);
        } else if (schedule instanceof Fixed fixed) {
            return editFixedField(fixed, choice);
        } else if (schedule instanceof General general) {
            return editGeneralField(general, choice);
        }

        return false;
    }

    private boolean editExamField(Exam exam, int choice) {
        switch (choice) {
            case 4 -> {
                exam.setSubject(input("New Subject: "));
                return true;
            }
            case 5 -> {
                exam.setLocation(input("New Location: "));
                return true;
            }
            case 6 -> {
                exam.setDate(inputDateWithoutYear("New Date (MM-dd): "));
                return true;
            }
            case 7 -> {
                exam.setTime(inputTime("New Time (HH:mm): "));
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean editAssignmentField(Assignment assignment, int choice) {
        switch (choice) {
            case 4 -> {
                assignment.setSubject(input("New Subject: "));
                return true;
            }
            case 5 -> {
                assignment.setSubmissionType(input("New Submission Type: "));
                return true;
            }
            case 6 -> {
                assignment.setDate(inputDateWithoutYear("New Due Date (MM-dd): "));
                return true;
            }
            case 7 -> {
                assignment.setTime(inputTime("New Due Time (HH:mm): "));
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean editFixedField(Fixed fixed, int choice) {
        switch (choice) {
            case 4 -> {
                fixed.setTitle(input("New Title: "));
                return true;
            }
            case 5 -> {
                fixed.setDayOfWeek(inputDayOfWeek());
                return true;
            }
            case 6 -> {
                fixed.setTime(inputTime("New Time (HH:mm): "));
                return true;
            }
            case 7 -> {
                fixed.setPlace(input("New Place: "));
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean editGeneralField(General general, int choice) {
        switch (choice) {
            case 4 -> {
                general.setTitle(input("New Title: "));
                return true;
            }
            case 5 -> {
                general.setDate(inputDateWithoutYear("New Date (MM-dd): "));
                return true;
            }
            case 6 -> {
                general.setTime(inputTime("New Time (HH:mm): "));
                return true;
            }
            case 7 -> {
                general.setPlace(input("New Place: "));
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private void deleteSchedule() {
        if (manager.getAllSchedules().isEmpty()) {
            view.printMessage(color, "No schedules to delete.");
            waitEnter();
            return;
        }

        view.printSchedules(color, manager.getAllSchedules());
        int id = inputInt("Enter schedule ID to delete: ");
        boolean result = manager.deleteSchedule(id);

        if (result) {
            view.printMessage(color, "Schedule deleted.");
        } else {
            view.printMessage(color, "Schedule not found.");
        }

        waitEnter();
    }

    private void changeColors() {
        view.printColorMenu(color);
        int menu = inputInt("Select color: ");

        switch (menu) {
            case 1 -> color = Color.WHITE;
            case 2 -> color = Color.CYAN;
            case 3 -> color = Color.GREEN;
            case 4 -> color = Color.YELLOW;
            case 5 -> color = Color.RED;
            case 6 -> color = Color.PURPLE;
            case 0 -> {
                return;
            }
            default -> {
                view.printMessage(color, "Invalid color.");
                waitEnter();
                return;
            }
        }

        view.printMessage(color, "Theme color changed.");
        waitEnter();
    }

    private String input(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    private int inputInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid number.");
            }
        }
    }

    private LocalDate inputDateWithoutYear(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = sc.nextLine();
                String[] parts = input.split("-");
                if (parts.length != 2) {
                    throw new IllegalArgumentException();
                }

                int month = Integer.parseInt(parts[0]);
                int day = Integer.parseInt(parts[1]);

                return LocalDate.of(LocalDate.now().getYear(), month, day);
            } catch (Exception e) {
                System.out.println("Invalid date format. Use MM-dd.");
            }
        }
    }

    private LocalTime inputTime(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalTime.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid time format. Use HH:mm.");
            }
        }
    }

    private Priority inputPriority() {
        while (true) {
            view.printPriorityGuide();
            int choice = inputInt("Select priority: ");

            switch (choice) {
                case 1 -> {
                    return Priority.HIGH;
                }
                case 2 -> {
                    return Priority.MEDIUM;
                }
                case 3 -> {
                    return Priority.LOW;
                }
                default -> System.out.println("Invalid priority.");
            }
        }
    }

    private Status inputStatus() {
        while (true) {
            System.out.println("Status:");
            System.out.println("1. TODO");
            System.out.println("2. DONE");
            int choice = inputInt("Select status: ");

            switch (choice) {
                case 1 -> {
                    return Status.TODO;
                }
                case 2 -> {
                    return Status.DONE;
                }
                default -> System.out.println("Invalid status.");
            }
        }
    }

    private DayOfWeek inputDayOfWeek() {
        while (true) {
            view.printDayOfWeekGuide();
            int choice = inputInt("Select day: ");

            switch (choice) {
                case 1 -> {
                    return DayOfWeek.MONDAY;
                }
                case 2 -> {
                    return DayOfWeek.TUESDAY;
                }
                case 3 -> {
                    return DayOfWeek.WEDNESDAY;
                }
                case 4 -> {
                    return DayOfWeek.THURSDAY;
                }
                case 5 -> {
                    return DayOfWeek.FRIDAY;
                }
                case 6 -> {
                    return DayOfWeek.SATURDAY;
                }
                case 7 -> {
                    return DayOfWeek.SUNDAY;
                }
                default -> System.out.println("Invalid day.");
            }
        }
    }

    private void waitEnter() {
        System.out.println("Press Enter to continue.");
        sc.nextLine();
    }
}