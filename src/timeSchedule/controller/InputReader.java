package timeSchedule.controller;

import timeSchedule.model.Priority;
import timeSchedule.model.ScheduleInput;
import timeSchedule.model.Status;
import timeSchedule.view.View;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;


public class InputReader implements ScheduleInput {
    private final Scanner scanner;
    private final View view;

    public InputReader(Scanner scanner, View view) {
        this.scanner = scanner;
        this.view = view;
    }

    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
    }

    @Override
    public LocalDate readDateWithoutYear(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String[] parts = scanner.nextLine().split("-");
                if (parts.length != 2) throw new IllegalArgumentException();
                int month = Integer.parseInt(parts[0]);
                int day   = Integer.parseInt(parts[1]);
                return LocalDate.of(LocalDate.now().getYear(), month, day);
            } catch (Exception e) {
                System.out.println("Invalid date format. Use MM-dd.");
            }
        }
    }

    @Override
    public LocalTime readTime(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid time format. Use HH:mm.");
            }
        }
    }

    @Override
    public Priority readPriority() {
        while (true) {
            view.printPriorityGuide();
            Priority p = Priority.fromMenuChoice(readInt("Select priority: "));
            if (p != null) return p;
            System.out.println("Invalid priority.");
        }
    }

    @Override
    public Status readStatus() {
        while (true) {
            System.out.println("Status:");
            System.out.println("1. TODO");
            System.out.println("2. DONE");
            int choice = readInt("Select status: ");
            if (choice == 1) return Status.TODO;
            if (choice == 2) return Status.DONE;
            System.out.println("Invalid status.");
        }
    }

    @Override
    public DayOfWeek readDayOfWeek() {
        while (true) {
            view.printDayOfWeekGuide();
            int choice = readInt("Select day: ");
            if (choice >= 1 && choice <= 7) return DayOfWeek.of(choice);
            System.out.println("Invalid day.");
        }
    }

    public void waitEnter() {
        System.out.println("Press Enter to continue.");
        scanner.nextLine();
    }
}