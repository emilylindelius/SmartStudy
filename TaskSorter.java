package Sprint1SmartStudy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class TaskSorter {

    // SORT BY PRIORITY THEN BY DUE DATE
    public static void sort(
            ArrayList<Task> tasks) {

        Collections.sort(tasks,
                new Comparator<Task>() {
                    public int compare(
                            Task t1, Task t2) {

                        int priorityCompare =
                                getPriorityValue(t1)
                                - getPriorityValue(t2);

                        if (priorityCompare != 0) {
                            return priorityCompare;
                        }

                        Date date1 =
                                parseDate(
                                        t1.getDueDate());
                        Date date2 =
                                parseDate(
                                        t2.getDueDate());

                        if (date1 == null
                                && date2 == null) {
                            return 0;
                        }
                        if (date1 == null) {
                            return 1;
                        }
                        if (date2 == null) {
                            return -1;
                        }

                        return date1.compareTo(date2);
                    }
                });
    }

    private static int getPriorityValue(
            Task task) {

        if (task.getPriority().equals("High")) {
            return 1;
        }
        if (task.getPriority().equals("Medium")) {
            return 2;
        }
        return 3;
    }

    private static Date parseDate(String date) {

        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat(
                            "MM/dd/yyyy");
            return sdf.parse(date);

        } catch (Exception e) {
            return null;
        }
    }
}
