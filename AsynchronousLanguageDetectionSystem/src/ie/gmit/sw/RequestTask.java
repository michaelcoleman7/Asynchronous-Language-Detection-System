package ie.gmit.sw;

import java.util.Map;
/**
 * Class used to create threads of user requests
 * @author Michael Coleman
 *
 */
public class RequestTask implements Runnable{
	private Request request;
	private Database database;
	private Map<String, Language> outQueue;
	/**
	 * 
	 * @param r Request object with user query and task number
	 * @param d instance of database for query comparison
	 */
	public RequestTask(Request r, Database d) {
		request=r;
		database = d;
	}

	/**
	 * Run method to be called to process a user query
	 */
	@Override
	public void run() {
		outQueue = OutQueue.getInstance();
		
		QueryParser qp = new QueryParser();
		
		//parse request query
		String parsedQuery = qp.parse(request.getQuery());
		QueryNGramGenerator queryNgGenerator = new QueryNGramGenerator();

		//generate ngrams for parsed query
		queryNgGenerator.generateNgram(parsedQuery);
		
		//create QueryMappper and populate it
		QueryMapper qm = new QueryMapper();
		@SuppressWarnings("unused") // used in constructor to populate via queue
		QueryDatabasePopulator qdm = new QueryDatabasePopulator(qm);
		
		//Get the result of the language query
		Language result = database.getLanguageResult(qm.entryset());
		
		//put result onto outQueue to be returned to the user
		outQueue.put(request.getTaskNumber(), result);	
	}

}
