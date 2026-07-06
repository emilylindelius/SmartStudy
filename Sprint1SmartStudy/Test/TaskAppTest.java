package Sprint1SmartStudy.Test;

import java.io.File;

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
    private static void testValidTaskCreation() throws Exception {
        Object service = createTaskService();

        boolean result = invokeAddTask(service, "Homework", "High");
        assertTrue(result, "Expected addTask to return true");
        java.util.List<?> tasks = invokeGetTasks(service);
        assertEquals(1, tasks.size(), "Expected one task created");
        Object task = tasks.get(0);
        assertEquals("Homework", invokeGet(task, "getName"), "Expected task name");
        assertEquals("High", invokeGet(task, "getPriority"), "Expected task priority");
    }

    // Test 2: empty task name should be rejected
    private static void testEmptyNameRejected() throws Exception {
        Object service = createTaskService();

        boolean result = invokeAddTask(service, "", "Medium");
        assertFalse(result, "Expected addTask to return false for empty name");
        java.util.List<?> tasks = invokeGetTasks(service);
        assertEquals(0, tasks.size(), "Expected no tasks created");
    }

    // Test 3: saving tasks should create a file
    private static void testSaveCreatesFile() throws Exception {
        Object service = createTaskService();
        invokeAddTask(service, "Study", "Low");
        invokeSaveToFile(service, TEST_FILE);

        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "Expected saveToFile to create the file");
        assertTrue(file.length() > 0, "Expected saved file to be non-empty");
    }

    // Test 4: loading from file should restore saved task
    private static void testLoadRestoresTask() throws Exception {
        Object service = createTaskService();
        invokeAddTask(service, "Read Book", "Low");
        invokeSaveToFile(service, TEST_FILE);

        Object loadedService = createTaskService();
        invokeLoadFromFile(loadedService, TEST_FILE);

        java.util.List<?> tasks = invokeGetTasks(loadedService);
        assertEquals(1, tasks.size(), "Expected one task loaded");
        Object task = tasks.get(0);
        assertEquals("Read Book", invokeGet(task, "getName"), "Expected restored name");
        assertEquals("Low", invokeGet(task, "getPriority"), "Expected restored priority");
    }

    // Reflection helpers to access non-public nested TaskService
    private static Object createTaskService() throws Exception {
        Class<?> cls = Class.forName("Sprint1SmartStudy.SmartStudyPlanner$TaskService");
        java.lang.reflect.Constructor<?> ctor = cls.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }

    private static boolean invokeAddTask(Object service, String name, String priority) throws Exception {
        java.lang.reflect.Method m = service.getClass().getDeclaredMethod("addTask", String.class, String.class);
        m.setAccessible(true);
        return (boolean) m.invoke(service, name, priority);
    }

    private static java.util.List<?> invokeGetTasks(Object service) throws Exception {
        java.lang.reflect.Method m = service.getClass().getDeclaredMethod("getTasks");
        m.setAccessible(true);
        return (java.util.List<?>) m.invoke(service);
    }

    private static void invokeSaveToFile(Object service, String file) throws Exception {
        java.lang.reflect.Method m = service.getClass().getDeclaredMethod("saveToFile", String.class);
        m.setAccessible(true);
        m.invoke(service, file);
    }

    private static void invokeLoadFromFile(Object service, String file) throws Exception {
        java.lang.reflect.Method m = service.getClass().getDeclaredMethod("loadFromFile", String.class);
        m.setAccessible(true);
        m.invoke(service, file);
    }

    private static Object invokeGet(Object obj, String method) throws Exception {
        java.lang.reflect.Method m = obj.getClass().getMethod(method);
        m.setAccessible(true);
        return m.invoke(obj);
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