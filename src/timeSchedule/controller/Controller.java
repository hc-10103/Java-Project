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

        if (categoryChoice == 0) return;

        switch (categoryChoice) {
            case 1 -> addExam();
            case 2 -> addAssignment();
            case 3 -> addFixed();
            case 4 -> addGeneral();
            default -> view.printMessage(color, "Invalid category.");
        }

        waitEnter();
    }

    private void addExam() {
        String subject = input("Subject: ");
        String detail = input("Detail: ");
        String location = input("Location: ");
        LocalDate date = inputDateWithoutYear("Date (MM-dd): ");
        LocalTime time = inputTime("Time (HH:mm): ");

        // ⭐ 마지막에 priority
        Priority priority = inputPriority();

        Exam exam = new Exam(subject, detail, priority, date, time, location);
        manager.addSchedule(exam);

        view.printMessage(color, "Exam schedule added.");
    }

    private void addAssignment() {
        String title = input("Title: ");
        String subject = input("Subject: ");
        String detail = input("Detail: ");
        String submissionType = input("Submission Type: ");
        LocalDate date = inputDateWithoutYear("Due Date (MM-dd): ");
        LocalTime time = inputTime("Due Time (HH:mm): ");

        Priority priority = inputPriority();

        Assignment assignment = new Assignment(title, detail, priority, date, time, subject, submissionType);
        manager.addSchedule(assignment);

        view.printMessage(color, "Assignment schedule added.");
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
    }

    private void viewSchedules() {
        while (true) {
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
    }

    private void viewSchedulesByCategory() {
        while (true) {
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
                    view.printMessage(color, "Invalid menu.");
                    waitEnter();
                }
            }
        }
    }

    private void viewSortedSchedules() {
        while (true) {
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
                    view.printMessage(color, "Invalid menu.");
                    waitEnter();
                }
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

        editCommonFields(schedule);

        if (schedule instanceof Exam exam) {
            editExam(exam);
        } else if (schedule instanceof Assignment assignment) {
            editAssignment(assignment);
        } else if (schedule instanceof Fixed fixed) {
            editFixed(fixed);
        } else if (schedule instanceof General general) {
            editGeneral(general);
        }

        view.printMessage(color, "Schedule updated.");
        waitEnter();
    }

    private void editCommonFields(Schedule schedule) {
        String detail = inputOptional("Detail (" + schedule.getDetail() + "): ");
        if (!detail.isBlank()) {
            schedule.setDetail(detail);
        }

        Priority priority = inputOptionalPriority("Priority (" + schedule.getPriority() + ")");
        if (priority != null) {
            schedule.setPriority(priority);
        }

        Status status = inputOptionalStatus("Status (" + schedule.getStatus() + ")");
        if (status != null) {
            schedule.setStatus(status);
        }
    }

    private void editExam(Exam exam) {
        String subject = inputOptional("Subject (" + exam.getSubject() + "): ");
        if (!subject.isBlank()) {
            exam.setSubject(subject);
        }

        String location = inputOptional("Location (" + exam.getLocation() + "): ");
        if (!location.isBlank()) {
            exam.setLocation(location);
        }

        LocalDate date = inputOptionalDateWithoutYear("Date (" + exam.getFormattedDate() + ")");
        if (date != null) {
            exam.setDate(date);
        }

        LocalTime time = inputOptionalTime("Time (" + exam.getTime() + "): ");
        if (time != null) {
            exam.setTime(time);
        }
    }

    private void editAssignment(Assignment assignment) {
        String title = inputOptional("Title (" + assignment.getTitle() + "): ");
        if (!title.isBlank()) {
            assignment.setTitle(title);
        }

        String subject = inputOptional("Subject (" + assignment.getSubject() + "): ");
        if (!subject.isBlank()) {
            assignment.setSubject(subject);
        }

        String detail = inputOptional("Detail (" + assignment.getDetail() + "): ");
        if (!detail.isBlank()) {
            assignment.setDetail(detail);
        }

        String submissionType = inputOptional("Submission Type (" + assignment.getSubmissionType() + "): ");
        if (!submissionType.isBlank()) {
            assignment.setSubmissionType(submissionType);
        }

        LocalDate date = inputOptionalDateWithoutYear("Due Date (" + assignment.getFormattedDate() + ")");
        if (date != null) {
            assignment.setDate(date);
        }

        LocalTime time = inputOptionalTime("Due Time (" + assignment.getTime() + "): ");
        if (time != null) {
            assignment.setTime(time);
        }
    }

    private void editFixed(Fixed fixed) {
        String title = inputOptional("Title (" + fixed.getTitle() + "): ");
        if (!title.isBlank()) {
            fixed.setTitle(title);
        }

        String detail = inputOptional("Detail (" + fixed.getDetail() + "): ");
        if (!detail.isBlank()) {
            fixed.setDetail(detail);
        }

        DayOfWeek dayOfWeek = inputOptionalDayOfWeek("Day Of Week (" + fixed.getDayOfWeek() + ")");
        if (dayOfWeek != null) {
            fixed.setDayOfWeek(dayOfWeek);
        }

        LocalTime time = inputOptionalTime("Time (" + fixed.getTime() + "): ");
        if (time != null) {
            fixed.setTime(time);
        }

        String place = inputOptional("Place (" + fixed.getPlace() + "): ");
        if (!place.isBlank()) {
            fixed.setPlace(place);
        }
    }

    private void editGeneral(General general) {
        String title = inputOptional("Title (" + general.getTitle() + "): ");
        if (!title.isBlank()) {
            general.setTitle(title);
        }

        String detail = inputOptional("Detail (" + general.getDetail() + "): ");
        if (!detail.isBlank()) {
            general.setDetail(detail);
        }

        LocalDate date = inputOptionalDateWithoutYear("Date (" + general.getFormattedDate() + ")");
        if (date != null) {
            general.setDate(date);
        }

        LocalTime time = inputOptionalTime("Time (" + general.getTime() + "): ");
        if (time != null) {
            general.setTime(time);
        }

        String place = inputOptional("Place (" + general.getPlace() + "): ");
        if (!place.isBlank()) {
            general.setPlace(place);
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

    private String inputOptional(String message) {
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
                if (parts.length != 2) throw new IllegalArgumentException();

                int month = Integer.parseInt(parts[0]);
                int day = Integer.parseInt(parts[1]);

                return LocalDate.of(LocalDate.now().getYear(), month, day);
            } catch (Exception e) {
                System.out.println("Invalid date format. Use MM-dd.");
            }
        }
    }

    private LocalDate inputOptionalDateWithoutYear(String message) {
        while (true) {
            try {
                System.out.print(message + " (Enter만 누르면 유지): ");
                String input = sc.nextLine();

                if (input.isBlank()) return null;

                String[] parts = input.split("-");
                if (parts.length != 2) throw new IllegalArgumentException();

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

    private LocalTime inputOptionalTime(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = sc.nextLine();
                if (input.isBlank()) return null;
                return LocalTime.parse(input);
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

    private Priority inputOptionalPriority(String message) {
        while (true) {
            view.printPriorityGuide();
            System.out.print(message + " (Enter만 누르면 유지): ");
            String input = sc.nextLine();

            if (input.isBlank()) return null;

            switch (input) {
                case "1" -> {
                    return Priority.HIGH;
                }
                case "2" -> {
                    return Priority.MEDIUM;
                }
                case "3" -> {
                    return Priority.LOW;
                }
                default -> System.out.println("Invalid priority.");
            }
        }
    }

    private Status inputOptionalStatus(String message) {
        while (true) {
            System.out.println("Status:");
            System.out.println("1. TODO");
            System.out.println("2. DONE");
            System.out.print(message + " (Enter만 누르면 유지): ");
            String input = sc.nextLine();

            if (input.isBlank()) return null;

            switch (input) {
                case "1" -> {
                    return Status.TODO;
                }
                case "2" -> {
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

    private DayOfWeek inputOptionalDayOfWeek(String message) {
        while (true) {
            view.printDayOfWeekGuide();
            System.out.print(message + " (Enter만 누르면 유지): ");
            String input = sc.nextLine();

            if (input.isBlank()) return null;

            switch (input) {
                case "1" -> {
                    return DayOfWeek.MONDAY;
                }
                case "2" -> {
                    return DayOfWeek.TUESDAY;
                }
                case "3" -> {
                    return DayOfWeek.WEDNESDAY;
                }
                case "4" -> {
                    return DayOfWeek.THURSDAY;
                }
                case "5" -> {
                    return DayOfWeek.FRIDAY;
                }
                case "6" -> {
                    return DayOfWeek.SATURDAY;
                }
                case "7" -> {
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