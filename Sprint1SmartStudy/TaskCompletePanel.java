package Sprint1SmartStudy;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class TaskCompletePanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskCompletePanel frame = new TaskCompletePanel();
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
	public TaskCompletePanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 888, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Smart Study");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 73, 68);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Task");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(0, 99, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Status");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setBounds(482, 99, 150, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Date Completed On");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_1.setBounds(273, 99, 150, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		String[] sortBy = 
			{"Task Completed", "Status",};
		JComboBox<String> sortbyBox = 
				new JComboBox<String>();
		sortbyBox.setBounds(698, 80, 73, 22);
		contentPane.add(sortbyBox);
		
		JCheckBox chckbxDeleteTask = new JCheckBox("Delete Task");
		chckbxDeleteTask.setBounds(635, 135, 117, 23);
		contentPane.add(chckbxDeleteTask);
		
		String[] filterBy = 
			{"Task Completed", "Status"};
		JComboBox<String> filterbyBox = 
				new JComboBox<String>();
		filterbyBox.setBounds(772, 80, 73, 22);
		contentPane.add(filterbyBox);

	}
}
