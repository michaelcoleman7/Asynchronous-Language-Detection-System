package ie.gmit.sw;
/**
 * Class used to store an ngram and its necessary information
 * @author Michael Coleman
 *
 */
public class NGram implements Comparable<NGram> {
	private int kmer;
	private int frequency;
	private int rank;

	/**
	 * Constructor for an ngram
	 * @param kmer int used to store kmer/ngram
	 * @param frequency int at which the ngram occurs
	 */
	public NGram(int kmer, int frequency) {
		super();
		this.kmer = kmer;
		this.frequency = frequency;
	}

	/**
	 * Getter method for kmer
	 * @return returns a int of kmer
	 */
	public int getKmer() {
		return kmer;
	}
	/**
	 * Setter method for kmer
	 * @param kmer int used to set kmer value
	 */
	public void setKmer(int kmer) {
		this.kmer = kmer;
	}
	/**
	 * Getter method for frequency
	 * @return returns a int of frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	/**
	 * Setter method for frequency
	 * @param frequency int used to set frequency value
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	/**
	 * Getter method for rank
	 * @return returns a int of rank
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * Setter method for rank
	 * @param rank int used to set rank value
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * comparison method between Ngrams
	 */
	@Override
	public int compareTo(NGram next) {
		return - Integer.compare(frequency, next.getFrequency());
	}
	
	/**
	 * toString method return details of the ngram as a string
	 */
	@Override
	public String toString() {
		return "[" + kmer + "/" + frequency + "/" + rank + "]";
	}
}