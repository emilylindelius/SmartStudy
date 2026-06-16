package Sprint1SmartStudy;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class TaskGUI {

    private ArrayList<Task> tasks =
            new ArrayList<Task>();

    private DefaultListModel<String> listModel =
            new DefaultListModel<String>();
    
    // Filter dropdown field
    private JComboBox<String> filterBox =
    		new JComboBox<String>();
    

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
        frame.setSize(560, 490);
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
        
        //Filter DropDown 
        JLabel filterLabel = 
        		new JLabel("Filter by:");
        filterLabel.setBounds(360, 115, 80, 25);
        
        filterBox.setBounds(360, 155, 150, 25);
        filterBox.addItem("All");
        filterBox.addItem("School");
        filterBox.addItem("Personal");
        filterBox.addItem("Appointments");
        
        

        JButton saveButton =
                new JButton("Save Task");
        saveButton.setBounds(130, 200, 120, 30);

        JList<String> taskList =
                new JList<String>(listModel);
        JScrollPane scrollPane =
                new JScrollPane(taskList);
        scrollPane.setBounds(30, 245, 500, 190);

        frame.add(taskLabel);
        frame.add(taskField);
        frame.add(dueDateLabel);
        frame.add(datePicker);
        frame.add(priorityLabel);
        frame.add(priorityBox);
        frame.add(categoryLabel);
        frame.add(categoryBox);
        frame.add(filterLabel);
        frame.add(filterBox);
        frame.add(saveButton);
        frame.add(scrollPane);
        
        // LOAD TASKS ON STARTUP
        tasks = TaskStorage.loadTasks();
        TaskSorter.sort(tasks);
        refreshList();
        
        //Filter Action Listener
        filterBox.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(
        					ActionEvent e) {
        				refreshList();
        			}
        		});

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

    // Group tasks by category and applies filter selection
    
    private void refreshList() {
    	
    	listModel.clear();
    	
    	String selected = 
    			(String) filterBox
    			.getSelectedItem();
    	Map<String, List<Task>> grouped =
    			new LinkedHashMap<String, 
    			List<Task>>();
    	
        for (Task task : tasks) {
        	
        	//Here where will be applied filter logic
        	if (selected != null
        			&& !selected.equals("All")
        			&& !task.getCategory()
        			.equals(selected)) {
        		continue;
        	}
        	
        	// Group by category
        	
        	if (!grouped.containsKey(
        			task.getCategory())) {
        		grouped.put(
        				task.getCategory(),
        				new ArrayList<Task>());
        	}
        	grouped.get(task.getCategory())
        	.add(task);
        }
        
        //Display grouped tasks
        
        for (String cat : grouped.keySet()) {
        	listModel.addElement(
        			"====" + cat + "===");
        	
        	for (Task task:
        			grouped.get(cat)) {
        		listModel.addElement(
        				"   "
        				+ task.toDisplayString());
        	}
        }
    }
}




































