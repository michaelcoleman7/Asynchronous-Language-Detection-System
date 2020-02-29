package ie.gmit.sw;

import java.io.FileInputStream;
import java.util.Scanner;
/**
 * Class which is used to read in the file and send to threads to be parsed line by line
 * @author Michael Coleman
 *
 */
public class FileReader{
	private ThreadHandler th =  new ThreadHandler();
	/**
	 * Method used to read file and output each line to thread handler
	 * @param url This refers to the files name and directory
	 * @throws Exception if cannot parse file
	 */
	public void readUrl(String url) throws Exception{
		 
		FileInputStream inputStream = null;
		Scanner scanner = null;
		try {
		    inputStream = new FileInputStream(url);
		    scanner = new Scanner(inputStream);
		    while (scanner.hasNextLine()) {
		    	//read in next line
		        String line = scanner.nextLine();
		        
		        //pass line to thread handler
		        th.generateNgramThread(line);
		        
		    }
		    //shutdown executor when all lines have been sent to threads and parsed
		    th.ngramShutdown();
		    if (scanner.ioException() != null) {
		        throw scanner.ioException();
		    }
		} finally {
		    if (inputStream != null) {
		    	//close input stream
		        inputStream.close();
		    }
		    if (scanner != null) {
		    	//close scanner
		    	scanner.close();
		    }
		    
		}
			
	}
}
