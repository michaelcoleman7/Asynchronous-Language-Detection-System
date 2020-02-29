package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
/**
 * Class used to populate a Database via use of a queue
 * @author Michael Coleman
 *
 */
public class DatabasePopulator {
	private Database database;
	private BlockingQueue<NGramObj> queue;

	public DatabasePopulator(Database database) {
		this.database = database;
		populate(this.database);
	}

	/**
	 * populate method which is used in order to populate the parameter given database using a NgramQueue
	 * @param database Database which is used to be populated
	 */
	private void populate(Database database){
		queue = NGramQueue.getInstance();
		
		//keep looping until queue is poisoned
		Boolean poisoned = false;
		while (!poisoned) {
			
			//pull ngram object from the queue
			NGramObj nGramObj = queue.poll();

			//poison queue
			if(nGramObj == null){
				poisoned = true;
				break;
			}
			
			//create ngram in format to be added to database
			CharSequence charSequence = Integer.toString(nGramObj.getNgram().getKmer());
			
			Language lang;
			try {
				//get language from ngramObj
				lang = Language.valueOf(nGramObj.getLang());				
				
				//add to database
				database.add(charSequence,lang);
			} catch (Exception e) {
				//All language which shouldnt be saved are caught here
			}
		}
		database.resize(300);
	}

}
