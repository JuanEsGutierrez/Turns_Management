package model;

import java.time.*;
public class SoftwareTime {
	private LocalDate date;
	private LocalTime time;
	
	public SoftwareTime() {
		time = LocalTime.now();
		date = LocalDate.now();
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
	
}
