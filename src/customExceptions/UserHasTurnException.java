package customExceptions;

@SuppressWarnings("serial")
public class UserHasTurnException extends Exception {
	public UserHasTurnException(String message) {
		super(message);
	}
	
}
