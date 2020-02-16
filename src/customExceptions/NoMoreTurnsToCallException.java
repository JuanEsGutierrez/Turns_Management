package customExceptions;

@SuppressWarnings("serial")
public class NoMoreTurnsToCallException extends Exception {
	public NoMoreTurnsToCallException(String message) {
		super(message);
	}
	
}
