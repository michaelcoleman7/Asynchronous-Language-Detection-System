package ie.gmit.sw;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Class used to handle the execution of threads
 * @author Michael Coleman
 *
 */
public class ThreadHandler{
	
	private ExecutorService executor;
	/**
	 * ThreadHandler constructor
	 */
	public ThreadHandler() {
		 executor = Executors.newSingleThreadExecutor();      
	}
	
	/**
	 * Method to generate new worker threads, which process ngrams
	 * @param line String used to send to WorkerThread to process
	 */
	public void generateNgramThread(String line) {
		executor.execute(new WorkerThread(line));
		if(line == null){
			executor.shutdown();
		}
	}
	/**
	 * Method to create threads which process user requests
	 * @param request User Request object
	 * @param requestList List of user requests in queue
	 * @param d Database to compare user request to
	 */
	public void generateQueryThread(Request request, List<Request> requestList, Database d) {
		ExecutorService executorProducer = Executors.newSingleThreadExecutor();
		for(Request r: requestList){
			//if request is in the request list
			if(requestList.contains(request)){
				//execute Request task thread
				executorProducer.execute(new RequestTask(r, d));
			}
		}
		//shutdown executor service
		executorProducer.shutdown();
	}
	
	public void ngramShutdown(){
		//shutdown executor service
		executor.shutdown();
	}
	
}
