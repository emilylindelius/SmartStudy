package Sprint1SmartStudy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;

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
        
        
        // Make the size resizable
        setSize(970, 800);
        setMinimumSize(new Dimension(760, 560));
        
        //Center on the screen 
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBackground(
        		new Color(245, 247, 250));
        contentPane.setBorder(
                new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(
        		new BorderLayout(0, 10));
        setContentPane(contentPane);
        
        //Header
        
        JPanel headerPanel = new JPanel(
        		new BorderLayout(10,0));
        headerPanel.setOpaque(false);
        contentPane.add(
        		headerPanel, BorderLayout.NORTH);  
        
        // APP TITLE
        JLabel lblTitle =
                new JLabel("SmartStudy");
        lblTitle.setFont( 
        		new Font(
        		"Helvetica",
        		Font.BOLD,32));       
        lblTitle.setForeground(
        		new Color(33, 90, 168));
        headerPanel.add(
        		lblTitle, BorderLayout.WEST);
        
        
        //Controls Panel  layout in a flexible grid
        
        JPanel controlsPanel = new JPanel(
        		new GridBagLayout());
        controlsPanel.setOpaque(false);
        headerPanel.add(
        		controlsPanel, BorderLayout.EAST);
        
        GridBagConstraints gbc =
        		new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //Row 0: Sort by / Add task 
        JLabel lblSort =
                new JLabel("Sort by:");
        gbc.gridx  = 0;
        gbc.gridy  = 0;
        gbc.anchor = GridBagConstraints.EAST;
        controlsPanel.add(lblSort, gbc);
        
        JComboBox<String> sortbyBox =
                new JComboBox<String>();
        sortbyBox.addItem("Due Date");
        sortbyBox.addItem("Priority");
        sortbyBox.addItem("Status");
        sortbyBox.setFont(new Font(
        		"Helvetica",
        		Font.PLAIN,
        		12));
        gbc.gridx  = 1;
        gbc.gridy  = 0;
        controlsPanel.add(sortbyBox, gbc);
        

        // ADD TASK BUTTON
        JButton btnAddTask =
                new JButton("Add Task");
        btnAddTask.setBackground(
        		new Color(33, 90, 168));
        btnAddTask.setForeground(Color.WHITE);
        btnAddTask.setFocusPainted(false);
        btnAddTask.setBorder(
        		BorderFactory.createEmptyBorder(
        				5, 10, 5, 10));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        controlsPanel.add(btnAddTask, gbc);
        
        // Row 1: Filter By / Delete Task
        
        // FILTER DROPDOWN
        JLabel lblFilter =
                new JLabel("Filter by:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        controlsPanel.add(lblFilter, gbc);
        
        JComboBox<String> filterbyBox =
                new JComboBox<String>();
        filterbyBox.addItem("All");
        filterbyBox.addItem("High Priority");
        filterbyBox.addItem("Medium Priority");
        filterbyBox.addItem("Low Priority");
        filterbyBox.addItem("Due Today");
        filterbyBox.setFont(new Font(
        		"Helvetica",
        		Font.PLAIN,
        		12));
        gbc.gridx = 1;
        gbc.gridy = 1;
        controlsPanel.add(filterbyBox, gbc);
        
     // DELETE TASK BUTTON
        JButton btnDeleteTask =
                new JButton("Delete Task");
        btnDeleteTask.setBackground(
        		new Color(200, 50, 50));
        btnDeleteTask.setForeground(Color.WHITE);
        btnDeleteTask.setFocusPainted(false);
        gbc.gridx = 2;
        gbc.gridy = 1;
        controlsPanel.add(btnDeleteTask, gbc);
        
        //Row 2: Mark Complete Checkbox / Deleted Completed
        
        // MARK COMPLETE CHECKBOX
        JCheckBox chckbxMarkComplete =
                new JCheckBox(
                        "Mark Task as Complete");
        chckbxMarkComplete.setOpaque(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(chckbxMarkComplete, gbc);
        gbc.gridwidth = 1;        

        // DELETE COMPLETED BUTTON
        JButton btnDeleteCompleted =
                new JButton("Delete Completed");
        btnDeleteCompleted.setBackground(
        		new Color(150, 50, 50));
        btnDeleteCompleted.setForeground(
        		Color.WHITE);
        btnDeleteCompleted.setFocusPainted(false);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        controlsPanel.add(btnDeleteCompleted, gbc);

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
        centerAlignTable(taskTable);

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
        completedTable.setFont(new Font(
                "Helvetica",
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
        completedTable.setFillsViewportHeight(true);
        completedTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS);
        centerAlignTable(completedTable);
 
        // ============= TABBED PANEL =============
        // Placed in BorderLayout.CENTER so it grows
        // and shrinks to fill whatever space is left
        // after the header, on any window size.
        JTabbedPane tabbedPane =
                new JTabbedPane();
        tabbedPane.setFont(new Font(
                "Helvetica",
                Font.BOLD,
                13));
        tabbedPane.setBackground(
                new Color(245, 247, 250));
        contentPane.add(
                tabbedPane, BorderLayout.CENTER);
 
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
 
    // Center align all table columns
    private void centerAlignTable(JTable table) {
 
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(
                JLabel.CENTER);
 
        for (int i = 0; i < table.getColumnCount();
                i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }
 
    }
 
    // OPENS A CLEAN POPUP TO ADD A TASK
    private void openAddTaskDialog() {
 
        JDialog dialog = new JDialog(
                this, "Add New Task", true);
        dialog.setLayout(
                new GridBagLayout());
 
        GridBagConstraints dgbc =
                new GridBagConstraints();
        dgbc.insets = new Insets(8, 10, 8, 10);
        dgbc.fill = GridBagConstraints.HORIZONTAL;
 
        JLabel nameLabel =
                new JLabel("Task Name:");
        dgbc.gridx = 0;
        dgbc.gridy = 0;
        dgbc.anchor = GridBagConstraints.EAST;
        dgbc.weightx = 0;
        dialog.add(nameLabel, dgbc);
 
        JTextField nameField =
                new JTextField(18);
        dgbc.gridx = 1;
        dgbc.gridy = 0;
        dgbc.weightx = 1;
        dialog.add(nameField, dgbc);
 
        JLabel dateLabel =
                new JLabel("Due Date:");
        dgbc.gridx = 0;
        dgbc.gridy = 1;
        dgbc.weightx = 0;
        dialog.add(dateLabel, dgbc);
 
        JTextField dateField =
                new JTextField(
                        new SimpleDateFormat(
                                "MM/dd/yyyy")
                        .format(new Date()));
        dgbc.gridx = 1;
        dgbc.gridy = 1;
        dgbc.weightx = 1;
        dialog.add(dateField, dgbc);
 
        JLabel priorityLabel =
                new JLabel("Priority:");
        dgbc.gridx = 0;
        dgbc.gridy = 2;
        dgbc.weightx = 0;
        dialog.add(priorityLabel, dgbc);
 
        String[] priorities =
                {"High", "Medium", "Low"};
        JComboBox<String> priorityBox =
                new JComboBox<String>(priorities);
        dgbc.gridx = 1;
        dgbc.gridy = 2;
        dgbc.weightx = 1;
        dialog.add(priorityBox, dgbc);
 
        JLabel categoryLabel =
                new JLabel("Category:");
        dgbc.gridx = 0;
        dgbc.gridy = 3;
        dgbc.weightx = 0;
        dialog.add(categoryLabel, dgbc);
 
        String[] categories =
                {"School", "Personal",
                        "Appointments"};
        JComboBox<String> categoryBox =
                new JComboBox<String>(categories);
        dgbc.gridx = 1;
        dgbc.gridy = 3;
        dgbc.weightx = 1;
        dialog.add(categoryBox, dgbc);
 
        // RECURRENCE DROPDOWN
        JLabel recurrenceLabel =
                new JLabel("Recurrence:");
        dgbc.gridx = 0;
        dgbc.gridy = 4;
        dgbc.weightx = 0;
        dialog.add(recurrenceLabel, dgbc);
 
        String[] recurrences =
                {"None", "Daily", "Weekly"};
        JComboBox<String> recurrenceBox =
                new JComboBox<String>(recurrences);
        dgbc.gridx = 1;
        dgbc.gridy = 4;
        dgbc.weightx = 1;
        dialog.add(recurrenceBox, dgbc);
 
        JButton saveBtn =
                new JButton("Save Task");
        dgbc.gridx = 0;
        dgbc.gridy = 5;
        dgbc.gridwidth = 2;
        dgbc.anchor = GridBagConstraints.CENTER;
        dgbc.fill = GridBagConstraints.NONE;
        dialog.add(saveBtn, dgbc);
 
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
 
        // pack() sizes the dialog to fit its content
        // instead of a hardcoded 360x340 that may
        // clip on high-DPI/scaled displays.
        dialog.pack();
        dialog.setMinimumSize(
                dialog.getPreferredSize());
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}