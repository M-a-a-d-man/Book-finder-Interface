package errors;


/**
 * created a new type of exception, which is a semantic error
 * @author evanteboul
 *
 */
public class UnknownGenreException extends Exception{
	
	public UnknownGenreException() {
		super();
	}
	public  UnknownGenreException(String x) {
		super(x);
	}
	
}