package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class InvalidArgumentException extends DetailedException {

	private boolean default_msg = true;

	public InvalidArgumentException() {
		super(null);
	}

	public InvalidArgumentException(String message) {
		super(message);
	}

	/**
	 * @param message Message to print to player.
	 * @param default_msg If true, prints default error message before message passed as argument.
	 * If false, does not print default error message, only passed-message.
	 */
	public InvalidArgumentException(String message, boolean default_msg) {
		super(message);
		this.default_msg = default_msg;
	}

	/**
	 * @return Returns true if default error message should be printed.
	 */
	public boolean defaultMessageIncluded() {
		return default_msg;
	}

	@Override
	public String getMessage(String command_name) {
		return ChatColor.RED + "Invalid parameters! Try /" + command_name + " help.";
	}
}
