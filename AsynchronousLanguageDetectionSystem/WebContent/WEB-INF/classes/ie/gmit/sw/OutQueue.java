package ie.gmit.sw;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
/**
 * Class used to create a single instance of a ConcurrentSkipListMap used in the servlet to return user task results
 * @author Michael Coleman
 *
 */
public class OutQueue {
	private static Map<String, Language> queue = new ConcurrentSkipListMap<String, Language>();
	
	/**
	 * Private constructor for creating a outQueue
	 * @param queue map of string and language objects used to create queue
	 */
	@SuppressWarnings("static-access")
	private OutQueue(Map<String, Language> queue) {
		this.queue = queue;
	}
	/**
	 * Method used to get an instance of the OutQueue
	 * @return Returns instance of a Map of strings and languages
	 */
	public static Map<String, Language> getInstance() {
		return queue;
	}
	/**
	 * Method to add a tasknumber and a Language to the queue
	 * @param taskNum String which is users task number
	 * @param lang Language object used in map
	 * @throws Exception if unable to add to queue
	 */

	public static void add(String taskNum, Language lang) throws Exception {
		queue.put(taskNum, lang);
	}
	
}
