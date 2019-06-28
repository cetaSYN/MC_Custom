package com.mc_custom.punishments.players;

import com.mc_custom.core.players.BasePlayer;
import com.mc_custom.core.players.Nickname;
import com.mc_custom.punishments.Action;
import com.mc_custom.punishments.ActionType;

import java.util.List;
import java.util.UUID;

public class ActionPlayer extends BasePlayer {

	private List<Action> actions;
	private boolean is_muted = false;
	private boolean is_frozen = false;

	public ActionPlayer(String player_name, Nickname nickname, UUID uuid, int player_id, List<Action> actions) {
		super(player_name, nickname, uuid, player_id);
		this.actions = actions;
	}

	public List<Action> getPlayerActions() {
		return actions;
	}

	public boolean isBanned() {
		Action most_recent = null;
		for (Action action : actions) {
			if (action.getActionType() == ActionType.BAN || action.getActionType() == ActionType.UNBAN) {
				if (most_recent == null) {
					most_recent = action;
				}
				if (action.getActionTime() > most_recent.getActionTime()) {
					most_recent = action;
				}
			}
		}
		return most_recent != null && most_recent.getActionType() == ActionType.BAN;
	}

	public boolean isMuted() {
		if (is_muted) {
			return true;
		}

		Action most_recent = null;
		for (Action action : actions) {
			if (action.getActionType() == ActionType.MUTE || action.getActionType() == ActionType.UNMUTE) {
				if (most_recent == null) {
					most_recent = action;
				}
				if (action.getActionTime() > most_recent.getActionTime()) {
					most_recent = action;
				}
			}
		}
		if (most_recent == null) {
			return false;
		}
		if (most_recent.getActionType() == ActionType.MUTE) {
			is_muted = true;
			return true;
		}
		return false;
	}

	public boolean isFrozen() {
		if (is_frozen) {
			return true;
		}

		Action most_recent = null;
		for (Action action : actions) {
			if (action.getActionType() == ActionType.FREEZE || action.getActionType() == ActionType.THAW) {
				if (most_recent == null) {
					most_recent = action;
				}
				if (action.getActionTime() > most_recent.getActionTime()) {
					most_recent = action;
				}
			}
		}
		if (most_recent == null) {
			return false;
		}
		if (most_recent.getActionType() == ActionType.FREEZE) {
			is_muted = true;
			is_frozen = true;
			return true;
		}
		return false;
	}

	public void setMuted(boolean mute) {
		this.is_muted = mute;
	}

	public void setFrozen(boolean freeze) {
		this.is_frozen = freeze;
	}
}