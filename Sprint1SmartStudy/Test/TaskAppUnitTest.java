package Sprint1SmartStudy.Test;

import Sprint1SmartStudy.Task;
import Sprint1SmartStudy.TaskRecurrenceManager;
import Sprint1SmartStudy.GraphicalTaskDisplayPanel;
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

    private static void testMarkCompleteFunction() throws Exception {
        GraphicalTaskDisplayPanel panel = new GraphicalTaskDisplayPanel();
        panel.setVisible(false);
        invokeSetTasksForTest(panel, new ArrayList<Task>());

        Task task = new Task(
                "Finish Assignment",
                "High",
                "06/22/2026",
                "Pending",
                "06/22/2026",
                "School",
                "None");

        invokeAddTaskForTest(panel, task);
        invokeMarkTaskComplete(panel, 0);

        assertEquals("Done", invokeGetTasks(panel).get(0).getStatus(), "Expected task status to be Done after marking complete");
        assertEquals(0, invokeGetActiveTaskCount(panel), "Expected no active tasks after marking complete");
        assertEquals(1, invokeGetCompletedTaskCount(panel), "Expected one completed task after marking complete");
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

    private static void testCompletedPanelSeparation() throws Exception {
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

        invokeSetTasksForTest(panel, tasks);

        assertEquals(1, invokeGetActiveTaskCount(panel), "Expected one active task in UI when one task is pending");
        assertEquals(1, invokeGetCompletedTaskCount(panel), "Expected one completed task in UI when one task is done");
    }

    private static void invokeSetTasksForTest(GraphicalTaskDisplayPanel panel, ArrayList<Task> tasks) throws Exception {
        java.lang.reflect.Method method = GraphicalTaskDisplayPanel.class.getDeclaredMethod("setTasksForTest", ArrayList.class);
        method.setAccessible(true);
        method.invoke(panel, tasks);
    }

    private static void invokeAddTaskForTest(GraphicalTaskDisplayPanel panel, Task task) throws Exception {
        java.lang.reflect.Method method = GraphicalTaskDisplayPanel.class.getDeclaredMethod("addTaskForTest", Task.class);
        method.setAccessible(true);
        method.invoke(panel, task);
    }

    private static void invokeMarkTaskComplete(GraphicalTaskDisplayPanel panel, int modelRow) throws Exception {
        java.lang.reflect.Method method = GraphicalTaskDisplayPanel.class.getDeclaredMethod("markTaskComplete", int.class);
        method.setAccessible(true);
        method.invoke(panel, modelRow);
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Task> invokeGetTasks(GraphicalTaskDisplayPanel panel) throws Exception {
        java.lang.reflect.Method method = GraphicalTaskDisplayPanel.class.getDeclaredMethod("getTasks");
        method.setAccessible(true);
        return (ArrayList<Task>) method.invoke(panel);
    }

    private static int invokeGetActiveTaskCount(GraphicalTaskDisplayPanel panel) throws Exception {
        java.lang.reflect.Method method = GraphicalTaskDisplayPanel.class.getDeclaredMethod("getActiveTaskCount");
        method.setAccessible(true);
        return (int) method.invoke(panel);
    }

    private static int invokeGetCompletedTaskCount(GraphicalTaskDisplayPanel panel) throws Exception {
        java.lang.reflect.Method method = GraphicalTaskDisplayPanel.class.getDeclaredMethod("getCompletedTaskCount");
        method.setAccessible(true);
        return (int) method.invoke(panel);
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}
