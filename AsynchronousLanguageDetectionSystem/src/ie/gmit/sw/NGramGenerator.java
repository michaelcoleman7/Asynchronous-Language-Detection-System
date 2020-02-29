package ie.gmit.sw;

/**
 * Class used to generate Ngrams
 * @author Michael Coleman
 *
 */
public class NGramGenerator implements Generator {
	private String line;
	private String lang;
	
	/**
	 * Method used to generate Ngrams of a given string and language
	 * @param lineObj Line object used to give access to both line and language
	 */
	public void generateNgram(Line lineObj) {
		line = lineObj.getLineString();
		lang = lineObj.getLanguage();
		
		//iterate over the line for every 4 characters
		for (int i = 0; i < line.length(); i+=4) {	
			//run 4 times to create set of 4 ngrams (i.e. 1-gram, 2-gram, 3-gram, 4-gram)
			for (int j = 1; j <= 4; j++) {
				String ngramString = line.substring(i, Math.min(i + j, line.length()));
				
				//encode ngram using default method in Generator interface
				int encodedNGram = (int) encode(ngramString);
				
				//create new ngram using encoded ngram string
				NGram ngram= new NGram(encodedNGram,0);
				
				//create new NgramObj with encoded ngram and language
				NGramObj nGramObj= new NGramObj(ngram,lang);
				try {
					//add to queue to handle passing to database (DatabasePopulator)
					NGramQueue.add(nGramObj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
