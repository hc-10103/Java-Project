package timeSchedule.view;

import timeSchedule.model.Schedule;

import java.util.List;

public class View {

    public void printSchedules(List<Schedule> list) {

        if (list.isEmpty()) {
            System.out.println("No schedules.");
            return;
        }

        System.out.printf("%-3s %-12s %-20s %-12s %-6s %-6s\n",
                "ID", "TYPE", "TITLE", "DATE", "TIME", "STATUS");

        System.out.println("--------------------------------------------------------------");

        for (Schedule s : list) {
            System.out.printf("%-3d %-12s %-20s %-12s %-6s %-6s\n",
                    s.getId(),
                    s.getCategory(),
                    cut(s.getTitle(), 20),
                    s.getFormattedDate(),
                    s.getTime() == null ? "-" : s.getTime(),
                    s.getStatus());
        }

        System.out.println();
    }

    // ⭐ 문자열 길이 제한
    private String cut(String str, int len) {
        if (str == null) return "";
        return str.length() > len ? str.substring(0, len - 3) + "..." : str;
    }
}