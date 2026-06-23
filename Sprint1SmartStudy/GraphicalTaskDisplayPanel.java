package Sprint1SmartStudy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GraphicalTaskDisplayPanel
        extends JFrame {

    private static final long serialVersionUID
            = 1L;

    private JPanel contentPane;
    private JTable taskTable;
    private JTable completedTable;
    private TaskTableModel tableModel;
    private TaskTableModel completedModel;
    private TaskFilterSorter filterSorter;
    private ArrayList<Task> tasks;

    public static void main(String[] args) {

        EventQueue.invokeLater(
                new Runnable() {
                    public void run() {
                        try {
                            GraphicalTaskDisplayPanel
                                    frame =
                                    new GraphicalTaskDisplayPanel();
                            frame.setVisible(
                                    true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public GraphicalTaskDisplayPanel() {

        setTitle("Smart Study Planner");
        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 970, 750);

        contentPane = new JPanel();
        contentPane.setBorder(
                new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // APP TITLE
        JLabel lblTitle =
                new JLabel("Smart Study");
        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER);
        lblTitle.setFont(
                lblTitle.getFont()
                .deriveFont(16f));
        lblTitle.setBounds(20, 15, 200, 30);
        contentPane.add(lblTitle);

        // SORT DROPDOWN
        JLabel lblSort =
                new JLabel("Sort by:");
        lblSort.setBounds(600, 18, 60, 22);
        contentPane.add(lblSort);

        JComboBox<String> sortbyBox =
                new JComboBox<String>();
        sortbyBox.addItem("Due Date");
        sortbyBox.addItem("Priority");
        sortbyBox.addItem("Status");
        sortbyBox.setBounds(665, 18, 120, 22);
        contentPane.add(sortbyBox);

        // FILTER DROPDOWN
        JLabel lblFilter =
                new JLabel("Filter by:");
        lblFilter.setBounds(600, 50, 65, 22);
        contentPane.add(lblFilter);

        JComboBox<String> filterbyBox =
                new JComboBox<String>();
        filterbyBox.addItem("All");
        filterbyBox.addItem("High Priority");
        filterbyBox.addItem("Medium Priority");
        filterbyBox.addItem("Low Priority");
        filterbyBox.addItem("Due Today");
        filterbyBox.setBounds(665, 50, 150, 22);
        contentPane.add(filterbyBox);

        // ADD TASK BUTTON
        JButton btnAddTask =
                new JButton("Add Task");
        btnAddTask.setBounds(
                840, 18, 100, 25);
        contentPane.add(btnAddTask);

        // MARK COMPLETE CHECKBOX
        JCheckBox chckbxMarkComplete =
                new JCheckBox(
                        "Mark Task as Complete");
        chckbxMarkComplete.setBounds(
                665, 82, 250, 23);
        contentPane.add(chckbxMarkComplete);

        // DELETE TASK BUTTON
        JButton btnDeleteTask =
                new JButton("Delete Task");
        btnDeleteTask.setBounds(
                840, 50, 100, 25);
        contentPane.add(btnDeleteTask);

        // ACTIVE TASKS LABEL
        JLabel lblActiveTasks =
                new JLabel("Active Tasks");
        lblActiveTasks.setFont(
                lblActiveTasks.getFont()
                .deriveFont(13f));
        lblActiveTasks.setBounds(20, 115, 200, 20);
        contentPane.add(lblActiveTasks);

        // ACTIVE TASKS TABLE
        tableModel = new TaskTableModel();
        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(
                ListSelectionModel
                .SINGLE_SELECTION);
        taskTable.setRowHeight(24);
        taskTable.getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(taskTable);
        scrollPane.setBounds(
                20, 138, 920, 250);
        contentPane.add(scrollPane);

        // T-15 COMPLETED TASKS SECTION
        JLabel lblCompletedTasks =
                new JLabel("Completed Tasks");
        lblCompletedTasks.setFont(
                lblCompletedTasks.getFont()
                .deriveFont(13f));
        lblCompletedTasks.setForeground(
                new Color(0, 128, 0));
        lblCompletedTasks.setBounds(
                20, 400, 200, 20);
        contentPane.add(lblCompletedTasks);

        // DELETE COMPLETED BUTTON
        JButton btnDeleteCompleted =
                new JButton("Delete Completed");
        btnDeleteCompleted.setBounds(
                840, 400, 100, 25);
        contentPane.add(btnDeleteCompleted);

        // T-15 COMPLETED TASKS TABLE
        completedModel = new TaskTableModel();
        completedTable = new JTable(
                completedModel);
        completedTable.setSelectionMode(
                ListSelectionModel
                .SINGLE_SELECTION);
        completedTable.setRowHeight(24);
        completedTable.getTableHeader()
                .setReorderingAllowed(false);
        completedTable.setBackground(
                new Color(240, 255, 240));

        JScrollPane completedScrollPane =
                new JScrollPane(completedTable);
        completedScrollPane.setBounds(
                20, 425, 920, 250);
        contentPane.add(completedScrollPane);

        // LOAD TASKS FROM FILE
        tasks = TaskStorage.loadTasks();
        TaskSorter.sort(tasks);
        refreshBothTables();

        // SETUP FILTER AND SORTER
        filterSorter = new TaskFilterSorter(
                taskTable, tableModel);

        // SORT ACTION LISTENER
        sortbyBox.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {
                        filterSorter.applySort(
                                (String) sortbyBox
                                .getSelectedItem());
                    }
                });

        // FILTER ACTION LISTENER
        filterbyBox.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {
                        filterSorter.applyFilter(
                                (String) filterbyBox
                                .getSelectedItem());
                    }
                });

        // ADD TASK ACTION LISTENER
        btnAddTask.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {
                        openAddTaskDialog();
                    }
                });

        // DELETE TASK ACTION LISTENER
        btnDeleteTask.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        int selectedRow =
                                taskTable
                                .getSelectedRow();

                        if (selectedRow < 0) {
                            JOptionPane
                                .showMessageDialog(
                                    null,
                                    "Please select "
                                    + "a task to "
                                    + "delete.");
                            return;
                        }

                        int modelRow =
                                taskTable
                                .convertRowIndexToModel(
                                        selectedRow);

                        tasks.remove(modelRow);
                        TaskStorage.saveTasks(
                                tasks);
                        refreshBothTables();
                    }
                });

        // MARK COMPLETE ACTION LISTENER
        // T-15 MOVES TASK TO COMPLETED TABLE
        chckbxMarkComplete.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        int selectedRow =
                                taskTable
                                .getSelectedRow();

                        if (selectedRow >= 0) {

                            int modelRow =
                                    taskTable
                                    .convertRowIndexToModel(
                                            selectedRow);

                            tasks.get(modelRow)
                                .setStatus("Done");

                            TaskStorage.saveTasks(
                                    tasks);

                            refreshBothTables();

                            chckbxMarkComplete
                                .setSelected(false);

                            JOptionPane
                                .showMessageDialog(
                                    null,
                                    "Task marked "
                                    + "as complete "
                                    + "and moved to "
                                    + "Completed "
                                    + "section.");

                        } else {
                            JOptionPane
                                .showMessageDialog(
                                    null,
                                    "Please select "
                                    + "a task first.");
                            chckbxMarkComplete
                                .setSelected(false);
                        }
                    }
                });

        // DELETE COMPLETED TASK LISTENER
        btnDeleteCompleted.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        int selectedRow =
                                completedTable
                                .getSelectedRow();

                        if (selectedRow < 0) {
                            JOptionPane
                                .showMessageDialog(
                                    null,
                                    "Please select "
                                    + "a completed "
                                    + "task to delete.");
                            return;
                        }

                        ArrayList<Task> completed =
                                getCompletedTasks();

                        Task toRemove =
                                completed.get(
                                        selectedRow);

                        tasks.remove(toRemove);
                        TaskStorage.saveTasks(
                                tasks);
                        refreshBothTables();
                    }
                });
    }

    // T-15 SEPARATES ACTIVE AND COMPLETED TASKS
    private void refreshBothTables() {

        ArrayList<Task> activeTasks =
                new ArrayList<Task>();
        ArrayList<Task> completedTasks =
                new ArrayList<Task>();

        for (Task task : tasks) {
            if (task.getStatus().equals("Done")) {
                completedTasks.add(task);
            } else {
                activeTasks.add(task);
            }
        }

        tableModel.setTasks(activeTasks);
        completedModel.setTasks(completedTasks);
    }

    // HELPER TO GET COMPLETED TASKS LIST
    private ArrayList<Task> getCompletedTasks() {

        ArrayList<Task> completedTasks =
                new ArrayList<Task>();

        for (Task task : tasks) {
            if (task.getStatus().equals("Done")) {
                completedTasks.add(task);
            }
        }

        return completedTasks;
    }

    // OPENS A CLEAN POPUP TO ADD A TASK
    private void openAddTaskDialog() {

        JDialog dialog = new JDialog(
                this, "Add New Task", true);
        dialog.setSize(360, 340);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel nameLabel =
                new JLabel("Task Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        dialog.add(nameLabel);

        JTextField nameField =
                new JTextField();
        nameField.setBounds(130, 20, 190, 25);
        dialog.add(nameField);

        JLabel dateLabel =
                new JLabel("Due Date:");
        dateLabel.setBounds(20, 60, 100, 25);
        dialog.add(dateLabel);

        JTextField dateField =
                new JTextField(
                        new SimpleDateFormat(
                                "MM/dd/yyyy")
                        .format(new Date()));
        dateField.setBounds(130, 60, 190, 25);
        dialog.add(dateField);

        JLabel priorityLabel =
                new JLabel("Priority:");
        priorityLabel.setBounds(
                20, 100, 100, 25);
        dialog.add(priorityLabel);

        String[] priorities =
                {"High", "Medium", "Low"};
        JComboBox<String> priorityBox =
                new JComboBox<String>(priorities);
        priorityBox.setBounds(
                130, 100, 190, 25);
        dialog.add(priorityBox);

        JLabel categoryLabel =
                new JLabel("Category:");
        categoryLabel.setBounds(
                20, 140, 100, 25);
        dialog.add(categoryLabel);

        String[] categories =
                {"School", "Personal",
                        "Appointments"};
        JComboBox<String> categoryBox =
                new JComboBox<String>(categories);
        categoryBox.setBounds(
                130, 140, 190, 25);
        dialog.add(categoryBox);

        // RECURRENCE DROPDOWN
        JLabel recurrenceLabel =
                new JLabel("Recurrence:");
        recurrenceLabel.setBounds(
                20, 180, 100, 25);
        dialog.add(recurrenceLabel);

        String[] recurrences =
                {"None", "Daily", "Weekly"};
        JComboBox<String> recurrenceBox =
                new JComboBox<String>(recurrences);
        recurrenceBox.setBounds(
                130, 180, 190, 25);
        dialog.add(recurrenceBox);

        JButton saveBtn =
                new JButton("Save Task");
        saveBtn.setBounds(115, 240, 130, 30);
        dialog.add(saveBtn);

        saveBtn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        String name =
                                nameField
                                .getText().trim();

                        if (name.isEmpty()) {
                            JOptionPane
                                .showMessageDialog(
                                    dialog,
                                    "Please enter "
                                    + "a task name.");
                            return;
                        }

                        String dueDate =
                                dateField
                                .getText().trim();

                        if (dueDate.isEmpty()) {
                            JOptionPane
                                .showMessageDialog(
                                    dialog,
                                    "Please enter "
                                    + "a due date.");
                            return;
                        }

                        String priority =
                                (String) priorityBox
                                .getSelectedItem();

                        String category =
                                (String) categoryBox
                                .getSelectedItem();

                        String recurrence =
                                (String) recurrenceBox
                                .getSelectedItem();

                        String createdAt =
                                new SimpleDateFormat(
                                        "MM/dd/yyyy")
                                .format(new Date());

                        Task task = new Task(
                                name, priority,
                                dueDate, "Pending",
                                createdAt, category,
                                recurrence);

                        tasks.add(task);

                        TaskRecurrenceManager
                            .generateRecurringTasks(
                                    tasks, task);

                        TaskSorter.sort(tasks);
                        TaskStorage.saveTasks(tasks);
                        refreshBothTables();

                        dialog.dispose();
                    }
                });

        dialog.setVisible(true);
    }
}
