import java.io.File;
import java.io.IOException;

public class TaskAppTest {

    private static final String TEST_FILE = "test-tasks.txt";

    public static void main(String[] args) {
        run("Test 1 - Valid task creation", TaskAppTest::testValidTaskCreation);
        run("Test 2 - Empty task name rejected", TaskAppTest::testEmptyNameRejected);
        run("Test 3 - Save creates file", TaskAppTest::testSaveCreatesFile);
        run("Test 4 - Load restores task", TaskAppTest::testLoadRestoresTask);
    }

    private static void run(String label, TestRunnable test) {
        try {
            cleanup();
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

    @FunctionalInterface
    private interface TestRunnable {
        void run() throws Exception;
    }

    // Test 1: valid task creation should succeed
    private static void testValidTaskCreation() {
        SmartStudyPlanner.TaskService service = new SmartStudyPlanner.TaskService();

        boolean result = service.addTask("Homework", "High");
        assertTrue(result, "Expected addTask to return true");
        assertEquals(1, service.getTasks().size(), "Expected one task created");
        assertEquals("Homework", service.getTasks().get(0).getName(), "Expected task name");
        assertEquals("High", service.getTasks().get(0).getPriority(), "Expected task priority");
    }

    // Test 2: empty task name should be rejected
    private static void testEmptyNameRejected() {
        SmartStudyPlanner.TaskService service = new SmartStudyPlanner.TaskService();

        boolean result = service.addTask("", "Medium");
        assertFalse(result, "Expected addTask to return false for empty name");
        assertEquals(0, service.getTasks().size(), "Expected no tasks created");
    }

    // Test 3: saving tasks should create a file
    private static void testSaveCreatesFile() throws IOException {
        SmartStudyPlanner.TaskService service = new SmartStudyPlanner.TaskService();
        service.addTask("Study", "Low");
        service.saveToFile(TEST_FILE);

        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "Expected saveToFile to create the file");
        assertTrue(file.length() > 0, "Expected saved file to be non-empty");
    }

    // Test 4: loading from file should restore saved task
    private static void testLoadRestoresTask() throws IOException {
        SmartStudyPlanner.TaskService service = new SmartStudyPlanner.TaskService();
        service.addTask("Read Book", "Low");
        service.saveToFile(TEST_FILE);

        SmartStudyPlanner.TaskService loadedService = new SmartStudyPlanner.TaskService();
        loadedService.loadFromFile(TEST_FILE);

        assertEquals(1, loadedService.getTasks().size(), "Expected one task loaded");
        assertEquals("Read Book", loadedService.getTasks().get(0).getName(), "Expected restored name");
        assertEquals("Low", loadedService.getTasks().get(0).getPriority(), "Expected restored priority");
    }

    private static void cleanup() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) throw new AssertionError(message);
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }
}