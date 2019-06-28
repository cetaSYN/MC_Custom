package com.mc_custom.core.players;

public class Flight {
	private boolean drift = true;
	private boolean cinematic = false;
	private boolean vertical_speed = true;

	public Flight() {
	}

	public Flight(boolean drift, boolean cinematic) {
		this.drift = drift;
		this.cinematic = cinematic;
	}

	public boolean hasDrift() {
		return drift;
	}

	public void setDrift(boolean drift) {
		this.drift = drift;
	}

	public boolean hasCinematic() {
		return cinematic;
	}

	public void setCinematic(boolean cinematic) {
		this.cinematic = cinematic;
	}

	public boolean hasVertSpeedSynced() {
		return vertical_speed;
	}

	public void setVertSpeedSynced(boolean vertical_speed) {
		this.vertical_speed = vertical_speed;
	}
}
