package errors;

/**
 * @author evanteboul
 *
 */
public class MissingFieldException extends Exception{
private String message ="Missing field";

public MissingFieldException() {
	super();
}
public MissingFieldException(String message) {
	super();
	this.message=message;
}
public String getMessage() {
	return message;
}

}
