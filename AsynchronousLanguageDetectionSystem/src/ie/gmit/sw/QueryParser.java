package ie.gmit.sw;
/**
 * Class used to parse a query
 * @author Michael Coleman
 *
 */
public class QueryParser {	
	/**
	 * Parse method to parse a query string
	 * @param query String used a basis of query
	 * @return query in abbreviated format
	 */
	public String parse(String query){
		return query = QueryAbbreviation(query);   
	}
	
	/**
	 * QueryAbbreviation method which abbreviates query to 400 characters
	 * @param query String used a basis of query
	 * @return query in abbreviated format
	 */
	private String QueryAbbreviation(String query){
		query = query.substring(0, Math.min(query.length(), 400));
		return query;
	}
}
