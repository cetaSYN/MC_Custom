package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class NotOnlineException extends Exception {

	@Override
	public String getMessage() {
		return ChatColor.RED + "The specified player is not online!";
	}
}
