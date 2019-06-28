package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class InvalidCommandException extends Exception {

	@Override
	public String getMessage() {
		return ChatColor.RED + "Invalid command! Try /help.";
	}
}
