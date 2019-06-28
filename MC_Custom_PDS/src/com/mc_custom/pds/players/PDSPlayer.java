package com.mc_custom.pds.players;

import com.mc_custom.core.players.BasePlayer;
import com.mc_custom.core.players.Nickname;

import java.util.Date;
import java.util.UUID;

public class PDSPlayer extends BasePlayer {

	private int violation_level = 0;
	private Date last_offense = null;

	public PDSPlayer(String name, Nickname nickname, UUID uuid, int player_id) {
		super(name, nickname, uuid, player_id);
	}

	public void resetViolationLevel() {
		violation_level = 0;
	}

	public int getViolationLevel() {
		return violation_level;
	}

	public void incrementViolationLevel(int increment_level) {
		this.violation_level += increment_level;
		last_offense = new Date();
	}

	public boolean exceedsBanThreshold() {
		return violation_level > 100;
	}

	public Date getLastOffense() {
		return last_offense;
	}

}