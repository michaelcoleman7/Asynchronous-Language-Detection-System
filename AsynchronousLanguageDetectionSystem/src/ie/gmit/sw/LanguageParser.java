package ie.gmit.sw;
/**
 * Class used to parse a language from a string
 * @author Michael Coleman
 *
 */
public class LanguageParser {
	/**
	 * Method used to parse line from its language
	 * @param str String to parse
	 * @return Line object of the split between the string and languages
	 */
	public Line parse(String str){
		//split line at @ symbol
		String[] tempLine = str.split("@");
		Line lineObj;
		
		//remove spaces in a language
		if(tempLine[1].contains(" ")){
			String[] tempLang = tempLine[1].split(" ");
			
			tempLine[1]= tempLang[0];
			
			//create Line object from both line splits
			lineObj = new Line(tempLine[0], tempLine[1]);
		}
		else{
			//create Line object from both line splits
			 lineObj = new Line(tempLine[0], tempLine[1]);
		}
        return lineObj;
	}
}
