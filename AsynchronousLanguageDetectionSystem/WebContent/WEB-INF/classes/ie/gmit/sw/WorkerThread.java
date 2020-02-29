package ie.gmit.sw;
/**
 * Class that is used as a thread to process lines and generate NGrams
 * @author Michael
 *
 */
public class WorkerThread implements Runnable {

	private String line;
	private LanguageParser lp = new LanguageParser();
	private NGramGenerator ngGenerator = new NGramGenerator();
	
	/**
	 * WorkerThread Constructor 
	 * @param line string used for parsing line and language
	 */
	public WorkerThread(String line) {
		this.line=line;
	}
	/**
	 * Run method used to parse lines and create NGrams
	 */
	@Override
	public void run() {
		Line pl = parse(line);	
		generate(pl);
	}
	
	/**
	 * Method used to parse line into its text and language values
	 * @param line string used for parsing line and language
	 * @return Line object with parsed string split into text and language
	 */
	public Line parse(String line) {	
		Line ParsedLine = lp.parse(line);
		return ParsedLine;
	}
	
	/**
	 * Method to generate NGrams from a parsed Line Object
	 * @param parsedLine Line object containing parsed line into text and language
	 */
	public void generate(Line parsedLine){
		ngGenerator.generateNgram(parsedLine);
	}
}
