package Sprint1SmartStudy;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SortOrder;
import javax.swing.RowSorter;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

public class TaskFilterSorter {

    private TableRowSorter<TaskTableModel>
            sorter;

    public TaskFilterSorter(
            JTable table,
            TaskTableModel model) {

        sorter = new TableRowSorter<
                TaskTableModel>(model);
        table.setRowSorter(sorter);
    }

    // FILTER BY PRIORITY OR DUE DATE
    public void applyFilter(String filter) {

        if (filter == null
                || filter.equals("All")) {
            sorter.setRowFilter(null);

        } else if (filter.equals(
                "High Priority")) {
            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "High", 2));

        } else if (filter.equals(
                "Medium Priority")) {
            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "Medium", 2));

        } else if (filter.equals(
                "Low Priority")) {
            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "Low", 2));

        } else if (filter.equals(
                "Due Today")) {
            java.text.SimpleDateFormat sdf =
                    new java.text
                    .SimpleDateFormat(
                            "MM/dd/yyyy");
            String today = sdf.format(
                    new java.util.Date());
            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            today, 1));
        }
    }

    // SORT BY COLUMN
    public void applySort(String sortBy) {

        List<RowSorter.SortKey> sortKeys =
                new ArrayList<
                        RowSorter.SortKey>();

        if (sortBy.equals("Priority")) {
            sortKeys.add(
                    new RowSorter.SortKey(
                            2,
                            SortOrder.ASCENDING));

        } else if (sortBy.equals(
                "Due Date")) {
            sortKeys.add(
                    new RowSorter.SortKey(
                            1,
                            SortOrder.ASCENDING));

        } else if (sortBy.equals("Status")) {
            sortKeys.add(
                    new RowSorter.SortKey(
                            3,
                            SortOrder.ASCENDING));
        }

        sorter.setSortKeys(sortKeys);
    }
}
	
	

