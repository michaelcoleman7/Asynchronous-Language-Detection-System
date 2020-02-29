# Asynchronous-Language-Detection-System
4th Year - Semester 1 Java project which takes in a file and creates a language database from it, then uses that database to query a user entered input and detect which language the input is.

### About:
1. When ngrams.war is placed in the webapps folder of Tomcat when startup.bat is running(note: Tested using Tomcat 9 only), 
the user should be greeted with a option which alows them to select the file they wish to populate the system database with,
Note: This file should be a .txt file and is part of the extra features I added

2. The file is then processed in the init() method of the serlet and the database is populated.
This is done by reading in the file, line by line, each line is then sent a ThreadHandler class which is used
to parse the line into its text and language each on their own thread using an 
ExecutorService as it is more efficient than individually reading line by line and parsing and generating in order. 
These are then made into a Line object which is passed to the NGramGenerator
which breaks the line up in 4's and creates a 1-gram, 2-gram, 3-gram, 4-gram from each 4 characters in the line and then encoded. 
These ngrams are then placed on a queue which the database is populated with via a databse populator. 
The database is populated in the form of a Concurrenthashmap as it is more thread safe than a treemap. 
Plus gets and puts are o(1) in comparison with log(n) on the provided treemap making it more efficient.

3. When the user goes to localhost:8080/ngrams they are greeted with an options menu and a query box.
The user should start on the Langauage detection option. They can then enter some text and press the enter button. 
This will then process the query and attempt to make a prediction. 
It does so passing the user Request to a RequestTask thread which then carries out the processing the query.
This is done in a similar manor in a similar manner to the database population only using a set of query-linked classes.
The two maps for the query and database are then compared using the out of place metric and a prediction is returned. 
This result is put on the outQueue along with the users task number and displayed to them on the next refresh (10 second interval).



### Instructions:
- Download Tomcat 9
- To set up tomcat variables - use following commands in command terminal:
SET JAVA_HOME=dir/path/to/your/java(e.g. jre or jdk)
SET TOMCAT_HOME=dir/to/tomcat/installation/directory (e.g. apache-tomcat-9.0.30)
SET CATALINA_HOME=dir/to/tomcat/installation/directory (e.g. apache-tomcat-9.0.30)
- cd into tomcat bin directory
- run command: startup.bat
- Pull ngrams.war into the webapps directory
- Choose file when prompted via FileDialog option menu that you wish to populated database with 
or exit and populate database using option 4 in menu from next step below
- In a browser, navigate to localhost:8080/ngrams by entering into a url - note may take a few seconds to populate database, 
so if the servlet isnt up in time, a refresh or two should have it up - usually takes about 3-5 seconds to populate database
- User can then choose 1-4 options with different results (Option 1 is language detection)
