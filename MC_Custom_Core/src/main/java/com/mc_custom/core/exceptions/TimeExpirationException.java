package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.Utils;

import java.util.Date;

public class TimeExpirationException extends Exception {

	private Date reference_time;
	private Date challenge_time;

	public TimeExpirationException(Date reference_time, Date challenge_time) {
		this.reference_time = reference_time;
		this.challenge_time = challenge_time;
	}

	public String getTimeRemaining() {
		return Utils.millisToDHMS(challenge_time.getTime() - reference_time.getTime());
	}

	@Override
	public String getMessage() {
		return ChatColor.RED + "You have to wait " + getTimeRemaining() + " before changing your nickname.";
	}

}
