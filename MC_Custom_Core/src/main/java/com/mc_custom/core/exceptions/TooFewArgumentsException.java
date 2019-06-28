package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class TooFewArgumentsException extends DetailedException {

	@Override
	public String getMessage(String command_name) {
		return ChatColor.RED + "Too few parameters! Try /" + command_name + " help.";
	}
}
