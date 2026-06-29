package Sprint1SmartStudy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calendar extends JPanel {

    private static final long serialVersionUID
            = 1L;

    // DRAGGED TASK REFERENCE
    private static Task draggedTask = null;

    public Calendar(int year, int month,
            LocalDate selectedDay,
            JPanel mainPanel,
            ArrayList<Task> tasks,
            TaskScheduler scheduler,
            TaskTableModel tableModel) {

        setLayout(new BorderLayout(3, 3));
        setBorder(BorderFactory
                .createEmptyBorder(
                        3, 3, 3, 3));
        setBackground(Color.white);

        JPanel top = new JPanel(
                new BorderLayout(3, 3));
        top.setBackground(null);

        JLabel date = new JLabel(
                LocalDate.of(year, month, 1)
                .format(DateTimeFormatter
                .ofPattern("MMMM yyyy")));
        date.setHorizontalAlignment(
                JLabel.CENTER);
        date.setFont(new Font(
                "Helvetica", Font.BOLD, 23));
        date.setForeground(Color.black);
        top.add(date, BorderLayout.CENTER);

        // LEFT ARROW - PREVIOUS MONTH
        JLabel left = new JLabel("◀");
        left.setFont(new Font(
                "Helvetica", Font.BOLD, 22));
        left.setCursor(new Cursor(
                Cursor.HAND_CURSOR));
        left.addMouseListener(
                new MouseListener() {
                    public void mouseClicked(
                            MouseEvent e) {
                        mainPanel.removeAll();
                        if (month != 1) {
                            mainPanel.add(
                                new Calendar(
                                    year,
                                    month - 1,
                                    selectedDay,
                                    mainPanel,
                                    tasks,
                                    scheduler,
                                    tableModel));
                        } else {
                            mainPanel.add(
                                new Calendar(
                                    year - 1,
                                    12,
                                    selectedDay,
                                    mainPanel,
                                    tasks,
                                    scheduler,
                                    tableModel));
                        }
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                    public void mouseReleased(
                            MouseEvent e) {}
                    public void mousePressed(
                            MouseEvent e) {}
                    public void mouseExited(
                            MouseEvent e) {}
                    public void mouseEntered(
                            MouseEvent e) {}
                });
        top.add(left, BorderLayout.WEST);

        // RIGHT ARROW - NEXT MONTH
        JLabel right = new JLabel("▶");
        right.setFont(new Font(
                "Helvetica", Font.BOLD, 22));
        right.setCursor(new Cursor(
                Cursor.HAND_CURSOR));
        right.addMouseListener(
                new MouseListener() {
                    public void mouseClicked(
                            MouseEvent e) {
                        mainPanel.removeAll();
                        if (month != 12) {
                            mainPanel.add(
                                new Calendar(
                                    year,
                                    month + 1,
                                    selectedDay,
                                    mainPanel,
                                    tasks,
                                    scheduler,
                                    tableModel));
                        } else {
                            mainPanel.add(
                                new Calendar(
                                    year + 1,
                                    1,
                                    selectedDay,
                                    mainPanel,
                                    tasks,
                                    scheduler,
                                    tableModel));
                        }
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                    public void mouseReleased(
                            MouseEvent e) {}
                    public void mousePressed(
                            MouseEvent e) {}
                    public void mouseExited(
                            MouseEvent e) {}
                    public void mouseEntered(
                            MouseEvent e) {}
                });
        top.add(right, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel days = new JPanel(
                new GridLayout(7, 7, 1, 1));
        days.setBackground(null);

        Color header = Color.LIGHT_GRAY;
        days.add(new DayLabel(
                "Sun", header, Color.white,
                false));
        days.add(new DayLabel(
                "Mon", header, Color.white,
                false));
        days.add(new DayLabel(
                "Tue", header, Color.white,
                false));
        days.add(new DayLabel(
                "Wed", header, Color.white,
                false));
        days.add(new DayLabel(
                "Thu", header, Color.white,
                false));
        days.add(new DayLabel(
                "Fri", header, Color.white,
                false));
        days.add(new DayLabel(
                "Sat", header, Color.white,
                false));

        String[] weekDays = new String[]{
                "SUNDAY", "MONDAY", "TUESDAY",
                "WEDNESDAY", "THURSDAY",
                "FRIDAY", "SATURDAY"};

        LocalDate firstDay = LocalDate.of(
                year, month, 1);

        int j = 0;
        while (!firstDay.getDayOfWeek()
                .toString()
                .equals(weekDays[j])) {
            days.add(new DayLabel(
                    "", Color.decode("#f0f0f0"),
                    Color.black, false));
            j++;
        }

        int daysNum = YearMonth.of(year, month)
                .lengthOfMonth();

        for (int i = 1; i <= daysNum; i++) {

            final int day = i;

            // FORMAT DATE FOR TASK LOOKUP
            String dayStr = String.format(
                    "%02d/%02d/%04d",
                    month, i, year);

            // GET TASKS FOR THIS DAY
            ArrayList<Task> dayTasks =
                    scheduler.getTasksForDate(
                            dayStr);

            // BUILD DISPLAY TEXT
            StringBuilder cellText =
                    new StringBuilder();
            cellText.append("<html><center>")
                    .append(i);

            for (Task t : dayTasks) {
                cellText.append(
                        "<br><font size='2' "
                        + "color='#333333'>")
                        .append(t.getTaskName())
                        .append("</font>");
            }
            cellText.append(
                    "</center></html>");

            DayLabel dayLabel;
            if (selectedDay.getYear() == year
                    && selectedDay
                    .getMonthValue() == month
                    && selectedDay
                    .getDayOfMonth() == i) {
                dayLabel = new DayLabel(
                        cellText.toString(),
                        Color.decode("#0ecf78"),
                        Color.black, true);
            } else if (!dayTasks.isEmpty()) {
                dayLabel = new DayLabel(
                        cellText.toString(),
                        Color.decode("#ffe4b5"),
                        Color.black, true);
            } else {
                dayLabel = new DayLabel(
                        cellText.toString(),
                        Color.decode("#f0f0f0"),
                        Color.black, true);
            }

            // T-21 DRAG SOURCE ON TASK CELLS
            if (!dayTasks.isEmpty()) {

                final Task firstTask =
                        dayTasks.get(0);

                DragSource dragSource =
                        DragSource
                        .getDefaultDragSource();

                dragSource
                    .createDefaultDragGestureRecognizer(
                        dayLabel,
                        DnDConstants.ACTION_MOVE,
                        new DragGestureListener() {
                            public void
                            dragGestureRecognized(
                                    DragGestureEvent
                                    dge) {
                                draggedTask =
                                        firstTask;
                                Transferable t =
                                        new StringSelection(
                                        firstTask
                                        .getTaskName());
                                dge.startDrag(
                                        DragSource
                                        .DefaultMoveDrop,
                                        t);
                            }
                        });
            }

            // T-21 DROP TARGET ON ALL DAY CELLS
            final String dropDate = dayStr;

            new DropTarget(dayLabel,
                    new DropTargetAdapter() {
                        public void drop(
                                DropTargetDropEvent
                                dtde) {
                            try {
                                dtde.acceptDrop(
                                        DnDConstants
                                        .ACTION_MOVE);

                                if (draggedTask
                                        != null) {
                                    scheduler
                                        .rescheduleTask(
                                            draggedTask,
                                            dropDate,
                                            tableModel);

                                    draggedTask =
                                            null;

                                    mainPanel
                                        .removeAll();
                                    mainPanel.add(
                                        new Calendar(
                                            year,
                                            month,
                                            selectedDay,
                                            mainPanel,
                                            tasks,
                                            scheduler,
                                            tableModel));
                                    mainPanel
                                        .revalidate();
                                    mainPanel
                                        .repaint();
                                }

                            } catch (Exception ex) {
                                dtde.rejectDrop();
                            }
                        }
                    });

            // CLICK TO SELECT DAY
            dayLabel.addMouseListener(
                    new MouseListener() {
                        public void mouseClicked(
                                MouseEvent e) {
                            mainPanel.removeAll();
                            LocalDate selected =
                                    LocalDate.of(
                                    year, month,
                                    day);
                            mainPanel.add(
                                new Calendar(
                                    year, month,
                                    selected,
                                    mainPanel,
                                    tasks,
                                    scheduler,
                                    tableModel));
                            mainPanel.revalidate();
                            mainPanel.repaint();
                        }
                        public void mouseReleased(
                                MouseEvent e) {}
                        public void mousePressed(
                                MouseEvent e) {}
                        public void mouseExited(
                                MouseEvent e) {}
                        public void mouseEntered(
                                MouseEvent e) {}
                    });

            days.add(dayLabel);
        }

        for (int i = 0;
                i < (42 - (j + daysNum)); i++) {
            days.add(new DayLabel(
                    "", Color.decode("#f0f0f0"),
                    Color.black, true));
        }

        add(days, BorderLayout.CENTER);
    }
}