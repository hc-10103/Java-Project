package timeSchedule.controller;

import timeSchedule.model.*;
import timeSchedule.view.Color;
import timeSchedule.view.View;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private final View view = new View();
    private final ScheduleManager manager = new ScheduleManager();
    private final InputReader input = new InputReader(new Scanner(System.in), view);
    private String color = Color.CYAN;

    public void start() {
        while (true) {
            view.printMainMenu(color);
            int menu = input.readInt("Select: ");
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
                default -> showMessageAndPause("Invalid menu.");
            }
        }
    }

    // ===== add =====================================================

    private void addSchedule() {
        view.printCategoryMenu(color);
        int choice = input.readInt("Select category: ");
        if (choice == 0) return;

        Category category = Category.fromMenuChoice(choice);
        if (category == null) {
            showMessageAndPause("Invalid category.");
            return;
        }

        Schedule schedule = switch (category) {
            case EXAM       -> createExam();
            case ASSIGNMENT -> createAssignment();
            case FIXED      -> createFixed();
            case GENERAL    -> createGeneral();
        };

        manager.addSchedule(schedule);
        showMessageAndPause(category.displayName() + " schedule added.");
    }

    private Schedule createExam() {
        String subject   = input.readLine("Subject: ");
        String detail    = input.readLine("Detail: ");
        String location  = input.readLine("Location: ");
        LocalDate date   = input.readDateWithoutYear("Date (MM-dd): ");
        LocalTime time   = input.readTime("Time (HH:mm): ");
        Priority priority = input.readPriority();
        return new Exam(subject, detail, priority, date, time, location);
    }

    private Schedule createAssignment() {
        String subject        = input.readLine("Subject: ");
        String detail         = input.readLine("Detail: ");
        String submissionType = input.readLine("Submission Type: ");
        LocalDate date        = input.readDateWithoutYear("Due Date (MM-dd): ");
        LocalTime time        = input.readTime("Due Time (HH:mm): ");
        Priority priority     = input.readPriority();
        return new Assignment(subject, detail, priority, date, time, submissionType);
    }

    private Schedule createFixed() {
        String title        = input.readLine("Title: ");
        String detail       = input.readLine("Detail: ");
        DayOfWeek dayOfWeek = input.readDayOfWeek();
        LocalTime time      = input.readTime("Time (HH:mm): ");
        String place        = input.readLine("Place: ");
        Priority priority   = input.readPriority();
        return new Fixed(title, detail, priority, dayOfWeek, time, place);
    }

    private Schedule createGeneral() {
        String title      = input.readLine("Title: ");
        String detail     = input.readLine("Detail: ");
        LocalDate date    = input.readDateWithoutYear("Date (MM-dd): ");
        LocalTime time    = input.readTime("Time (HH:mm): ");
        String place      = input.readLine("Place: ");
        Priority priority = input.readPriority();
        return new General(title, detail, priority, date, time, place);
    }

    // ===== view ====================================================

    private void viewSchedules() {
        view.printViewMenu(color);
        int menu = input.readInt("Select: ");
        switch (menu) {
            case 1 -> showScheduleListWithDetail(manager.getAllSchedules());
            case 2 -> viewSchedulesByCategory();
            case 3 -> viewSortedSchedules();
            case 0 -> { }
            default -> showMessageAndPause("Invalid menu.");
        }
    }

    private void viewSchedulesByCategory() {
        view.printViewCategoryMenu(color);
        int choice = input.readInt("Select category: ");
        if (choice == 0) return;

        Category category = Category.fromMenuChoice(choice);
        if (category == null) {
            showMessageAndPause("Invalid category.");
            return;
        }
        showScheduleListWithDetail(manager.getSchedulesByCategory(category));
    }

    private void viewSortedSchedules() {
        view.printSortMenu(color);
        int choice = input.readInt("Select sort option: ");
        if (choice == 0) return;

        SortOption option = SortOption.fromMenuChoice(choice);
        if (option == null) {
            showMessageAndPause("Invalid sort option.");
            return;
        }
        showScheduleListWithDetail(manager.getSchedulesSortedBy(option));
    }

    private void showScheduleListWithDetail(List<Schedule> schedules) {
        view.printSchedules(color, schedules);

        if (schedules.isEmpty()) {
            input.waitEnter();
            return;
        }

        int id = input.readInt("Select ID: ");
        if (id == 0) return;

        Schedule schedule = manager.findById(id);
        if (schedule == null) {
            showMessageAndPause("Schedule not found.");
            return;
        }
        view.printScheduleDetail(color, schedule);
        input.waitEnter();
    }

    // ===== mark / edit / delete ===================================

    private void markAsDone() {
        if (manager.getAllSchedules().isEmpty()) {
            showMessageAndPause("No schedules to update.");
            return;
        }
        view.printSchedules(color, manager.getAllSchedules());
        int id = input.readInt("Enter schedule ID to mark as done: ");
        boolean ok = manager.markAsDone(id);
        showMessageAndPause(ok ? "Schedule marked as DONE." : "Schedule not found.");
    }

    private void editSchedule() {
        if (manager.getAllSchedules().isEmpty()) {
            showMessageAndPause("No schedules to edit.");
            return;
        }

        view.printSchedules(color, manager.getAllSchedules());
        int id = input.readInt("Enter schedule ID to edit: ");
        Schedule schedule = manager.findById(id);
        if (schedule == null) {
            showMessageAndPause("Schedule not found.");
            return;
        }

        // Polymorphic field list — no instanceof, no per-category switch.
        List<EditableField> fields = schedule.getEditableFields();

        while (true) {
            view.printEditMenu(color, schedule, fields);
            int choice = input.readInt("Select field to edit: ");
            if (choice == 0) {
                showMessageAndPause("Schedule updated.");
                return;
            }
            if (choice < 1 || choice > fields.size()) {
                showMessageAndPause("Invalid menu.");
                continue;
            }
            fields.get(choice - 1).edit(input);
        }
    }

    private void deleteSchedule() {
        if (manager.getAllSchedules().isEmpty()) {
            showMessageAndPause("No schedules to delete.");
            return;
        }
        view.printSchedules(color, manager.getAllSchedules());
        int id = input.readInt("Enter schedule ID to delete: ");
        boolean ok = manager.deleteSchedule(id);
        showMessageAndPause(ok ? "Schedule deleted." : "Schedule not found.");
    }

    // ===== theme ==================================================

    private void changeColors() {
        view.printColorMenu(color);
        int menu = input.readInt("Select color: ");
        if (menu == 0) return;

        String newColor = Color.fromMenuChoice(menu);
        if (newColor == null) {
            showMessageAndPause("Invalid color.");
            return;
        }
        color = newColor;
        showMessageAndPause("Theme color changed.");
    }

    // ===== helpers ================================================

    private void showMessageAndPause(String message) {
        view.printMessage(color, message);
        input.waitEnter();
    }
}