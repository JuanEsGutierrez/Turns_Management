package customExceptions;

@SuppressWarnings("serial")
public class TurnTypeDoesNotExistException extends Exception {
	public TurnTypeDoesNotExistException(String message) {
		super(message);
	}
	
}
