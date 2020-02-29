package ie.gmit.sw;
/**
 * Class that is used to generate NGrams for a query text
 * @author Michael Coleman
 *
 */
public class QueryNGramGenerator implements Generator{
	/**
	 * Method which creates ngrams from query string and adds them to query queue
	 * @param query String which is used to create NGrams from
	 */
	public void generateNgram(String query) {
		//iterate over the line for every 4 characters
		for (int i = 0; i < query.length(); i+=4) {
			//run 4 times to create set of 4 ngrams (i.e. 1-gram, 2-gram, 3-gram, 4-gram)
			for (int j = 1; j <= 4; j++) {
				String ngram = query.substring(i, Math.min(i + j, query.length()));
				
				//encode ngram using default method in Generator interface
				int encodedNGram = (int) encode(ngram);
				
				//create new ngram using encoded ngram string
				NGram tempNgram= new NGram(encodedNGram,0);
				
				try {
					//add to queue to handle passing to database (QueryDatabasePopulator)
					QueryNgramQueue.add(tempNgram);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}		
		}
	}
}
