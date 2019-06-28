package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class NoPermissionException extends Exception{

	@Override
	public String getMessage() {
		return ChatColor.RED + "You do not have permission to use this!";
	}
}
