package model;

public class TurnType {
	private String name;
	private float duration;
	
	public TurnType(String name, float duration) {
		this.name = name;
		this.duration = duration;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the duration
	 */
	public float getDuration() {
		return duration;
	}
	
}
