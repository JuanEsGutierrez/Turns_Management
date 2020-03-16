package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TurnType implements Serializable {
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

	@Override
	public String toString() {
		return "name: " + name + ", duration: " + duration;
	}
	
}
