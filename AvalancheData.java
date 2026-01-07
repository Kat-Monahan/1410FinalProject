package final01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles a collection of avalanche data read in from a csv file piece by piece
 * that corresponds to a certain attribute of the avalanche. Reads from
 * (resources/avalanchedata.csv) Stores avalanche objects in an array so the
 * avalanche data can be worked with and displayed.
 *
 * 
 * @author KMonahan
 */

public class AvalancheData {

	private List<Avalanche> avalanches = new ArrayList<>();

	public AvalancheData() {
		String file = "resources/AvalancheData.csv";

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			reader.readLine();

			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				String date = data[0];
				String region = data[1];
				String place = data[2];
				String trigger = data[3];
				String depth = data[4];
				String width = data[5];
				String vertical = data[6];
				String elevation = data[7];
				String fatalities = data[8];

				Avalanche a = new Avalanche(date, region, place, trigger, depth, width, vertical, elevation,
						fatalities);
				avalanches.add(a);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File couldn't be found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something went wrong");
		}
	}

	/**
	 * Returns the list of all avalanche objects
	 * 
	 * @return the avalanches
	 */
	public List<Avalanche> getAvalanches() {
		return avalanches;
	}

}