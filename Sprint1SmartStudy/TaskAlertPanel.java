package Sprint1SmartStudy;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskAlertPanel extends JPanel{
	
	private final JPopupMenu popupMenu;

    public TaskAlertPanel() {
        setPreferredSize(new Dimension(700, 500));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createTitledBorder("Tasks Due Today"));

        
        popupMenu = buildPopupMenu();

        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
        });
    }

    private void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupMenu.show(this, e.getX(), e.getY());
        }
    }

    private JPopupMenu buildPopupMenu() {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem snoozeItem = new JMenuItem("Snooze 10 min");
        snoozeItem.addActionListener(ev -> System.out.println("Task snoozed"));

        JMenuItem dismissItem = new JMenuItem("Dismiss Alert");
        dismissItem.addActionListener(ev -> System.out.println("Task dismissed"));

        JMenuItem detailsItem = new JMenuItem("View Details");
        detailsItem.addActionListener(ev -> System.out.println("Opening details…"));

        JMenuItem completeItem = new JMenuItem("Mark Complete");
        completeItem.addActionListener(ev -> System.out.println("Task completed"));

        menu.add(snoozeItem);
        menu.add(dismissItem);
        menu.add(detailsItem);
        menu.addSeparator();
        menu.add(completeItem);

        return menu;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Task Alert");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TaskAlertPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
