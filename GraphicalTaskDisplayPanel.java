package SmartStudypackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class GraphicalTaskDisplayPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicalTaskDisplayPanel frame = new GraphicalTaskDisplayPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GraphicalTaskDisplayPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 997, 566);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Smart Study");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 73, 68);
		contentPane.add(lblNewLabel);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(954, 11, 17, 516);
		contentPane.add(scrollBar);
		
		JLabel lblNewLabel_1 = new JLabel("Task");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(0, 96, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Due Date");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setBounds(305, 96, 64, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Priority");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_2.setBounds(442, 96, 46, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Status");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_3.setBounds(566, 96, 46, 14);
		contentPane.add(lblNewLabel_1_3);
		
		String[] sortBy = 
			{"Due Date", "Priority", "Status"};
		JComboBox<String> sortbyBox = 
				new JComboBox<String>();
		sortbyBox.setBounds(698, 80, 73, 22);
		contentPane.add(sortbyBox);
		
		JButton btnNewButton = new JButton("Add Task");
		btnNewButton.setBounds(855, 80, 89, 23);
		contentPane.add(btnNewButton);
		
		String[] filterBy = 
			{"Due Date", "Priority"};
		JComboBox<String> filterbyBox = 
				new JComboBox<String>();
		filterbyBox.setBounds(772, 80, 73, 22);
		contentPane.add(filterbyBox);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Mark Task as Complete");
		chckbxNewCheckBox.setBounds(697, 136, 274, 23);
		contentPane.add(chckbxNewCheckBox);
		

	}
}
