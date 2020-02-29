package ie.gmit.sw;

import java.util.*;
/**
 * Database class which is used to populate a Hashmap with a set of Ngrams
 */
import java.util.concurrent.ConcurrentHashMap;
public class Database{
	//Concurrenthashmap used as it is more thread safe than a treemap. Plus get and put are o(1) in comparison with log(n) 
	private Map<Language, Map<Integer, NGram>> db = new ConcurrentHashMap<>();
	
	/**
	 * Method used to return the size of the database
	 * @return Returns an int of the database size
	 */
	public int length() {
		return db.size();
	}
	
	/**
	 * Return the set of languages used a the keyset in the database
	 * @return Returns a set of languages
	 */
	public Set<Language> keyset() {
		return db.keySet();
	}
	
	/**
	 * 
	 * @param ngram CharSequence which refers to the individual Ngram
	 * @param lang Language object which contains Ngrams language information
	 */
	public void add(CharSequence ngram, Language lang) {
		int kmer = ngram.hashCode();
		Map<Integer, NGram> langDb = getLanguageEntries(lang);
		
		int frequency = 1;
		if (langDb.containsKey(kmer)) {
			frequency += langDb.get(kmer).getFrequency();
		}
		langDb.put(kmer, new NGram(kmer, frequency));	
	}
	
	/**
	 * 
	 * @param lang Language which is used to get all ngrams associated with that language
	 * @return returns a map containing language and associated ngrams
	 */
	public Map<Integer, NGram> getLanguageEntries(Language lang){
		Map<Integer, NGram> langDb = null; 
		if (db.containsKey(lang)) {
			langDb = db.get(lang);
		}else {
			langDb = new ConcurrentHashMap<Integer, NGram>();
			db.put(lang, langDb);
		}
		return langDb;
	}
	
	/**
	 * Method used to resize the database to a set value
	 * @param max size value for database resize
	 */
	public void resize(int max) {
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			Map<Integer, NGram> top = getTop(max, lang);
			db.put(lang, top);
		}
	}
	
	/**
	 * method used to return the top ngrams within a language
	 * @param max value used to get the top n ngrams, where n = max
	 * @param lang lang refers to the language to get the max ngrams from
	 * @return Returns a Map of a language and associated top max ngrams
	 */
	private Map<Integer, NGram> getTop(int max, Language lang) {
		Map<Integer, NGram> temp = new ConcurrentHashMap<>();
		Set<NGram> ngramSet = new TreeSet<>(db.get(lang).values());

		int rank = 1;
		for (NGram n : ngramSet) {
			n.setRank(rank);
			temp.put(n.getKmer(), n);			
			if (rank == max) break;
			rank++;
		}	
		return temp;
	}
	
	/**
	 * Method used to return the language prediction
	 * @param query this is the queried language map with which to find the result from the database
	 * @return returns the language which is the predicted result
	 */
	public Language getLanguageResult(Map<Integer, NGram> query) {
		TreeSet<OutOfPlaceMetric> oopmSet = new TreeSet<>();
		
		Set<Language> langs = db.keySet();
		for (Language lang : langs) {
			oopmSet.add(new OutOfPlaceMetric(lang, getOutOfPlaceDistance(query, db.get(lang))));
		}
		return oopmSet.first().getLanguage();
	}
	
	/**
	 * 
	 * @param query language string with which to check if it exists within the language keyset
	 * @return returns a boolean, true if in database, false if not
	 */
	public boolean getlanguageExistence(String query) {
		Set<Language> langs = keyset();
		for (Language lang : langs) {
			if(lang.getLanguageName().toString().equalsIgnoreCase(query)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param query query map with which to compare against subject query
	 * @param subject subject map with which to compare against the query map
	 * @return Returns an int which refers to the out of distance between query and subject
	 */
	private int getOutOfPlaceDistance(Map<Integer, NGram> query, Map<Integer, NGram> subject) {
		int distance = 0;
		
		Set<NGram> queryNgrams = new TreeSet<>(query.values());		
		for (NGram q : queryNgrams) {
			NGram nGram = subject.get(q.getKmer());
			if (nGram == null) {
				distance += subject.size() + 1;
			}else {
				distance += nGram.getRank() - q.getRank();
			}
		}
		return distance;
	}
}