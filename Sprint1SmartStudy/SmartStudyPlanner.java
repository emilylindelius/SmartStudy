package Sprint1SmartStudy;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SmartStudyPlanner {

    // Task class model

    static class Task {
        private String name;
        private String priority;

        public Task(String name, String priority) {
            this.name = name;
            this.priority = priority;
        }

        public String getName() { return name; }
        public String getPriority() { return priority; }

        @Override
        public String toString() {
            return priority + " - " + name;
        }
    }
    // --------------------------------------------------------------------------------------
    // Service management for task creation, sorting, and file handling
    static class TaskService {

        private java.util.List<Task> tasks = new ArrayList<>();

        public boolean addTask(String name, String priority) {
            if (name == null || name.trim().isEmpty()) {
                return false;
            }
            tasks.add(new Task(name, priority));
            return true;
        }

        public java.util.List<Task> getTasks() {
            return tasks;
        }
        
        // Sort tasks by priority: High > Medium > Low
        public void sortTasks() {
            tasks.sort(Comparator.comparingInt(this::getPriorityValue));
        }

        private int getPriorityValue(Task task) {
            switch (task.getPriority()) {
                case "High": return 1;
                case "Medium": return 2;
                default: return 3;
            }
        }

        // File handling - save and load tasks
        public void saveToFile(String filename) {
            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(filename))) {

                for (Task t : tasks) {
                    writer.write(t.toString());
                    writer.newLine();
                }

            } catch (IOException e) {
                System.out.println("Error saving file");
            }
        }

        public void loadFromFile(String filename) {
            tasks.clear();

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(filename))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" - ");
                    tasks.add(new Task(parts[1], parts[0]));
                }

            } catch (IOException e) {
                System.out.println("Error loading file");
            }
        }
    }
    // --------------------------------------------------------------------------------------
    // User interface implementation
    static DefaultListModel<String> taskListModel = new DefaultListModel<>();
    static TaskService service = new TaskService();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Smart Study Planner");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // User input panel
        JPanel inputPanel = new JPanel(null);
        inputPanel.setPreferredSize(new Dimension(500, 140));
        
        JLabel taskLabel = new JLabel("Task Name:");
        taskLabel.setBounds(30, 30, 100, 25);
        inputPanel.add(taskLabel);
        
        JTextField taskField = new JTextField();
        taskField.setBounds(130, 30, 200, 25);
        inputPanel.add(taskField);
        
        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setBounds(30, 70, 100, 25);
        inputPanel.add(priorityLabel);
        
        String[] priorities = {"High", "Medium", "Low"};
        JComboBox<String> priorityBox = new JComboBox<>(priorities);
        priorityBox.setBounds(130, 70, 200, 25);
        inputPanel.add(priorityBox);
        
        JButton saveButton = new JButton("Save Task");
        saveButton.setBounds(130, 110, 120, 30);
        inputPanel.add(saveButton);

        // Display the current list of tasks
        JList<String> taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Load existing tasks from file on startup
        service.loadFromFile("tasks.txt");
        refreshUI();

        // Save button action - add task, sort, save to file, and refresh display
        saveButton.addActionListener(e -> {

            String name = taskField.getText();
            String priority = (String) priorityBox.getSelectedItem();

            if (!service.addTask(name, priority)) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter a valid task name.");
                return;
            }

            service.sortTasks();
            service.saveToFile("tasks.txt");

            refreshUI();
            taskField.setText("");
        });

        frame.setVisible(true);
    }

    // Refresh the task list display after changes
    public static void refreshUI() {
        taskListModel.clear();

        for (Task t : service.getTasks()) {
            taskListModel.addElement(t.toString());
        }
    }
}