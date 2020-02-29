package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * Class which contains an single instance of LinkedBlockingQueue of NGrams, used to populated a QueryMapper
 * @author Michael Coleman
 *
 */
public class QueryNgramQueue {
	private static BlockingQueue<NGram> queue = new LinkedBlockingQueue<NGram>();
	/**
	 * Private constructor for creating a QueryNgramQueue
	 * @param queue BlockingQueue<NGram> used to create queue
	 */
	@SuppressWarnings("static-access")
	private QueryNgramQueue(BlockingQueue<NGram> queue) {
		this.queue = queue;
	}
	/**
	 * Method used to get an instance of the QueryNgram queue
	 * @return Returns instance of an BlockingQueue of NGrams
	 */
	public static BlockingQueue<NGram> getInstance() {
		return queue;
	}
	/**
	 * Method to add an NGram to the queue
	 * @param ngram NGram that is to be added to queue
	 * @throws Exception If unable to add to queue
	 */
	public static void add(NGram ngram) throws Exception {
		queue.put(ngram);
	}
}
