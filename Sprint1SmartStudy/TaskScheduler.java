package Sprint1SmartStudy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskScheduler {

    private ArrayList<Task> tasks;

    public TaskScheduler(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // =========================
    // T-20: Calendar Support
    // =========================

    // Get tasks for a specific date (MM/dd/yyyy)
    public ArrayList<Task> getTasksForDate(String date) {
        ArrayList<Task> result = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDueDate() != null &&
                task.getDueDate().equals(date)) {
                result.add(task);
            }
        }

        return result;
    }

    // Get tasks in a date range
    public ArrayList<Task> getTasksInRange(String start, String end) {
        ArrayList<Task> result = new ArrayList<>();

        Date startDate = parseDate(start);
        Date endDate = parseDate(end);

        for (Task task : tasks) {

            Date taskDate = parseDate(task.getDueDate());

            if (taskDate == null || startDate == null || endDate == null) {
                continue;
            }

            if (!taskDate.before(startDate) && !taskDate.after(endDate)) {
                result.add(task);
            }
        }

        return result;
    }

    // =========================
    // T-21: Rescheduling Logic
    // =========================

    public void rescheduleTask(Task task, String newDate) {
        if (task == null || newDate == null) return;

        task.setDueDate(newDate);
    }

    // Optional UI refresh version
    public void rescheduleTask(Task task, String newDate, TaskTableModel model) {
        if (task == null || newDate == null) return;

        task.setDueDate(newDate);
        model.fireTableDataChanged();
    }

    // =========================
    // Helper
    // =========================

    private Date parseDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            return sdf.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
