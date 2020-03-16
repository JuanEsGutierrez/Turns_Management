package customExceptions;

@SuppressWarnings("serial")
public class NewDateTimeIsBeforeException extends Exception {
	public NewDateTimeIsBeforeException(String message) {
		super(message);
	}
	
}
