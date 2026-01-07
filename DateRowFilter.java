package final01;

/**
 * Filters rows in a JTable based on a date string
 * Converts Dates in the first column to strings and checks if they contain the string the user entered
 * 
 * @author KMonahan
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.RowFilter;

public class DateRowFilter extends RowFilter<Object, Object> {

	private String dateString;
	private SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy"); // fixed this

	DateRowFilter(String dateString) {
		this.dateString = dateString;

	}

	@Override
	public boolean include(Entry<?, ?> entry) {

		Date date = (Date) entry.getValue(0);

		String formattedDate = formatter.format(date);
		return formattedDate.contains(dateString);

	

	}

}
