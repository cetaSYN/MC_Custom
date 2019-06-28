package com.mc_custom.classes.listeners;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.events.CorePreLoginEvent;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.players.CorePlayer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class JoinQuitListener implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public JoinQuitListener() {
		this.player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onCorePreLogin(final CorePreLoginEvent event) {
		CorePlayer core_player = event.getCorePlayer();
		player_handler.playerJoin(new MCCustomPlayer(core_player.getPlayerName(), core_player.getNickname(), core_player.getUUID(), core_player.getId()));
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		Player player = event.getPlayer();
		// Reset this achievement for detecting of open inventory
		// player.removeAchievement(Achievement.OPEN_INVENTORY);

		MCCustomPlayer mc_custom_player = null;
		try {
			mc_custom_player = player_handler.getPlayer(player);
		} catch (NotOnlineException e) {
			//don't do anything if we can't get the player
			return;
		}
		//Set the internal player for use in other parts of the plugin.
		mc_custom_player.setPlayer(player);

		//If First Join
		if (!player.hasPlayedBefore()) {
			mc_custom_player.setLastChange(new Date(System.currentTimeMillis() - 864000000)); //10 days in milliseconds
			mc_custom_player.resetChangeTime();
		}

		if (mc_custom_player.getMCCustomClass() != null) {
			mc_custom_player.applyAttributes();
			Block check_block = player.getLocation().getBlock().getRelative(BlockFace.DOWN, 1);
			if (mc_custom_player.hasAbilityInRegion(AbilityType.FLIGHT) && check_block.getType() == Material.AIR) {
				player.setFlying(true);
			}
		}
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		player_handler.playerQuit(event.getPlayer());
	}

}