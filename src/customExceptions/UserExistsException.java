package customExceptions;

@SuppressWarnings("serial")
public class UserExistsException extends Exception {
	private String id;
	
	public UserExistsException(String message, String id) {
		super(message);
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
}
