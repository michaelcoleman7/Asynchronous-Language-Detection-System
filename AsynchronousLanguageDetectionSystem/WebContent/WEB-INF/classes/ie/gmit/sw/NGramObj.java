package ie.gmit.sw;

public class NGramObj {
	/**
	 * Class to contain an NGram and a language string
	 */
	private NGram ngram;
	private String lang;

	/**
	 * Constructor for NGramObj
	 * @param ngram set NGram to given NGram
	 * @param lang String used to set language
	 */
	public NGramObj(NGram ngram, String lang) {
		super();
		this.ngram = ngram;
		this.lang = lang;
	}
	/**
	 * Getter method for NGram
	 * @return returns the NGram object
	 */
	public NGram getNgram() {
		return ngram;
	}
	/**
	 * Getter method for language
	 * @return returns a string of the language
	 */
	public String getLang() {
		return lang;
	}	

}
