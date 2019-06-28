package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class NotImplementedException extends Exception {

	@Override
	public String getMessage() {
		return ChatColor.RED + "This command has not yet been implemented!";
	}
}
