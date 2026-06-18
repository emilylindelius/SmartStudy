package Sprint1SmartStudy;

import javax.swing.JTable;
import java.util.ArrayList;

public class TaskLogicTest {

    public static void main(String[] args) {
        run("Test 1 - Category field stored and table model exposes category", TaskLogicTest::testCategoryFieldAndTableModel);
        run("Test 2 - Priority filter keeps only High priority tasks", TaskLogicTest::testFilterPriorityHigh);
        run("Test 3 - All filter returns all tasks", TaskLogicTest::testFilterAllReturnsAll);
        run("Test 4 - Mark complete updates task status", TaskLogicTest::testUpdateStatusMarksComplete);
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

    private static void testCategoryFieldAndTableModel() {
        Task task = new Task(
                "Homework",
                "High",
                "06/20/2026",
                "Pending",
                "06/10/2026",
                "School");

        assertEquals("School", task.getCategory(), "Expected task category getter to return the assigned category");

        TaskTableModel model = new TaskTableModel();
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(task);
        model.setTasks(tasks);

        assertEquals("School", model.getValueAt(0, 4), "Expected table model to expose category in the category column");
    }

    private static void testFilterPriorityHigh() {
        TaskTableModel model = new TaskTableModel();
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("Math", "High", "06/20/2026", "Pending", "06/10/2026", "School"));
        tasks.add(new Task("Music", "Medium", "06/21/2026", "Pending", "06/10/2026", "Personal"));
        tasks.add(new Task("Doctor", "Low", "06/22/2026", "Pending", "06/10/2026", "Appointments"));
        model.setTasks(tasks);

        JTable table = new JTable(model);
        TaskFilterSorter sorter = new TaskFilterSorter(table, model);
        sorter.applyFilter("High Priority");

        assertEquals(1, table.getRowCount(), "Expected the High Priority filter to show only one matching row");
        assertEquals("High", table.getValueAt(0, 2), "Expected the remaining row to have High priority");
    }

    private static void testFilterAllReturnsAll() {
        TaskTableModel model = new TaskTableModel();
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("Math", "High", "06/20/2026", "Pending", "06/10/2026", "School"));
        tasks.add(new Task("Music", "Medium", "06/21/2026", "Pending", "06/10/2026", "Personal"));
        tasks.add(new Task("Doctor", "Low", "06/22/2026", "Pending", "06/10/2026", "Appointments"));
        model.setTasks(tasks);

        JTable table = new JTable(model);
        TaskFilterSorter sorter = new TaskFilterSorter(table, model);
        sorter.applyFilter("All");

        assertEquals(3, table.getRowCount(), "Expected the All filter to show every task row");
    }

    private static void testUpdateStatusMarksComplete() {
        Task task = new Task(
                "English",
                "Medium",
                "06/23/2026",
                "Pending",
                "06/10/2026",
                "School");

        TaskTableModel model = new TaskTableModel();
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(task);
        model.setTasks(tasks);

        model.updateStatus(0, "Done");

        assertEquals("Done", task.getStatus(), "Expected task status to update to Done");
        assertEquals("Done", model.getValueAt(0, 3), "Expected table model status cell to reflect the updated status");
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}
