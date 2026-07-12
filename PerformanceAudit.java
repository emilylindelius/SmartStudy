package Sprint1SmartStudy;

import java.util.ArrayList;

public class PerformanceAudit {

    // Returns the number of completed tasks
    public static int getCompletedTasks(
            ArrayList<Task> tasks) {

        int completed = 0;

        for (Task task : tasks) {

            if (task.getStatus().equalsIgnoreCase("Completed")) {
                completed++;
            }
        }

        return completed;
    }

    // Removes null tasks to improve performance
    public static void cleanTaskList(
            ArrayList<Task> tasks) {

        tasks.removeIf(task -> task == null);
    }

    // Returns the total number of tasks
    public static int totalTasks(
            ArrayList<Task> tasks) {

        return tasks.size();
    }

    // Returns true if there are duplicate task names
    public static boolean hasDuplicateTasks(
            ArrayList<Task> tasks) {

        for (int i = 0; i < tasks.size(); i++) {

            for (int j = i + 1; j < tasks.size(); j++) {

                if (tasks.get(i).getTaskName()
                        .equalsIgnoreCase(tasks.get(j).getTaskName())) {

                    return true;
                }
            }
        }

        return false;
    }
}
