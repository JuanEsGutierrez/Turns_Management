package customExceptions;

@SuppressWarnings("serial")
public class TurnTypeExistsException extends Exception {
	private String name;

	public TurnTypeExistsException(String message, String name) {
		super(message);
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}
