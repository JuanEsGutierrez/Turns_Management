package customExceptions;

@SuppressWarnings("serial")
public class NotEnoughFieldsException extends Exception {
	public NotEnoughFieldsException(String message) {
		super(message);
	}
	
}
