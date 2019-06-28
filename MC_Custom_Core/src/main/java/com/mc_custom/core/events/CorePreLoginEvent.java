package com.mc_custom.core.events;

import com.mc_custom.core.players.CorePlayer;

public class CorePreLoginEvent extends BaseEvent {
	private CorePlayer core_player = null;
	private boolean join_allowed = true;
	private String message = "";

	public CorePreLoginEvent(CorePlayer core_player) {
		this.core_player = core_player;
	}

	public CorePlayer getCorePlayer() {
		return core_player;
	}

	public boolean getJoinAllowed() {
		return join_allowed;
	}

	public void setJoinAllowed(boolean join_allowed) {
		this.join_allowed = join_allowed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
