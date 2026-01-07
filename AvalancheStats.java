package final01;

/**
 * A simple data class that stores stats information about a collection of
 * Avalanche objects.
 * 
 * @author LatifahW
 */
public class AvalancheStats {
	public int totalAvalanches;
	public double averageDepth;
	public int totalFatalities;

	public AvalancheStats(int total, double avgDepth, int fatalities) {
		this.totalAvalanches = total;
		this.averageDepth = avgDepth;
		this.totalFatalities = fatalities;
	}
}
