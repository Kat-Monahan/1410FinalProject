package final01;

/**
* Panel handles the JTable display of the data parsed from the CSV file.
* Contains methods for the event listener buttons to filter/sort by/store data into a file for the user.
* Reads in data from the AvalancheDate class.
* Utilizes TableRowSorter for basic sorting functions and basic filtering using RegexFilter
* Formats display using DefaultTableCellRenderer + DecimalFormat
*
* @author KMonahan
*/
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Date;
import java.text.DecimalFormat;

public class JTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;

	/**
	 * Create the panel.
	 */
	public JTablePanel() {
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		scrollPane.setViewportView(table);
		AvalancheData avalancheList = new AvalancheData();
		List<Avalanche> data = avalancheList.getAvalanches();
		String[] columnNames = { "Date", "Region", "Place", "Trigger", "Depth (ft) ", "Width (ft) ", "Vertical (ft) ",
				"Elevation (ft)", "Fatalities" };
		/**
		 * Overridden getColumnClass that manually tell the model what class each column
		 * is for proper sorting.
		 */
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
				case 0:
					return Date.class;
				case 4:
				case 5:
				case 6:
				case 7:
					return Double.class;
				default:
					return String.class;
				}
			}
		};

		for (Avalanche a : data) {
			Object[] row = { a.getDate(), a.getRegion(), a.getPlace(), a.getTrigger(), a.getDepth(), a.getWidth(),
					a.getVertical(), a.getElevation(), a.getFatalities() };
			model.addRow(row);
		}
		table.setModel(model);
		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);
		/**
		 * Anonymous class that overrides setValue for a DefaultCellRenderer. Uses
		 * DecimalFormat to show on the JTable formatted versions of the values. Uses
		 * format #,#0.00 for rounding to the thousands.
		 */
		DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer() {
			private final DecimalFormat formatCells = new DecimalFormat("#,##0.00");

			@Override
			protected void setValue(Object value) {
				if (value instanceof Number) {
					value = formatCells.format(value);
				}
				super.setValue(value);
			}
		};
		table.getColumnModel().getColumn(4).setCellRenderer(cellRender);
		table.getColumnModel().getColumn(5).setCellRenderer(cellRender);
		table.getColumnModel().getColumn(6).setCellRenderer(cellRender);
		table.getColumnModel().getColumn(7).setCellRenderer(cellRender);
	}

	/**
	 * Sorts by chosen column based on the columnIndex as well as a boolean value
	 * for ascending/descending1
	 *
	 * @param columnIndex the index of the specific column.
	 * @param ascending   whether the sorter is sorting in ascending or descending
	 *                    order
	 *
	 */
	public void sortByColumnIndex(int columnIndex, boolean ascending) {
		if (ascending) {
			List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
			sorter.sort();

		} else {
			List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(columnIndex, SortOrder.DESCENDING));
			sorter.setSortKeys(sortKeys);
			sorter.sort();

		}
	}

	/**
	 * Filters table rows by date string using DateRowFilter
	 * 
	 * @param date string to filter by
	 */
	public void dateFilter(String date) {
		sorter.setRowFilter(new DateRowFilter(date));
	}

	/**
	 * Filters table rows by place name (case-insensitive)
	 * 
	 * @param place string to filter by
	 */

	public void placeFilter(String place) {
		RowFilter<DefaultTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + place, 2);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	/**
	 * Filters table rows by trigger type (case-insensitive)
	 * 
	 * @param trigger string to sort by
	 */

	public void triggerFilter(String trigger) {
		RowFilter<DefaultTableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + trigger, 3);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	/**
	 * Resets all filters
	 */

	public void reset() {

		sorter.setRowFilter(null);

	}

	/**
	 * Calculates basic statistics from the table: total avalanches, average depth,
	 * and total fatalities.
	 * 
	 * @return AvalancheStats with calculated values
	 */

	public AvalancheStats calculateStats() {

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = table.getRowCount();
		int totalAvalanches = rowCount;
		double depthSum = 0;
		int depthCount = 0;
		int totalFatalities = 0;
		for (int i = 0; i < rowCount; i++) {
			int modelRow = table.convertRowIndexToModel(i);

			Object depthObj = model.getValueAt(modelRow, 4);
			if (depthObj instanceof Double) {
				depthSum += (Double) depthObj;
				depthCount++;
			}

			Object fatObj = model.getValueAt(modelRow, 8);
			if (fatObj instanceof String s && s.matches("\\d+")) {
				totalFatalities += Integer.parseInt(s);
			}
		}
		double avgDepth = (depthCount == 0) ? 0 : depthSum / depthCount;
		return new AvalancheStats(totalAvalanches, avgDepth, totalFatalities);
	}

	/**
	 * Saves the current table data to a CSV file.
	 */

	public void saveToFile() {
		String filename = "resources/toFile/AvalancheFile.csv";
		try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename))) {

			DefaultTableModel model = (DefaultTableModel) table.getModel();

			for (int i = 0; i < model.getColumnCount(); i++) {
				printWriter.print(model.getColumnName(i) + ",");
			}
			printWriter.println();
			for (int i = 0; i < table.getRowCount(); i++) {
				for (int x = 0; x < table.getColumnCount(); x++) {
					printWriter.print(table.getValueAt(i, x) + ",");
				}
				printWriter.println();

			}

		} catch (FileNotFoundException e) {
			System.out.println("The file was not found");
			e.printStackTrace();
		} catch (IOException e1) {
			System.out.println("There was an issue saving to a file");
			e1.printStackTrace();
		}

	}

}
