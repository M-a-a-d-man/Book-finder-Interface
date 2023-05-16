package errors;

/**
 * @author evanteboul
 *
 */
public class TooFewFieldsException extends Exception {
	
	public TooFewFieldsException() {
		super("Too few fields");
	}

}
