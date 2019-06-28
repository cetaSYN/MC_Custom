package com.mc_custom.core.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CoreGameModeChangeEvent extends BaseEvent {
	private final GameMode newGameMode;
	private Player player;

	public CoreGameModeChangeEvent(Player player, GameMode newGameMode) {
		this.newGameMode = newGameMode;
		this.player = player;
	}

	/**
	 * Returns the player involved in this event
	 *
	 * @return Player who is involved in this event
	 */
	public final Player getPlayer() {
		return player;
	}

	public GameMode getNewGameMode() {
		return this.newGameMode;
	}
}
