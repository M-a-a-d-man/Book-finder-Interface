package errors;

public class SemanticError extends Exception {
	private String message;
	
	public SemanticError(String message) {
		super();
		this.message = message;
	}
	public SemanticError() {
		super();
	}
	
	public String getMessage() {
		return message;
	}
	
	public static void main(String[] args) {
		SemanticError yes = new SemanticError("yooooo");
		System.out.println(yes.getMessage());
	}

}
