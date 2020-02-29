package ie.gmit.sw;
/**
 * Class used to store a string which is to be split into ngrams and its language
 * @author Michael Coleman
 *
 */
public class Line {

	private String lineString;
	private String language;
	
	/**
	 * 
	 * @param lineString string of text to be used as ngrams
	 * @param language string of associated language
	 */
	public Line(String lineString, String language) {
		super();
		this.lineString = lineString;
		this.language = language;
	}

	/**
	 * Getter method for lineString
	 * @return returns a string of the line of text
	 */
	public String getLineString() {
		return lineString;
	}
	/**
	 * Getter method for language
	 * @return returns a string of the language
	 */
	public String getLanguage() {
		return language;
	}	
}