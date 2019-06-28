package com.mc_custom.core.configuration;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.utils.ChatColor;

public class Announcement {
	private final String message;
	private final int repeat_time;
	private final String permission;

	public Announcement(String message, int repeat_time) {
		this(message, repeat_time, null);
	}

	public Announcement(String message, int repeat_time, String permission) {
		this.message = message;
		this.repeat_time = repeat_time;
		this.permission = permission;
	}

	public void announce() {
		MC_Custom_Core.broadcast(ChatColor.DARK_BLUE + message, permission);
	}

	public String getMessage() {
		return message;
	}

	public int getRepeatTime() {
		return repeat_time;
	}

	public String getPermission() {
		return permission;
	}
}
