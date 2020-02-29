package ie.gmit.sw;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This is a servlet handler class which is used to carry out the passing of messages from both in and out queues to process user requests
 * @author Michael Coleman
 *
 */

@SuppressWarnings("serial")
public class ServiceHandler extends HttpServlet {
	private String languageDataSet = null; //This variable is shared by all HTTP requests for the servlet
	private static long jobNumber = 0; //The number of the task in the async queue

	private Map<String, Language> outQueue;
	private List<Request> inQueue = new LinkedList<>();
	private Database database;
	private ThreadHandler th =  new ThreadHandler();
	
	/**
	 * Init method used to initialise the servlet e.g. populate database
	 */
	public void init() throws ServletException {
		//Using FileDialog as I have heard issues exist on mac using JFileChooser
		//Adapted from https://stackoverflow.com/a/1357214
		JFrame frame = new JFrame();
		System.setProperty("apple.awt.fileDialogForDirectories", "true");
	    FileDialog d = new FileDialog(frame, "Open", FileDialog.LOAD);
	    d.setVisible(true);
	    String path = d.getDirectory() + d.getFile();
	    File f = new File(path);
	    languageDataSet = f.getAbsolutePath();
	   
		if(d.getFile() == null) {
			
		}else {
			FileReader fr = new FileReader();
			try {
				//read the language file
				fr.readUrl(languageDataSet);
				//create new database and populate it
				database = new Database();
				@SuppressWarnings("unused") //used in constructor to populate via queue
				DatabasePopulator dp = new DatabasePopulator(database);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get method to carry out the get requests using the user options
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html"); //Output the MIME type
		PrintWriter out = resp.getWriter(); //Write out text. We can write out binary too and change the MIME type...

		//Initialise some request variables with the submitted form info. These are local to this method and thread safe...
		String option = req.getParameter("cmbOptions"); //Change options to whatever you think adds value to your assignment...
		String query = req.getParameter("query");
		String taskNumber = req.getParameter("frmTaskNumber");
		Language language = null;


		out.print("<html><head><title>Advanced Object Oriented Software Development Assignment</title>");
		out.print("</head>");
		out.print("<body>");

		if (taskNumber == null){
			//create new task number
			taskNumber = new String("T" + jobNumber);
			jobNumber++;
			
			//if empty query is entered
			if(query.equalsIgnoreCase("") && option.equalsIgnoreCase("Language Detection")){
				out.print("<H1>Error: Cannot process an empty query, Please enter some text</H1>");
			}
			else {
				if(option.equalsIgnoreCase("Language Detection") && database!=null) {
					//output processing request info to user
					out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
				}
				// create a new user request
				Request r = new Request(query, taskNumber);
				
				//Add request to in-queue
				((LinkedList<Request>) inQueue).addFirst(r);
			}
			
			
		}else{
			outQueue= OutQueue.getInstance();
			//Check out-queue for finished job
			if(outQueue.containsKey(taskNumber)){
				//get language result from outQueue using associated taskNumber
				language = outQueue.get(taskNumber);
				
				//output prediction
				out.print("<H1>Language Prediction: "+ language.getLanguageName() +"</H1>");
				//remove task from outQueue
				outQueue.remove(taskNumber);
			}	
		}

		//if user selects language detection option
		if(option.equalsIgnoreCase("Language Detection")) {
			if(database == null) {
				out.print("<H1>Database is empty, Please populate database</H1>");
			}
			else{
				if(!inQueue.isEmpty()) {
					th.generateQueryThread(inQueue.get(0), inQueue, database);
					inQueue.remove(0);
				}
				out.print("<meta http-equiv=\"refresh\" content=\"10\">");
			}
		}
		//if user selects language existence option - i.e does a specified language exist in the database
		else if(option.equalsIgnoreCase("Language Existence")) {
			if(query.equalsIgnoreCase("")){
				out.print("<H1>Error: Cannot process an empty query, Please enter some text</H1>");
			}
			if(database == null) {
				out.print("<H1>Database is empty, Please populate database</H1>");
			}
			else{
				//find if language exists in the database
				Boolean languageExists = database.getlanguageExistence(query);
				if(languageExists) {
					//print result of existence to user
					out.print("<H1>Language: "+query+" - exists in the database</H1>");
					
					//create language object from query
					Language lang = Language.valueOf(query);
					Map<Integer, NGram> map = database.getLanguageEntries(lang);
					
					//out put the amount of ngrams for that language in the database
					out.print("<p>Language has "+map.size()+" ngram entries</p>");
				}else {
					//print result of languages lack of existence in database
					out.print("<H1>Language: "+query+" - does NOT exist in the database</H1>");
				}
			}
		}
		//if user selects database languages option - i.e show all languages in database
		else if(option.equalsIgnoreCase("Database Languages")) {
			if(database == null) {
				out.print("<H1>Database is empty, Please populate database</H1>");
			}
			else{
				//print out amount of languages that exist in database
				out.print("<H1>Languages in the Database: "+ database.length() +"</H1>");
				out.print("<OL>");
				//print out list of all languages in database
				for(Language lang: database.keyset()) {
					out.print("<LI>"+lang.getLanguageName()+"</p>");
				}
				out.print("</OL>");
			}
		}
		else if(option.equalsIgnoreCase("Repopulate Database")) {
			if(query.equalsIgnoreCase("")){
				out.print("<H1>Error: Cannot repopulate using an empty query, Please enter a file url</H1>");
			}else {
				Boolean successRepopulating = repopulate(query);
				if(successRepopulating) {
					out.print("<H1>Database Repopulated</H1>");
				}else {
					out.print("<H1>Error: Cannot repopulate using url provided, ensure file path is correct</H1>");
				}				
			}
		}
		out.print("<div id=\"r\"></div>");

		out.print("<font color=\"#993333\"><b>");
		out.print("<br>Option(s): " + option);
		out.print("<br>Query Text : " + query);
		out.print("<br><a href=http://localhost:8080/ngrams/>Back to Main Menu</a>");
		out.print("</font><p/>");

		out.print("<form method=\"POST\" name=\"frmRequestDetails\">");
		out.print("<input name=\"cmbOptions\" type=\"hidden\" value=\"" + option + "\">");
		out.print("<input name=\"query\" type=\"hidden\" value=\"" + query + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
		out.print("</script>");


	}
	
	/**
	 * Method to re-populate the database
	 * @param query String which is used as the path to the file used to re-populate database
	 * @return Boolean returning success or failure of file populating database
	 */
	public Boolean repopulate(String query){
		File f = new File(query);
		if(f.canRead()) {
			FileReader fr = new FileReader();
			try {
				//read the language file
				fr.readUrl(query);
				//create new database and populate it
				database = new Database();
				@SuppressWarnings("unused") //used in constructor to populate via queue
				DatabasePopulator dp = new DatabasePopulator(database);

			}catch(Exception e){
				e.printStackTrace();
			}	
			return true;
		}else {
			return false;
		}

 	}

	/**
	 * Post request which calls the get request
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}