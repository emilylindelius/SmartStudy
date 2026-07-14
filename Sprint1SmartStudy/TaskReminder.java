package Sprint1SmartStudy;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TaskReminder {

    // Returns all tasks due within the specified number of days
    public static ArrayList<Task> getUpcomingTasks(
            ArrayList<Task> tasks,
            int daysAhead) {

        ArrayList<Task> reminders = new ArrayList<>();
        Date today = new Date();

        for (Task task : tasks) {

            Date dueDate = parseDate(task.getDueDate());

            if (dueDate == null) {
                continue;
            }

            long difference = dueDate.getTime() - today.getTime();
            long days = TimeUnit.MILLISECONDS.toDays(difference);

            if (days >= 0 && days <= daysAhead) {
                reminders.add(task);
            }
        }

        return reminders;
    }

    private static Date parseDate(String date) {

        try {

            SimpleDateFormat sdf =
                    new SimpleDateFormat("MM/dd/yyyy");

            return sdf.parse(date);

        } catch (Exception e) {

            return null;
        }
    }
}
