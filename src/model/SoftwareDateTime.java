package model;

import java.io.Serializable;
import java.time.*;
@SuppressWarnings("serial")
public class SoftwareDateTime implements Serializable {
	private LocalDate date;
	private LocalTime time;
	private long difference;
	
	public SoftwareDateTime() {
		date = LocalDate.now();
		time = LocalTime.now();
		difference = 0;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return the time
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(LocalTime time) {
		this.time = time;
	}

	/**
	 * @return the difference
	 */
	public long getDifference() {
		return difference;
	}

	/**
	 * @param difference the difference to set
	 */
	public void setDifference(long difference) {
		this.difference = difference;
	}

}
