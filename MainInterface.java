package MonthlyCalendarPanel;

import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainInterface {
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Calendar");
		frame.setSize(900, 500);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		
		JPanel mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		
		LocalDate date = LocalDate.now();
		
		mainPanel.add(new Calendar(date.getYear(), date.getMonthValue(), date, mainPanel));
		
		frame.getContentPane().add(mainPanel);
		frame.setVisible(true);
	}

}
