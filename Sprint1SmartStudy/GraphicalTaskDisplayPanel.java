package Sprint1SmartStudy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Font;
import javax.swing.BorderFactory;

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
    private ArrayList<Task> tasks =
            new ArrayList<Task>();

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

        setTitle("SmartStudy");
        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 970, 800);

        contentPane = new JPanel();
        contentPane.setBackground(
        		new Color(245, 247, 250));
        contentPane.setBorder(
                new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // APP TITLE
        JLabel lblTitle =
                new JLabel("SmartStudy");
        lblTitle.setHorizontalAlignment(
                SwingConstants.LEFT);
        lblTitle.setFont( 
        		new Font(
        		"Helvetica",
        		Font.BOLD,22));       
        lblTitle.setForeground(
        		new Color(33, 90, 168));
        lblTitle.setBounds(20,15,300, 35);
        contentPane.add(lblTitle);

        // SORT DROPDOWN
        JLabel lblSort =
                new JLabel("Sort by:");
        lblSort.setBounds(500, 18, 60, 22);
        contentPane.add(lblSort);

        JComboBox<String> sortbyBox =
                new JComboBox<String>();
        sortbyBox.addItem("Due Date");
        sortbyBox.addItem("Priority");
        sortbyBox.addItem("Status");
        sortbyBox.setBounds(565, 18, 120, 22);
        sortbyBox.setFont(new Font(
        		"Helvetica",
        		Font.PLAIN,
        		12));
        contentPane.add(sortbyBox);

        // FILTER DROPDOWN
        JLabel lblFilter =
                new JLabel("Filter by:");
        lblFilter.setBounds(500, 50, 65, 22);
        contentPane.add(lblFilter);

        JComboBox<String> filterbyBox =
                new JComboBox<String>();
        filterbyBox.addItem("All");
        filterbyBox.addItem("High Priority");
        filterbyBox.addItem("Medium Priority");
        filterbyBox.addItem("Low Priority");
        filterbyBox.addItem("Due Today");
        filterbyBox.setBounds(565, 50, 120, 22);
        filterbyBox.setFont(new Font(
        		"Helvetica",
        		Font.PLAIN,
        		12));
        contentPane.add(filterbyBox);

        // ADD TASK BUTTON
        JButton btnAddTask =
                new JButton("Add Task");
        btnAddTask.setBounds(760, 18, 140, 25);
        btnAddTask.setBackground(
        		new Color(33, 90, 168));
        btnAddTask.setForeground(Color.WHITE);
        btnAddTask.setFocusPainted(false);
        btnAddTask.setBorder(
        		BorderFactory.createEmptyBorder(
        				5, 10, 5, 10));
        contentPane.add(btnAddTask);

        // MARK COMPLETE CHECKBOX
        JCheckBox chckbxMarkComplete =
                new JCheckBox(
                        "Mark Task as Complete");
        chckbxMarkComplete.setBounds(
                500, 82, 170, 23);
        contentPane.add(chckbxMarkComplete);

        // DELETE TASK BUTTON
        JButton btnDeleteTask =
                new JButton("Delete Task");
        btnDeleteTask.setBounds(760, 50, 140, 25);
        btnDeleteTask.setBackground(
        		new Color(200, 50, 50));
        btnDeleteTask.setForeground(Color.WHITE);
        btnDeleteTask.setFocusPainted(false);
        contentPane.add(btnDeleteTask);

        // DELETE COMPLETED BUTTON
        JButton btnDeleteCompleted =
                new JButton("Delete Completed");
        btnDeleteCompleted.setBounds(
                760, 82, 140, 25);
        btnDeleteCompleted.setBackground(
        		new Color(150, 50, 50));
        btnDeleteCompleted.setForeground(
        		Color.WHITE);
        btnDeleteCompleted.setFocusPainted(false);
        contentPane.add(btnDeleteCompleted);

        // ACTIVE TASKS TABLE SETUP
        tableModel = new TaskTableModel();
        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(
                ListSelectionModel
                .SINGLE_SELECTION);
        taskTable.setRowHeight(24);
        taskTable.setFont( new Font(
        		"Helvetica", 
        		Font.PLAIN, 13));
        taskTable.getTableHeader() .setFont(
        		new Font(
        				"Helevetica",
        				Font.BOLD,
        				13));
        taskTable.getTableHeader().setBackground(
        		new Color(33, 90, 168));
        taskTable.getTableHeader().setForeground(
        		Color.WHITE);
        taskTable.setGridColor(
        		new Color(220, 220, 220));
        taskTable.setSelectionBackground(
        		new Color(173, 216, 230));
        taskTable.setSelectionForeground(
        		Color.BLACK);
        taskTable.setBackground(Color.WHITE);
        taskTable.getTableHeader()
                .setReorderingAllowed(false);

        // COMPLETED TASKS TABLE SETUP
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
        completedTable.setFont(new Font (
        		"Helevetica",
        		Font.PLAIN,
        		13));
        completedTable.getTableHeader().setFont(
        		new Font(
        				"Helvetica",
        				Font.BOLD,
        				13));
        completedTable.getTableHeader()
        		.setBackground(new Color
        				(0, 128, 0));
        completedTable.getTableHeader()
        			.setForeground(Color.WHITE);
        completedTable.setGridColor(
        		new Color(220, 220, 220));
        completedTable.setSelectionBackground(
        		new Color(144, 238, 144));
        completedTable.setSelectionForeground(
        		Color.BLACK);
        

        // TABBED PANEL FOR ALL VIEWS
        JTabbedPane tabbedPane =
                new JTabbedPane();
        tabbedPane.setBounds(
                50, 100, 850, 400);
        tabbedPane.setFont(new Font (
        		"Helvetica",
        		Font.BOLD,
        		13));
        tabbedPane.setBackground(
        		new Color(245, 247, 250));
        contentPane.add(tabbedPane);

        // ACTIVE TASKS TAB
        JScrollPane scrollPane =
                new JScrollPane(taskTable);
        tabbedPane.addTab(
                "Active Tasks", scrollPane);

        // COMPLETED TASKS TAB
        JScrollPane completedScrollPane =
                new JScrollPane(completedTable);
        tabbedPane.addTab(
                "Completed Tasks",
                completedScrollPane);

        // LOAD TASKS FROM FILE BEFORE
        // BUILDING CALENDAR
        tasks = TaskStorage.loadTasks();
        TaskSorter.sort(tasks);
        refreshBothTables();

        // CALENDAR TAB - T-20 AND T-21
        TaskScheduler scheduler =
                new TaskScheduler(tasks);
        JPanel calendarPanel =
                MainInterface.buildCalendarPanel(
                        tasks, scheduler,
                        tableModel);
        tabbedPane.addTab(
                "Calendar", calendarPanel);

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

        // DELETE ACTIVE TASK ACTION LISTENER
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

                        ArrayList<Task> active =
                                getActiveTasks();
                        tasks.remove(
                                active.get(modelRow));
                        TaskStorage.saveTasks(
                                tasks);
                        refreshBothTables();
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

                            ArrayList<Task> active =
                                    getActiveTasks();
                            active.get(modelRow)
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
                                    + "task to "
                                    + "delete.");
                            return;
                        }

                        ArrayList<Task> completed =
                                getCompletedTasks();
                        tasks.remove(
                                completed.get(
                                        selectedRow));
                        TaskStorage.saveTasks(
                                tasks);
                        refreshBothTables();
                    }
                });
    }

    // T-15 SEPARATES ACTIVE AND COMPLETED
    private void refreshBothTables() {

        ArrayList<Task> activeTasks =
                new ArrayList<Task>();
        ArrayList<Task> completedTasks =
                new ArrayList<Task>();

        for (Task task : tasks) {
            if (task.getStatus()
                    .equals("Done")) {
                completedTasks.add(task);
            } else {
                activeTasks.add(task);
            }
        }

        tableModel.setTasks(activeTasks);
        completedModel.setTasks(completedTasks);
    }

    ArrayList<Task> getCompletedTasks() {

        ArrayList<Task> completedTasks =
                new ArrayList<Task>();

        for (Task task : tasks) {
            if (task.getStatus()
                    .equals("Done")) {
                completedTasks.add(task);
            }
        }

        return completedTasks;
    }

    ArrayList<Task> getTasks() {
        return tasks;
    }

    ArrayList<Task> getActiveTasks() {

        ArrayList<Task> activeTasks =
                new ArrayList<Task>();

        for (Task task : tasks) {
            if (!task.getStatus()
                    .equals("Done")) {
                activeTasks.add(task);
            }
        }

        return activeTasks;
    }

    int getActiveTaskCount() {
        return tableModel.getRowCount();
    }

    int getCompletedTaskCount() {
        return completedModel.getRowCount();
    }

    void setTasksForTest(
            ArrayList<Task> tasks) {
        this.tasks = tasks;
        refreshBothTables();
    }

    void addTaskForTest(Task task) {
        tasks.add(task);
        refreshBothTables();
    }

    void markTaskComplete(int modelRow) {
        getActiveTasks().get(modelRow)
                .setStatus("Done");
        TaskStorage.saveTasks(tasks);
        refreshBothTables();
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
                        TaskStorage.saveTasks(
                                tasks);
                        refreshBothTables();

                        dialog.dispose();
                    }
                });

        dialog.setVisible(true);
    }
}