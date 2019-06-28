package com.mc_custom.core.exceptions;

import com.mc_custom.core.utils.ChatColor;

public class NicknameInUseException extends Exception {

	@Override
	public String getMessage() {
		return ChatColor.RED + "Oops! Someone else already has that nickname!";
	}

}