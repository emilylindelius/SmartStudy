package Sprint1SmartStudy;

import java.util.ArrayList;

public class TaskAppUnitTest {

    public static void main(String[] args) {
        run("Test 1 - Mark complete function", TaskAppUnitTest::testMarkCompleteFunction);
        run("Test 2 - Task recurrence option", TaskAppUnitTest::testTaskRecurrenceOption);
        run("Test 3 - Completed panel separation", TaskAppUnitTest::testCompletedPanelSeparation);
    }

    @FunctionalInterface
    private interface TestRunnable {
        void run() throws Exception;
    }

    private static void run(String label, TestRunnable test) {
        try {
            test.run();
            System.out.println(label + ": PASS");
        } catch (AssertionError e) {
            System.out.println(label + ": FAIL");
            System.out.println("  " + e.getMessage());
        } catch (Exception e) {
            System.out.println(label + ": ERROR");
            e.printStackTrace(System.out);
        }
    }

    private static void testMarkCompleteFunction() {
        GraphicalTaskDisplayPanel panel = new GraphicalTaskDisplayPanel();
        panel.setVisible(false);
        panel.setTasksForTest(new ArrayList<Task>());

        Task task = new Task(
                "Finish Assignment",
                "High",
                "06/22/2026",
                "Pending",
                "06/22/2026",
                "School",
                "None");

        panel.addTaskForTest(task);
        panel.markTaskComplete(0);

        assertEquals("Done", panel.getTasks().get(0).getStatus(), "Expected task status to be Done after marking complete");
        assertEquals(0, panel.getActiveTaskCount(), "Expected no active tasks after marking complete");
        assertEquals(1, panel.getCompletedTaskCount(), "Expected one completed task after marking complete");
    }

    private static void testTaskRecurrenceOption() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task recurringTask = new Task(
                "Recurring Study",
                "Medium",
                "06/22/2026",
                "Pending",
                "06/22/2026",
                "School",
                "Daily");

        TaskRecurrenceManager.generateRecurringTasks(tasks, recurringTask);

        assertEquals(10, tasks.size(), "Expected 10 recurring tasks generated for Daily recurrence");
        assertEquals("06/23/2026", tasks.get(0).getDueDate(), "Expected first recurring task to be next day");
        assertEquals("Daily", tasks.get(0).getRecurrence(), "Expected recurrence property to be preserved");
        assertEquals("Pending", tasks.get(0).getStatus(), "Expected recurring tasks to start in Pending status");
    }

    private static void testCompletedPanelSeparation() {
        GraphicalTaskDisplayPanel panel = new GraphicalTaskDisplayPanel();
        panel.setVisible(false);

        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task(
                "Homework",
                "Low",
                "06/22/2026",
                "Pending",
                "06/22/2026",
                "School",
                "None"));
        tasks.add(new Task(
                "Finished Project",
                "Medium",
                "06/20/2026",
                "Done",
                "06/20/2026",
                "School",
                "None"));

        panel.setTasksForTest(tasks);

        assertEquals(1, panel.getActiveTaskCount(), "Expected one active task in UI when one task is pending");
        assertEquals(1, panel.getCompletedTaskCount(), "Expected one completed task in UI when one task is done");
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}
