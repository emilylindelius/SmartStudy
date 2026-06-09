package Sprint1SmartStudy;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaskGUI {

    private ArrayList<Task> tasks =
            new ArrayList<Task>();

    private DefaultListModel<String> listModel =
            new DefaultListModel<String>();

    public void launch() {

        JFrame frame =
                new JFrame("Smart Study Planner");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // AUTO SAVE ON CLOSE
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(
                            WindowEvent e) {
                        TaskStorage.saveTasks(tasks);
                    }
                });

        JLabel taskLabel =
                new JLabel("Task Name:");
        taskLabel.setBounds(30, 30, 100, 25);

        JTextField taskField = new JTextField();
        taskField.setBounds(130, 30, 200, 25);

        JLabel dueDateLabel =
                new JLabel("Due Date:");
        dueDateLabel.setBounds(30, 70, 100, 25);

        SpinnerDateModel dateModel =
                new SpinnerDateModel(new Date(),
                        null, null,
                        Calendar.DAY_OF_MONTH);

        JSpinner dateSpinner =
                new JSpinner(dateModel);
        dateSpinner.setBounds(130, 70, 200, 25);

        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(
                        dateSpinner, "MM/dd/yyyy");
        dateSpinner.setEditor(dateEditor);

        JLabel priorityLabel =
                new JLabel("Priority:");
        priorityLabel.setBounds(30, 110, 100, 25);

        String[] priorities =
                {"High", "Medium", "Low"};
        JComboBox<String> priorityBox =
                new JComboBox<String>(priorities);
        priorityBox.setBounds(130, 110, 200, 25);

        JButton saveButton =
                new JButton("Save Task");
        saveButton.setBounds(130, 150, 120, 30);

        JList<String> taskList =
                new JList<String>(listModel);
        JScrollPane scrollPane =
                new JScrollPane(taskList);
        scrollPane.setBounds(30, 200, 420, 150);

        frame.add(taskLabel);
        frame.add(taskField);
        frame.add(dueDateLabel);
        frame.add(dateSpinner);
        frame.add(priorityLabel);
        frame.add(priorityBox);
        frame.add(saveButton);
        frame.add(scrollPane);

        // LOAD TASKS ON STARTUP
        tasks = TaskStorage.loadTasks();
        TaskSorter.sort(tasks);
        refreshList();

        saveButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        String taskName =
                                taskField.getText()
                                        .trim();

                        if (taskName.isEmpty()) {
                            JOptionPane
                                .showMessageDialog(
                                    frame,
                                    "Please enter "
                                    + "a task name.");
                            return;
                        }

                        String priority =
                                (String) priorityBox
                                .getSelectedItem();

                        Date selectedDate =
                                (Date) dateSpinner
                                .getValue();
                        SimpleDateFormat sdf =
                                new SimpleDateFormat(
                                        "MM/dd/yyyy");
                        String dueDate =
                                sdf.format(
                                        selectedDate);

                        SimpleDateFormat sdfNow =
                                new SimpleDateFormat(
                                        "MM/dd/yyyy");
                        String createdAt =
                                sdfNow.format(
                                        new Date());

                        Task task = new Task(
                                taskName,
                                priority,
                                dueDate,
                                "Pending",
                                createdAt);

                        tasks.add(task);
                        TaskSorter.sort(tasks);
                        TaskStorage.saveTasks(tasks);
                        refreshList();

                        JOptionPane
                            .showMessageDialog(
                                frame,
                                "Task saved "
                                + "successfully.");

                        taskField.setText("");
                    }
                });

        frame.setVisible(true);
    }

    // REFRESH JLIST FROM TASK ARRAYLIST
    private void refreshList() {

        listModel.clear();

        for (Task task : tasks) {
            listModel.addElement(
                    task.toDisplayString());
        }
    }
}
