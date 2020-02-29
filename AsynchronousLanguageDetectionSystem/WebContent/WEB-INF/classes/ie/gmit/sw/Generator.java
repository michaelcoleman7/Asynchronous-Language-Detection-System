package ie.gmit.sw;
/**
 * Interface used for instances of generators 
 * @author Michael Coleman
 *
 */
public interface Generator {
	/**
	 * Method used to encode a string by shifting the bits for each string character
	 * @param s String which is to be encoded
	 * @return returns long of encoded string
	 */
	public default long encode(String s){
		if(s.length() < 1 || s.length()> 4)
			try {
				throw new Exception("Can only be ngram of size 1, 2, 3, or 4 characters");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		long sequence = 0x0000000000000000L; //set all 64 bits to zero
		for (int i = 0; i < s.length(); i++) { //loop over the string
			sequence <<= 16; //shift bits left by 16 bits
			sequence |= s.charAt(i); //bitwise OR. Sets the first (right most) bits to the unicode binary value
		}
		return sequence; //return chars encoded as a long
	}
}
