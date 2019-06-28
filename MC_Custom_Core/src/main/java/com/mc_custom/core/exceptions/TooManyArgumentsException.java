package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class TooManyArgumentsException extends DetailedException {

	public TooManyArgumentsException() {
		super(null);
	}

	public TooManyArgumentsException(String message) {
		super(message);
	}

	@Override
	public String getMessage(String command_name) {
		return ChatColor.RED + "Too many parameters! Try /" + command_name + " help.";
	}
}
