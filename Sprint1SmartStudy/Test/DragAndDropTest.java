package Sprint1SmartStudy.Test;

import java.util.ArrayList;
import Sprint1SmartStudy.Task;
import Sprint1SmartStudy.TaskScheduler;
import Sprint1SmartStudy.TaskTableModel;

public class DragAndDropTest {

    public static void main(String[] args) {
        run("Test 1 - Task reschedule via drag and drop", DragAndDropTest::testTaskRescheduleViaDragDrop);
        run("Test 2 - Drag and drop updates table model", DragAndDropTest::testDragDropUpdatesTableModel);
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

    /**
     * Test 1: Verify that a task can be rescheduled via drag and drop
     * This tests the basic functionality of the rescheduleTask method
     */
    private static void testTaskRescheduleViaDragDrop() {
        ArrayList<Task> tasks = new ArrayList<>();
        Task task = new Task(
                "Study for Exam",
                "High",
                "06/22/2026",
                "Pending",
                "06/22/2026",
                "School",
                "None");

        tasks.add(task);
        TaskScheduler scheduler = new TaskScheduler(tasks);

        String originalDate = task.getDueDate();
        String newDate = "06/25/2026";

        // Simulate drag and drop by rescheduling the task
        scheduler.rescheduleTask(task, newDate);

        assertEquals(newDate, task.getDueDate(), 
                "Expected task date to be updated from " + originalDate + " to " + newDate);
        assertEquals(true, !task.getDueDate().equals(originalDate), 
                "Expected task date to have changed");
    }

    /**
     * Test 2: Verify that drag and drop updates the table model correctly
     */
    private static void testDragDropUpdatesTableModel() {
        ArrayList<Task> tasks = new ArrayList<>();
        Task task = new Task(
                "Project",
                "High",
                "06/22/2026",
                "Pending",
                "06/22/2026",
                "Work",
                "None");

        tasks.add(task);
        
        TaskTableModel tableModel = new TaskTableModel();
        tableModel.setTasks(tasks);

        TaskScheduler scheduler = new TaskScheduler(tasks);

        // Get initial row count
        int initialRowCount = tableModel.getRowCount();
        assertEquals(1, initialRowCount, 
                "Expected table model to have 1 row initially");

        // Reschedule task with table model refresh
        scheduler.rescheduleTask(task, "06/29/2026", tableModel);

        assertEquals("06/29/2026", task.getDueDate(), 
                "Expected task date to be updated to 06/29/2026");
        assertEquals(1, tableModel.getRowCount(), 
                "Expected table model to still have 1 row after reschedule");
    }

    /**
     * Helper assertion method
     */
    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}
