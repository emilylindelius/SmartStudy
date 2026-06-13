package Sprint1SmartStudy;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class TaskGUI {

    private ArrayList<Task> tasks =
            new ArrayList<Task>();

    private DefaultListModel<String> listModel =
            new DefaultListModel<String>();

    // Date formatter for calendar style
    
    class DateLabelFormatter
    	extends JFormattedTextField.AbstractFormatter {
    	
    	private String datePattern = "MM/dd/yyyy";
    	private SimpleDateFormat sdf = 
    			new SimpleDateFormat(datePattern);
    	@Override
    	public Object stringToValue(String text)
    			throws ParseException {
    		return sdf.parse(text);
    		
    	}
    	
    	@Override 
    	public String valueToString(Object value)
    				throws ParseException{
    		if (value != null) {
    			Calendar cal = (Calendar) value;
    			return sdf.format(
    					cal.getTime());
    		}
    		return "";
    	}
    			
    }
    
    public void launch() {

        JFrame frame =
                new JFrame("Smart Study");
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

        //Calendar format 
        JLabel dueDateLabel =
                new JLabel("Due Date:");
        dueDateLabel.setBounds(30, 70, 100, 25);
        
        UtilDateModel dateModel =
        		new UtilDateModel();
        dateModel.setDate(
        		Calendar.getInstance()
        				.get(Calendar.YEAR),
        		Calendar.getInstance()
        				.get(Calendar.MONTH),
        		Calendar.getInstance()
        				.get(Calendar.DAY_OF_MONTH));
        dateModel.setSelected(true);
        
        Properties dateProperties = 
        		new Properties();
        dateProperties.put(
        		"text.today", "Today");
        dateProperties.put(
        		"text.month", "Month");
        dateProperties.put(
        		"text.year", "Year");
        
        JDatePanelImpl datePanel =
        		new JDatePanelImpl(dateModel,
        				dateProperties);
        JDatePickerImpl datePicker =
        		new JDatePickerImpl(datePanel,
        				new DateLabelFormatter());
        datePicker.setBounds(130,70,200,30);
            
        JLabel priorityLabel =
                new JLabel("Priority:");
        priorityLabel.setBounds(30, 115, 100, 25);

        String[] priorities =
                {"High", "Medium", "Low"};
        JComboBox<String> priorityBox =
                new JComboBox<String>(priorities);
        priorityBox.setBounds(130, 115, 200, 25);
        
        
        // Here I added the category label and DropDown
        
        JLabel categoryLabel = 
        		new JLabel("Category:");
        categoryLabel.setBounds(30, 155, 100, 25);
        
        String[] categories = 
        	{"School", "Personal", "Appointments"};
        JComboBox<String> categoryBox =
        		new JComboBox<String>(categories);
        categoryBox.setBounds(130, 155, 200, 25);    

        JButton saveButton =
                new JButton("Save Task");
        saveButton.setBounds(130, 195, 120, 30);

        JList<String> taskList =
                new JList<String>(listModel);
        JScrollPane scrollPane =
                new JScrollPane(taskList);
        scrollPane.setBounds(30, 240, 420, 160);

        frame.add(taskLabel);
        frame.add(taskField);
        frame.add(dueDateLabel);
        frame.add(datePicker);
        frame.add(priorityLabel);
        frame.add(priorityBox);
        frame.add(saveButton);
        frame.add(scrollPane);
        frame.add(categoryLabel);
        frame.add(categoryBox);

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
                        
                        // Get date from the calendar format
                        Date selectDate = 
                        		(Date) datePicker
                        		.getModel()
                        		.getValue();
                        
                        if (selectDate == null) {
                        	JOptionPane
                        	.showMessageDialog(
                        			frame,
                        			"Please select "
                        			+ "a due date.");
                        	return;
                        	
                        }
                        
                        SimpleDateFormat sdf = 
                        		new SimpleDateFormat(
                        				"MM/dd/yyyy");
                        String dueDate =
                        		sdf.format(selectDate);

                        String priority =
                                (String) priorityBox
                                .getSelectedItem();

                       SimpleDateFormat sdfNow =
                                new SimpleDateFormat(
                                        "MM/dd/yyyy");
                       
                       String createdAt =
                                sdfNow.format(
                                        new Date());
                       
                       //Here I am validating the category for Sprint Mid 2
                       String category = 
                    		   (String) categoryBox
                    		   .getSelectedItem();
                       
                       if (category == null
                    		   || category.isEmpty()) {
                    	   JOptionPane
                    	   .showMessageDialog(
                    			   frame,
                    			   "Please select "
                    			   + "a category.");
                    	   return;
                    	   
                       }

                        Task task = new Task(
                                taskName,
                                priority,
                                dueDate,
                                "Pending",
                                createdAt,
                                category);

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
