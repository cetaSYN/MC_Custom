package com.mc_custom.classes.listeners;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.events.ClassChangeEvent;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.events.CoreGameModeChangeEvent;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.Date;

public class PlayerStateChangeListener implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public PlayerStateChangeListener() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void onClassChange(final ClassChangeEvent event) {
		final MCCustomPlayer mc_custom_player = event.getPlayer();
		mc_custom_player.setMCCustomClass(event.getNewClass());
		mc_custom_player.applyAttributes();
		mc_custom_player.setLastChange(new Date());
		event.getPlayer().setFallDistance(0);
		mc_custom_player.save();
	}

	@EventHandler
	public void onWorldChange(final PlayerChangedWorldEvent event) {
		try {
			player_handler.getPlayer(event.getPlayer()).applyAttributes();
		} catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onTeleport(final PlayerChangedWorldEvent event) {
		try {
			player_handler.getPlayer(event.getPlayer()).applyAttributes();
		} catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onTeleport(final PlayerPortalEvent event) {
		try {
			player_handler.getPlayer(event.getPlayer()).applyAttributes();
		} catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

/*	@EventHandler
	public void onInventoryChange(final PlayerAchievementAwardedEvent event) {
		// Unfortunately, the only way to detect if a player's inventory is open is by checking for this achievement
		// This means that the achievement can never be awarded, and thus the achievement tree is never unlocked
		// Nobody really cares about achievements, right?
		if (event.getAchievement().equals(Achievement.OPEN_INVENTORY)) {
			MCCustomPlayer player = player_handler.getPlayerSilently(event.getPlayer());
			player.setHasOpenInventory(true);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryChange(final InventoryOpenEvent event) {
		MCCustomPlayer player = player_handler.getPlayerSilently((Player) event.getPlayer());
		player.setHasOpenInventory(true);
	}

	@EventHandler
	public void onInventoryChange(final InventoryCloseEvent event) {
		MCCustomPlayer player = player_handler.getPlayerSilently((Player) event.getPlayer());
		player.setHasOpenInventory(false);
	}*/

	@EventHandler
	public void onGameModeChange(final CoreGameModeChangeEvent event) {
		try {
			player_handler.getPlayer(event.getPlayer()).applyAttributes();
		} catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}
}
