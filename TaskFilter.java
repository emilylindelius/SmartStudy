import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SmartStudyPlanner {

    // Task model
    static class Task {

        private int taskId;
        private String name;
        private String priority;
        private String dueDate;
        private String status;
        private String category;

        public Task(int taskId, String name, String priority,
                    String dueDate, String status, String category) {

            this.taskId = taskId;
            this.name = name;
            this.priority = priority;
            this.dueDate = dueDate;
            this.status = status;
            this.category = category;
        }

        public int getTaskId() { return taskId; }
        public String getName() { return name; }
        public String getPriority() { return priority; }
        public String getDueDate() { return dueDate; }
        public String getStatus() { return status; }
        public String getCategory() { return category; }

        @Override
        public String toString() {
            return priority + " | " +
                    dueDate + " | " +
                    status + " | " +
                    name;
        }
    }

    // Service class
    static class TaskService {

        private List<Task> tasks = new ArrayList<>();
        private int nextId = 1;

        public boolean addTask(String name,
                               String priority,
                               String dueDate,
                               String category) {

            if (name == null || name.trim().isEmpty()) {
                return false;
            }

            Task task = new Task(
                    nextId++,
                    name,
                    priority,
                    dueDate,
                    "Not Started",
                    category
            );

            tasks.add(task);
            return true;
        }

        public List<Task> getTasks() {
            return tasks;
        }

        public void sortTasks() {
            tasks.sort((t1, t2) -> {
                int priorityCompare =
                        Integer.compare(
                                getPriorityValue(t1),
                                getPriorityValue(t2));

                if (priorityCompare != 0) {
                    return priorityCompare;
                }

                return t1.getDueDate().compareTo(t2.getDueDate());
            });
        }

        private int getPriorityValue(Task task) {
            switch (task.getPriority()) {
                case "High": return 1;
                case "Medium": return 2;
                default: return 3;
            }
        }

        public void saveToFile(String filename) {

            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(filename))) {

                for (Task task : tasks) {

                    writer.write(
                            task.getTaskId() + "," +
                                    task.getName() + "," +
                                    task.getPriority() + "," +
                                    task.getDueDate() + "," +
                                    task.getStatus() + "," +
                                    task.getCategory()
                    );

                    writer.newLine();
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving tasks.");
            }
        }

        public void loadFromFile(String filename) {

            tasks.clear();

            File file = new File(filename);
            if (!file.exists()) return;

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split(",");

                    if (parts.length == 6) {

                        int id = Integer.parseInt(parts[0]);

                        tasks.add(new Task(
                                id,
                                parts[1],
                                parts[2],
                                parts[3],
                                parts[4],
                                parts[5]
                        ));

                        if (id >= nextId) {
                            nextId = id + 1;
                        }
                    }
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading tasks.");
            }
        }
    }

    static DefaultListModel<String> taskListModel = new DefaultListModel<>();
    static TaskService service = new TaskService();
    static JComboBox<String> filterBox = new JComboBox<>();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Smart Study Planner");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(null);
        inputPanel.setPreferredSize(new Dimension(600, 230));

        JLabel taskLabel = new JLabel("Task Name:");
        taskLabel.setBounds(30, 20, 100, 25);
        inputPanel.add(taskLabel);

        JTextField taskField = new JTextField();
        taskField.setBounds(130, 20, 200, 25);
        inputPanel.add(taskField);

        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setBounds(30, 60, 100, 25);
        inputPanel.add(priorityLabel);

        String[] priorities = {"High", "Medium", "Low"};
        JComboBox<String> priorityBox = new JComboBox<>(priorities);
        priorityBox.setBounds(130, 60, 200, 25);
        inputPanel.add(priorityBox);

        JLabel dueDateLabel = new JLabel("Due Date:");
        dueDateLabel.setBounds(30, 100, 100, 25);
        inputPanel.add(dueDateLabel);

        JTextField dueDateField = new JTextField();
        dueDateField.setBounds(130, 100, 200, 25);
        inputPanel.add(dueDateField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(30, 140, 100, 25);
        inputPanel.add(categoryLabel);

        JTextField categoryField = new JTextField();
        categoryField.setBounds(130, 140, 200, 25);
        inputPanel.add(categoryField);

        JButton saveButton = new JButton("Save Task");
        saveButton.setBounds(130, 180, 120, 30);
        inputPanel.add(saveButton);

        filterBox.setBounds(360, 60, 180, 25);
        inputPanel.add(filterBox);

        filterBox.addItem("All");

        JList<String> taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        service.loadFromFile("tasks.txt");
        refreshUI();

        filterBox.addActionListener(e -> refreshUI());

        saveButton.addActionListener(e -> {

            String name = taskField.getText();
            String priority = (String) priorityBox.getSelectedItem();
            String dueDate = dueDateField.getText();
            String category = categoryField.getText();

            if (!service.addTask(name, priority, dueDate, category)) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid task name.");
                return;
            }

            service.sortTasks();
            service.saveToFile("tasks.txt");

            refreshUI();

            taskField.setText("");
            dueDateField.setText("");
            categoryField.setText("");
        });

        frame.setVisible(true);
    }

    public static void refreshUI() {

        taskListModel.clear();

        String selectedFilter = (String) filterBox.getSelectedItem();

        Map<String, List<Task>> grouped = new LinkedHashMap<>();

        for (Task task : service.getTasks()) {

            if (selectedFilter != null &&
                    !selectedFilter.equals("All") &&
                    !task.getCategory().equals(selectedFilter)) {
                continue;
            }

            grouped.computeIfAbsent(task.getCategory(), k -> new ArrayList<>())
                    .add(task);
        }

        for (String category : grouped.keySet()) {

            taskListModel.addElement("=== " + category + " ===");

            for (Task task : grouped.get(category)) {
                taskListModel.addElement("   " + task.toString());
            }
        }

        if (filterBox.getItemCount() <= 1) {
            for (Task task : service.getTasks()) {

                boolean exists = false;

                for (int i = 0; i < filterBox.getItemCount(); i++) {
                    if (filterBox.getItemAt(i).equals(task.getCategory())) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    filterBox.addItem(task.getCategory());
                }
            }
        }
    }
}
