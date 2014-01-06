package helperclasses;

public class NoValueProvidedException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoValueProvidedException() {
		super("No values provided!");
	}
}
