package Sprint1SmartStudy;

public class UnitTests_Gets {

    public static void main(String[] args) {

        System.out.println("Running Manual Tests...\n");

        testGetPriority();
        testGetDueDate();
        testGetStatus();
        testGetCreatedAt();

        System.out.println("\nAll tests completed.");
    }

    
    // Test Case 1: getPriority()
    
    static void testGetPriority() {

        // Create Task object
        Task task = new Task(
                "Study Java",
                "High",
                "2026-06-10",
                "Not Started",
                "2026-06-01",
                "School"
        );

        // Call method
        String result = task.getPriority();

        // Verify result
        if (!result.equals("High")) {
            System.out.println("FAIL: getPriority() | Got: " + result);
        } else {
            System.out.println("PASS: getPriority()");
        }
    }

    
    // Test Case 2: getDueDate()
    
    static void testGetDueDate() {

        Task task = new Task(
                "Write Paper",
                "Medium",
                "2026-06-15",
                "In Progress",
                "2026-06-01",
                "Appointments"
        );

        String result = task.getDueDate();

        if (!result.equals("2026-06-15")) {
            System.out.println("FAIL: getDueDate() | Got: " + result);
        } else {
            System.out.println("PASS: getDueDate()");
        }
    }

   
    // Test Case 3: getStatus()
  
    static void testGetStatus() {

        Task task = new Task(
                "Do Homework",
                "Low",
                "2026-06-20",
                "Completed",
                "2026-06-01",
                "Personal"
        );

        String result = task.getStatus();

        if (!result.equals("Completed")) {
            System.out.println("FAIL: getStatus() | Got: " + result);
        } else {
            System.out.println("PASS: getStatus()");
        }
    }

    // Test Case 4: getCreatedAt()

    static void testGetCreatedAt() {

        Task task = new Task(
                "Prepare Presentation",
                "High",
                "2026-06-12",
                "Not Started",
                "2026-06-01",
                "School"
        );

        String result = task.getCreatedAt();

        if (!result.equals("2026-06-01")) {
            System.out.println("FAIL: getCreatedAt() | Got: " + result);
        } else {
            System.out.println("PASS: getCreatedAt()");
        }
    }
}