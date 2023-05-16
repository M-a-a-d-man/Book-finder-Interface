package errors;

public class TooManyFieldsException extends Exception {
	private String message;

	public TooManyFieldsException() {
		super();
		this.message = "Too many fields";
	}
	public String getMessage() {
		return message;
	}
	
}
