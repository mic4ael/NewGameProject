package helperclasses;

public class WrongPortException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WrongPortException() {
		super();
	}
	
	public WrongPortException(String message) {
		super(message);
	}
}
