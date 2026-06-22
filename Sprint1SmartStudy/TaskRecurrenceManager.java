package Sprint1SmartStudy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaskRecurrenceManager {

    // T-16
    public static boolean isValidRecurrence(String recurrence) {

        if (recurrence == null) {
            return false;
        }

        return recurrence.equals("None")
                || recurrence.equals("Daily")
                || recurrence.equals("Weekly");
    }

    // T-17
    public static void generateRecurringTasks(
            ArrayList<Task> tasks,
            Task originalTask) {

        if (originalTask == null) {
            return;
        }

        String recurrence =
                originalTask.getRecurrence();

        if (!isValidRecurrence(recurrence)
                || recurrence.equals("None")) {
            return;
        }

        try {

            SimpleDateFormat sdf =
                    new SimpleDateFormat("MM/dd/yyyy");

            Date dueDate =
                    sdf.parse(
                            originalTask.getDueDate());

            Calendar calendar =
                    Calendar.getInstance();

            calendar.setTime(dueDate);

            // Generate 10 future occurrences
            for (int i = 0; i < 10; i++) {

                if (recurrence.equals("Daily")) {

                    calendar.add(
                            Calendar.DAY_OF_MONTH,
                            1);

                } else if (recurrence.equals("Weekly")) {

                    calendar.add(
                            Calendar.WEEK_OF_YEAR,
                            1);
                }

                Task recurringTask =
                        new Task(
                                originalTask.getTaskName(),
                                originalTask.getPriority(),
                                sdf.format(
                                        calendar.getTime()),
                                "Pending",
                                originalTask.getCreatedAt(),
                                originalTask.getCategory(),
                                originalTask.getRecurrence());

                tasks.add(recurringTask);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error generating recurring tasks.");
        }
    }
}
