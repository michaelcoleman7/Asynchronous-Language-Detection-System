package ie.gmit.sw;

import java.util.Map;
import java.util.TreeMap;
/**
 * Class used to map Ngram objects to be compared with database entries
 * @author Michael Coleman
 *
 */
public class QueryMapper {
	private Map<Integer, NGram> qm = new TreeMap<>();
	
	/**
	 * Method which adds ngrams to the map
	 * @param ngram CharSequence ngram which is used to get its hashcode for storing
	 */
	public void add(CharSequence ngram) {
		int kmer = ngram.hashCode();
		
		int frequency = 1;
		if (qm.containsKey(kmer)) {
			frequency += qm.get(kmer).getFrequency();
		}
		qm.put(kmer, new NGram(kmer, frequency));	
	}
	
	/**
	 * method to return the querymapper map entryset
	 * @return Map of integers and NGrams
	 */
	public Map<Integer, NGram> entryset() {
		return qm;
	}
}
