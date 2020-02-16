package customExceptions;

@SuppressWarnings("serial")
public class UserDoesNotExistException extends Exception {
	public UserDoesNotExistException(String message) {
		super(message);
	}
	
}
