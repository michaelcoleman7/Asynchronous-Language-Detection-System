package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
/**
 * Database populator class for the query database map, populates with ngrams and its associated languages
 * @author Michael
 *
 */
public class QueryDatabasePopulator {
	private QueryMapper querymapper;
	private BlockingQueue<NGram> queue;

	/**
	 * QueryDatabasePopulator constructor 
	 * @param querymapper QueryMapper Object which is used as query database
	 */
	public QueryDatabasePopulator(QueryMapper querymapper) {
		this.querymapper = querymapper;
		populate(this.querymapper);
	}

	/**
	 * Method which populates query mapper by use of a QueryNgramQueue
	 * @param querymapper QueryMapper Object which is used as query database
	 */
	private void populate(QueryMapper querymapper){
		queue = QueryNgramQueue.getInstance();
		
		Boolean poisoned = false;
		while (!poisoned) {
			
			NGram nGram = queue.poll();
			
			if(nGram == null){
				poisoned = true;
				break;
			}
			CharSequence charSequence = Integer.toString(nGram.getKmer());

			querymapper.add(charSequence);
		}
	}
}
