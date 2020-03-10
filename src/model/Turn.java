package model;

public class Turn {
	private char turnLetter;
	private int turnNumber;
	private boolean attended;
	private boolean left;
	
	private User user;
	private TurnType turnType;

	public Turn(char turnLetter, int turnNumber, User user, TurnType turnType) {
		this.turnLetter = turnLetter;
		this.turnNumber = turnNumber;
		attended = false;
		left = false;
		this.user = user;
		this.turnType = turnType;
	}

	/**
	 * @return the turnLetter
	 */
	public char getTurnLetter() {
		return turnLetter;
	}

	/**
	 * @param turnLetter the turnLetter to set
	 */
	public void setTurnLetter(char turnLetter) {
		this.turnLetter = turnLetter;
	}

	/**
	 * @return the turnNumber
	 */
	public int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * @param turnNumber the turnNumber to set
	 */
	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the attended
	 */
	public boolean isAttended() {
		return attended;
	}

	/**
	 * @param attended the attended to set
	 */
	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	/**
	 * @return the left
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}
	
}
