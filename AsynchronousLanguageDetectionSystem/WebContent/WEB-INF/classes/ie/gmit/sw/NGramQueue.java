package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class which contains an single instance of LinkedBlockingQueue of NGramObjs, used to populated database
 * @author Michael Coleman
 *
 */

public class NGramQueue {
	private static BlockingQueue<NGramObj> queue = new LinkedBlockingQueue<NGramObj>();

	/**
	 * Private constructor for creating a NGramQueue
	 * @param queue NGramObj queue used to create queue
	 */
	@SuppressWarnings("static-access")
	private NGramQueue(BlockingQueue<NGramObj> queue) {
		this.queue = queue;
	}
	
	/**
	 * Method used to get an instance of the NgramObj queue
	 * @return Returns instance of an BlockingQueue of NGramObjs
	 */
	public static BlockingQueue<NGramObj> getInstance() {
		return queue;
	}

	/**
	 * Method to add an NGramObj to the queue
	 * @param ngram NGramObj that is to be added to queue
	 * @throws Exception If unable to add to queue
	 */
	public static void add(NGramObj ngram) throws Exception {
		queue.put(ngram);
	}
	
}
