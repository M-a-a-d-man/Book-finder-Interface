package errors;

public class BadPriceException extends Exception {
	public BadPriceException() {
		super();
	}
	public BadPriceException(String x) {
		super(x);
	}
}
