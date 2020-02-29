package ie.gmit.sw;
/**
 * Class used to create an OutOfPlaceMetric object
 *
 */
public class OutOfPlaceMetric implements Comparable<OutOfPlaceMetric>{
	private Language lang;
	private int distance;
	
	/**
	 * Constructor for OutOfPlaceMetric object
	 * @param lang Language Object used to set language
	 * @param distance int used to set distance
	 */
	public OutOfPlaceMetric(Language lang, int distance) {
		super();
		this.lang = lang;
		this.distance = distance;
	}
	/**
	 * Getter method for language
	 * @return returns a Language object of a language
	 */
	public Language getLanguage() {
		return lang;
	}
	/**
	 * Get the absolute distance of the metric
	 * @return int of the absolute distance
	 */
	public int getAbsoluteDistance() {
		return Math.abs(distance);
	}
	/**
	 * Comparison method to compare another OutOfPlaceMetric object to this OutOfPlaceMetric object
	 * @return int of the difference between OutOfPlaceMetric objects
	 */
	@Override
	public int compareTo(OutOfPlaceMetric o) {
		return Integer.compare(this.getAbsoluteDistance(), o.getAbsoluteDistance());
	}
}
