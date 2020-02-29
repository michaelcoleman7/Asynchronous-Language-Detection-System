package ie.gmit.sw;
/**
 * Class used for passing of user Requests
 * @author Michael
 *
 */
public class Request {
	private String query;
	private String taskNumber;	
	
	/**
	 * Request Constructor
	 * @param query string received from user query
	 * @param taskNumber String received as the users requests task number
	 */
	public Request(String query, String taskNumber) {
		this.query = query;
		this.taskNumber = taskNumber;
	}
	/**
	 * Getter method for query
	 * @return returns the string of query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * Getter method for taskNumber
	 * @return returns the string of taskNumber
	 */
	public String getTaskNumber() {
		return taskNumber;
	}
}
