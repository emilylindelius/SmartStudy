package Sprint1SmartStudy;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskStorage {

    private static final String FILE_NAME =
            "tasks.txt";
    private static final String TEMP_FILE_NAME =
            "tasks_temp.txt";
    private static final String APP_FOLDER_NAME =
            "SmartStudy";

    public static File getStorageDirectory() {
        String appData = System.getenv("APPDATA");
        File baseDirectory;

        if (appData != null && !appData.isEmpty()) {
            baseDirectory = new File(appData, APP_FOLDER_NAME);
        } else {
            baseDirectory = new File(
                    System.getProperty("user.home"),
                    ".smartstudy");
        }

        if (!baseDirectory.exists()) {
            baseDirectory.mkdirs();
        }

        return baseDirectory;
    }

    public static File getStorageFile() {
        File preferredFile = new File(
                getStorageDirectory(), FILE_NAME);

        if (!preferredFile.exists()) {
            File legacyFile = new File(FILE_NAME);
            if (legacyFile.exists()) {
                legacyFile.renameTo(preferredFile);
            }
        }

        return preferredFile;
    }

    public static File getTempStorageFile() {
        return new File(getStorageDirectory(), TEMP_FILE_NAME);
    }

    // SAVE ALL TASKS TO STRUCTURED TEXT FILE
    public static void saveTasks(ArrayList<Task> tasks) {

        File tempFile = getTempStorageFile();
        File realFile = getStorageFile();

        System.out.println("=== SAVE DEBUG ===");
        System.out.println("Storage directory: " + getStorageDirectory().getAbsolutePath());
        System.out.println("Directory exists? " + getStorageDirectory().exists());
        System.out.println("Target file: " + realFile.getAbsolutePath());
        System.out.println("Number of tasks to save: " + tasks.size());

        try (FileWriter writer = new FileWriter(tempFile)) {

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String createdAt = sdf.format(new Date());

            for (Task task : tasks) {
                writer.write("task_name=" + task.getTaskName() + System.lineSeparator());
                writer.write("priority=" + task.getPriority() + System.lineSeparator());
                writer.write("due_date=" + task.getDueDate() + System.lineSeparator());
                writer.write("status=" + task.getStatus() + System.lineSeparator());
                writer.write("created_at=" + createdAt + System.lineSeparator());
                writer.write("category=" + task.getCategory() + System.lineSeparator());
                writer.write("recurrence=" + task.getRecurrence() + System.lineSeparator());
                writer.write("---" + System.lineSeparator());
            }

            writer.flush();

        } catch (IOException e) {
            System.out.println("SAVE FAILED WRITING TEMP FILE:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving tasks: " + e.getMessage());
            return;
        }

        System.out.println("Temp file written? " + tempFile.exists() + " size=" + tempFile.length());

        if (realFile.exists()) {
            boolean deleted = realFile.delete();
            System.out.println("Old real file deleted? " + deleted);
        }

        boolean renamed = tempFile.renameTo(realFile);
        System.out.println("Renamed temp to real? " + renamed);
        System.out.println("Real file exists after rename? " + realFile.exists());
        System.out.println("=== END SAVE DEBUG ===");
    }

    // LOAD ALL TASKS FROM STRUCTURED TEXT FILE
    public static ArrayList<Task> loadTasks() {

        ArrayList<Task> tasks =
                new ArrayList<Task>();

        File file = getStorageFile();

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;
            String taskName  = "";
            String priority  = "";
            String dueDate   = "";
            String status    = "Pending";
            String createdAt = "";
            String category  = "General";
            String recurrence = "None";

            while ((line =
                    reader.readLine()) != null) {

                if (line.startsWith(
                        "task_name=")) {
                    taskName =
                            line.substring(10);

                } else if (line.startsWith(
                        "priority=")) {
                    priority =
                            line.substring(9);

                } else if (line.startsWith(
                        "due_date=")) {
                    dueDate =
                            line.substring(9);

                } else if (line.startsWith(
                        "status=")) {
                    status =
                            line.substring(7);

                } else if (line.startsWith(
                        "created_at=")) {
                    createdAt =
                            line.substring(11);

                } else if (line.startsWith(
                			"category=")) {
                	category = 
                			line.substring(9);
                	
                } else if (line.startsWith(
                		"recurrence=")) {
                	recurrence = 
                			line.substring(11);
                	
                }
                else if (line.equals("---")) {

                    if (!taskName.isEmpty()
                            && !priority.isEmpty()
                            && !dueDate.isEmpty()) {

                        tasks.add(new Task(
                                taskName,
                                priority,
                                dueDate,
                                status,
                                createdAt,
                                category,
                                recurrence));
                    }

                    taskName   = "";
                    priority   = "";
                    dueDate    = "";
                    status     = "Pending";
                    createdAt  = "";
                    category   = "General";
                    recurrence = "None";
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error loading tasks.");
        }

        return tasks;
    }
}
