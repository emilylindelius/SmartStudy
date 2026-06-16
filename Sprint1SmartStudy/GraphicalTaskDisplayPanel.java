package Sprint1SmartStudy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
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
    private TaskTableModel tableModel;
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
        setBounds(100, 100, 970, 560);

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

        // JTABLE SETUP
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
                20, 120, 920, 400);
        contentPane.add(scrollPane);

        // LOAD TASKS FROM FILE
        tasks = TaskStorage.loadTasks();
        TaskSorter.sort(tasks);
        tableModel.setTasks(tasks);

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
                        tableModel.setTasks(
                                tasks);
                    }
                });

        // MARK COMPLETE ACTION LISTENER
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

                            tableModel
                                .updateStatus(
                                    modelRow,
                                    "Done");

                            TaskStorage.saveTasks(
                                    tableModel
                                    .getTasks());

                            chckbxMarkComplete
                                .setSelected(
                                        false);

                            JOptionPane
                                .showMessageDialog(
                                    null,
                                    "Task marked "
                                    + "as complete.");

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
    }

    // OPENS A CLEAN POPUP TO ADD A TASK
    private void openAddTaskDialog() {

        JDialog dialog = new JDialog(
                this, "Add New Task", true);
        dialog.setSize(360, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel nameLabel =
                new JLabel("Task Name:");
        nameLabel.setBounds(
                20, 20, 100, 25);
        dialog.add(nameLabel);

        JTextField nameField =
                new JTextField();
        nameField.setBounds(
                130, 20, 190, 25);
        dialog.add(nameField);

        JLabel dateLabel =
                new JLabel("Due Date:");
        dateLabel.setBounds(
                20, 60, 100, 25);
        dialog.add(dateLabel);

        JTextField dateField =
                new JTextField(
                        new SimpleDateFormat(
                                "MM/dd/yyyy")
                        .format(new Date()));
        dateField.setBounds(
                130, 60, 190, 25);
        dialog.add(dateField);

        JLabel priorityLabel =
                new JLabel("Priority:");
        priorityLabel.setBounds(
                20, 100, 100, 25);
        dialog.add(priorityLabel);

        String[] priorities =
                {"High", "Medium", "Low"};
        JComboBox<String> priorityBox =
                new JComboBox<String>(
                        priorities);
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
                new JComboBox<String>(
                        categories);
        categoryBox.setBounds(
                130, 140, 190, 25);
        dialog.add(categoryBox);

        JButton saveBtn =
                new JButton("Save Task");
        saveBtn.setBounds(
                115, 200, 130, 30);
        dialog.add(saveBtn);

        saveBtn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        String name =
                                nameField
                                .getText()
                                .trim();

                        if (name.isEmpty()) {
                            JOptionPane
                                .showMessageDialog(
                                    dialog,
                                    "Please enter "
                                    + "a task "
                                    + "name.");
                            return;
                        }

                        String dueDate =
                                dateField
                                .getText()
                                .trim();

                        if (dueDate.isEmpty()) {
                            JOptionPane
                                .showMessageDialog(
                                    dialog,
                                    "Please enter "
                                    + "a due "
                                    + "date.");
                            return;
                        }

                        String priority =
                                (String)
                                priorityBox
                                .getSelectedItem();

                        String category =
                                (String)
                                categoryBox
                                .getSelectedItem();

                        String createdAt =
                                new SimpleDateFormat(
                                        "MM/dd/yyyy")
                                .format(
                                        new Date());

                        Task task = new Task(
                                name, priority,
                                dueDate,
                                "Pending",
                                createdAt,
                                category);

                        tasks.add(task);
                        TaskSorter.sort(
                                tasks);
                        TaskStorage.saveTasks(
                                tasks);
                        tableModel.setTasks(
                                tasks);

                        dialog.dispose();
                    }
                });

        dialog.setVisible(true);
    }
}
