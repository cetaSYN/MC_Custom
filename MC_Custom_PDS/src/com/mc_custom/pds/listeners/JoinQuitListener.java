package com.mc_custom.pds.listeners;

import com.mc_custom.core.events.CorePreLoginEvent;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.Utils;
import com.mc_custom.pds.MC_Custom_PDS;
import com.mc_custom.pds.players.PDSPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class JoinQuitListener implements BaseListener {

	private final PlayerHandler<PDSPlayer> player_handler;

	public JoinQuitListener() {
		this.player_handler = MC_Custom_PDS.getPlayerHandler();
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onCorePreLogin(final CorePreLoginEvent event) {
		CorePlayer core_player = event.getCorePlayer();
		player_handler.playerJoin(new PDSPlayer(core_player.getPlayerName(), core_player.getNickname(), core_player.getUUID(), core_player.getId()));
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {

		PDSPlayer player = null;
		try {
			player = player_handler.getPlayer(event.getPlayer());
		}
		catch (NotOnlineException e) {
			//don't do anything if we can't get the player
			return;
		}

		//Set the internal player for use in other parts of the plugin.
		player.setPlayer(event.getPlayer());

		Date reset_date = Utils.addDays(-5);
		Date last_offense = player.getLastOffense();
		if (last_offense == null || last_offense.before(reset_date)) {
			player.resetViolationLevel();
		}
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		player_handler.playerQuit(event.getPlayer());
	}
}
