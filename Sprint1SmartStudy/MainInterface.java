package Sprint1SmartStudy;

import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainInterface {
	
	// Called from GraphicalTaskDisplayPanel to build the calendar panel
	public static JPanel buildCalendarPanel(
            ArrayList<Task> tasks,
            TaskScheduler scheduler,
            TaskTableModel tableModel) {

        JPanel mainPanel = new JPanel(
                new GridLayout(1, 1, 0, 0));
        mainPanel.setBackground(Color.white);

        LocalDate date = LocalDate.now();

        mainPanel.add(new Calendar(
        		date.getYear(),
                date.getMonthValue(),
                date,
                mainPanel,
                tasks,
                scheduler,
                tableModel));

        return mainPanel;
    }

	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Calendar");
		frame.setSize(900, 500);
		frame.setLocationRelativeTo(null);
		frame.getContentPane()
			.setBackground(Color.white);
		
		JPanel mainPanel = new JPanel(
				new GridLayout(1, 1, 0, 0));
		
		LocalDate date = LocalDate.now();
		
		//Create empty tasks and scheduler
		 ArrayList<Task> tasks =
	                new ArrayList<Task>();
	        TaskScheduler scheduler =
	                new TaskScheduler(tasks);
	        TaskTableModel tableModel =
	                new TaskTableModel();
	        
	        mainPanel.add(new Calendar
	        		(date.getYear(), 
	        		date.getMonthValue(), 
	        		date,
	        		mainPanel,
	        		tasks,
	        		scheduler,
	        		tableModel));
		
		frame.getContentPane().add(mainPanel);
		frame.setVisible(true);
	}

}
