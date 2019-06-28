package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class PlayerOnlyException extends Exception{

	@Override
	public String getMessage() {
		return ChatColor.RED + "This command can only be used by a player!";
	}
}
