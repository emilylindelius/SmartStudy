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

    // SAVE ALL TASKS TO STRUCTURED TEXT FILE
    public static void saveTasks(
            ArrayList<Task> tasks) {

        File tempFile = new File(TEMP_FILE_NAME);
        File realFile = new File(FILE_NAME);

        try (FileWriter writer =
                     new FileWriter(tempFile)) {

            SimpleDateFormat sdf =
                    new SimpleDateFormat(
                            "MM/dd/yyyy");
            String createdAt =
                    sdf.format(new Date());

            for (Task task : tasks) {

                writer.write("task_name="
                        + task.getTaskName()
                        + System.lineSeparator());
                writer.write("priority="
                        + task.getPriority()
                        + System.lineSeparator());
                writer.write("due_date="
                        + task.getDueDate()
                        + System.lineSeparator());
                writer.write("status="
                        + task.getStatus()
                        + System.lineSeparator());
                writer.write("created_at="
                        + createdAt
                        + System.lineSeparator());
                writer.write("---"
                        + System.lineSeparator());
                
                writer.write("category="
                		+ task.getCategory()
                		+ System.lineSeparator());
                writer.write("recurrence="
                		+ task.getrecurrence()
                		+ System.lineSeparator());
                writer.write("---"
                		+ System.lineSeparator());
                
            }

            writer.flush();

            if (realFile.exists()) {
                realFile.delete();
            }

            tempFile.renameTo(realFile);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error saving tasks.");
        }
    }

    // LOAD ALL TASKS FROM STRUCTURED TEXT FILE
    public static ArrayList<Task> loadTasks() {

        ArrayList<Task> tasks =
                new ArrayList<Task>();

        File file = new File(FILE_NAME);

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
                    recurrence = "Nonse";
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error loading tasks.");
        }

        return tasks;
    }
}
