package final01;

import java.util.Date;

/**
 * Create a single avalanche object Stores information like date, location,
 * measurements, trigger, and fatalities Converts String inputs for measurements
 * and dates into usable formats for sorting/filtering
 * 
 * @author LatifahW
 */
public class Avalanche {

	private Date date;
	private String region;
	private String place;
	private Double depth;
	private Double elevation;
	private Double vertical;
	private Double width;
	private String trigger;
	private String fatalities;

	/**
	 * Creates an Avalanche object from the CSV data Converts string measurements to
	 * Double where appropriate and date strings to Date.
	 * 
	 * @param date       String representing the date in MM/DD/YYYY format
	 * @param region     Avalanche region
	 * @param place      Specific place of the avalanche
	 * @param trigger    Cause of the avalanche
	 * @param depth      Depth in feet/inches (String)
	 * @param width      Width in feet/inches (String)
	 * @param vertical   Vertical drop in feet/inches (String)
	 * @param elevation  Elevation in feet/inches (String)
	 * @param fatalities Number of fatalities (String)
	 */
	public Avalanche(String date, String region, String place, String trigger, String depth, String width,
			String vertical, String elevation, String fatalities) {

		this.date = parseDate(date);
		this.region = region;
		this.place = place;
		this.depth = parseMeasurements(depth);
		this.elevation = parseMeasurements(elevation);
		this.vertical = parseMeasurements(vertical);
		this.width = parseMeasurements(width);
		this.trigger = trigger;
		this.fatalities = fatalities;
	}

	/**
	 * @return the date of the avalanche
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the avalanche region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @return the specific place of the avalanche
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @return depth in feet (Double)
	 */
	public Double getDepth() {
		return depth;
	}

	/**
	 * @return elevation in feet (Double)
	 */
	public Double getElevation() {
		return elevation;
	}

	/**
	 * @return vertical drop in feet (Double)
	 */
	public Double getVertical() {
		return vertical;
	}

	/**
	 * @return width in feet (Double)
	 */
	public Double getWidth() {
		return width;
	}

	/**
	 * @return the trigger/cause of the avalanche
	 */
	public String getTrigger() {
		return trigger;
	}

	/**
	 * @return fatalities as a String
	 */
	public String getFatalities() {
		return fatalities;
	}

	/**
	 * Converts a measurement String (like "5'6\"" or "12") into feet as a Double.
	 * Returns null for unknown or empty values.
	 * 
	 * @param stringRepresentation String representing a measurement
	 * @return measurement in feet as Double, or null if invalid
	 */
	private Double parseMeasurements(String stringRepresentation) {
		if (stringRepresentation == null)
			return null;

		stringRepresentation = stringRepresentation.trim().toLowerCase();
		if (stringRepresentation.isBlank() || stringRepresentation.equals("unknown"))
			return null;

		stringRepresentation = stringRepresentation.replace("\"", "").replace(",", "");
		if (stringRepresentation.contains("'")) {
			String[] splitMeasurement = stringRepresentation.split("'");
			Double feet = Double.parseDouble(splitMeasurement[0]);
			Double inches = (splitMeasurement.length > 1) ? Double.parseDouble(splitMeasurement[1]) : 0.0;
			return feet + inches / 12;
		} else {
			return Double.parseDouble(stringRepresentation) / 12;
		}
	}

	/**
	 * Converts a date string in MM/DD/YYYY format into a Date object. Returns null
	 * null input
	 * 
	 * @param date String date in MM/DD/YYYY
	 * @return Date object
	 */
	public static Date parseDate(String date) {
		if (date == null)
			return null;

		String[] splitDate = date.split("/");
		int month = Integer.parseInt(splitDate[0]) - 1;
		int day = Integer.parseInt(splitDate[1]);
		int year = Integer.parseInt(splitDate[2]) - 1900;

		return new Date(year, month, day);
	}
}