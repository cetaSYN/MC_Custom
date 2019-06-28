package com.mc_custom.core.timers;

public abstract class CoreTimer implements Runnable {

	private long repeat_delay = 100l;
	private long time_passed = 0l;

	/**
	 * @param repeat_delay Milliseconds between event executions.
	 */
	public CoreTimer(long repeat_delay) {
		this.repeat_delay = repeat_delay;
	}

	@Override
	public abstract void run();

	/**
	 * @return repeat_delay
	 * Returns the value that the timer must pass pass before repeating.
	 */
	public long getRepeatDelay() {
		return repeat_delay;
	}

	/**
	 * @return time_passed
	 * Returns the amount of time that has passed on the current timer.
	 */
	public long getTimePassed() {
		return time_passed;
	}

	/**
	 * Resets time passed to zero.
	 */
	public void resetTime() {
		time_passed = 0l;
	}

	/**
	 * @param time Causes additional time to be added to time passed.
	 */
	public void passTime(long time) {
		time_passed += time;
	}
}
