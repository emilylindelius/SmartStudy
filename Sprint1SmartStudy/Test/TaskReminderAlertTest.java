package Sprint1SmartStudy.Test;

import Sprint1SmartStudy.Task;
import Sprint1SmartStudy.TaskReminder;
import Sprint1SmartStudy.TaskAlertPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaskReminderAlertTest {

    public static void main(String[] args) {
        run("Test 1 - Upcoming tasks within range", TaskReminderAlertTest::testUpcomingTasksWithinRange);
        run("Test 2 - Task alert panel popup menu items", TaskReminderAlertTest::testTaskAlertPanelPopupMenuItems);
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

    private static void testUpcomingTasksWithinRange() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task Today", "High", formatRelativeDate(0), "Pending", formatRelativeDate(-1), "School", "None"));
        tasks.add(new Task("Task In 2 Days", "Medium", formatRelativeDate(2), "Pending", formatRelativeDate(-1), "School", "None"));
        tasks.add(new Task("Task In 5 Days", "Low", formatRelativeDate(5), "Pending", formatRelativeDate(-1), "School", "None"));
        tasks.add(new Task("Task Yesterday", "Low", formatRelativeDate(-1), "Pending", formatRelativeDate(-2), "School", "None"));
        tasks.add(new Task("Task In 8 Days", "High", formatRelativeDate(8), "Pending", formatRelativeDate(-1), "School", "None"));
        tasks.add(new Task("Invalid Date", "Low", "13/99/2026", "Pending", formatRelativeDate(-1), "School", "None"));

        ArrayList<Task> upcoming = TaskReminder.getUpcomingTasks(tasks, 5);

        assertEquals(3, upcoming.size(), "Expected three upcoming tasks within 5 days");
        assertContains(upcoming, "Task Today", "Expected Task Today to be included");
        assertContains(upcoming, "Task In 2 Days", "Expected Task In 2 Days to be included");
        assertContains(upcoming, "Task In 5 Days", "Expected Task In 5 Days to be included");
        assertDoesNotContain(upcoming, "Task Yesterday", "Expected Task Yesterday to be excluded");
        assertDoesNotContain(upcoming, "Task In 8 Days", "Expected Task In 8 Days to be excluded");
        assertDoesNotContain(upcoming, "Invalid Date", "Expected invalid date task to be excluded");
    }

    private static void testTaskAlertPanelPopupMenuItems() throws Exception {
        System.setProperty("java.awt.headless", "true");
        TaskAlertPanel panel = new TaskAlertPanel();

        Border border = panel.getBorder();
        assertTrue(border instanceof TitledBorder, "Expected panel to use a titled border");
        assertEquals("Tasks Due Today", ((TitledBorder) border).getTitle(), "Expected titled border text");

        Field popupField = TaskAlertPanel.class.getDeclaredField("popupMenu");
        popupField.setAccessible(true);
        JPopupMenu popupMenu = (JPopupMenu) popupField.get(panel);

        assertNotNull(popupMenu, "Expected popup menu to be initialized");

        java.util.List<String> itemTexts = new ArrayList<>();
        for (java.awt.Component component : popupMenu.getComponents()) {
            if (component instanceof JMenuItem) {
                itemTexts.add(((JMenuItem) component).getText());
            }
        }

        assertEquals(4, itemTexts.size(), "Expected four items in the alert popup menu");
        assertContainsString(itemTexts, "Snooze 10 min", "Expected Snooze menu item");
        assertContainsString(itemTexts, "Dismiss Alert", "Expected Dismiss Alert menu item");
        assertContainsString(itemTexts, "View Details", "Expected View Details menu item");
        assertContainsString(itemTexts, "Mark Complete", "Expected Mark Complete menu item");
    }

    private static String formatRelativeDate(int offsetDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, offsetDays);
        Date date = calendar.getTime();
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    private static void assertContains(java.util.List<Task> tasks, String name, String message) {
        for (Task task : tasks) {
            if (name.equals(task.getTaskName())) {
                return;
            }
        }
        throw new AssertionError(message + " (missing=" + name + ")");
    }

    private static void assertDoesNotContain(java.util.List<Task> tasks, String name, String message) {
        for (Task task : tasks) {
            if (name.equals(task.getTaskName())) {
                throw new AssertionError(message + " (found=" + name + ")");
            }
        }
    }

    private static void assertContainsString(java.util.List<String> values, String expected, String message) {
        for (String value : values) {
            if (expected.equals(value)) {
                return;
            }
        }
        throw new AssertionError(message + " (missing=" + expected + ")");
    }

    private static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new AssertionError(message);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}
