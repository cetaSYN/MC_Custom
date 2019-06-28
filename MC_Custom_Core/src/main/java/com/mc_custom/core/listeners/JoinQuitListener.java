package com.mc_custom.core.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.configuration.Configuration;
import com.mc_custom.core.events.CorePreLoginEvent;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements BaseListener {

	private final PlayerHandler<CorePlayer> player_handler;
	// Globally keep track of the regular players
	int reg_players = 0;

	public JoinQuitListener() {
		player_handler = MC_Custom_Core.getPlayerHandler();
	}

	@EventHandler
	public void getInfo(final AsyncPlayerPreLoginEvent event) {
		//Build CorePlayer
		CorePlayer core_player = new CorePlayer(event.getName(), event.getUniqueId(), event.getAddress());
		player_handler.playerJoin(core_player);
		//Allow other plugins async access to newly-made CorePlayer.
		CorePreLoginEvent pre_login = new CorePreLoginEvent(core_player);
		MC_Custom_Core.callEvent(pre_login);

		//Join disallowed //TODO CACHE
		if (!pre_login.getJoinAllowed()) {
			event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
			event.setKickMessage("You have been banned! Appeal on the url_removed forums!\nhttp://url_removed/forum\n\n" +
					"You may see why you were banned here:\nhttp://url_removed/punishments/player/" + event.getName());
		}
	}

	@EventHandler
	public void login(final PlayerLoginEvent event) {
		CorePlayer core_player = null;
		try {
			core_player = player_handler.getPlayer(event.getPlayer());
		}
		catch (NotOnlineException e) {
			//don't do anything if we can't get the player
			return;
		}

		//Set the internal player for use in other parts of the plugin.
		core_player.setPlayer(event.getPlayer());

		//Set Permissions
		core_player.getPermissions().attachPermissions();

		//Set AFK Timer
		core_player.createAFKTimer();

		if (!core_player.ignoresPlayerCap()) {
			reg_players++;
		}

		//Make bypass players not count toward cap.
		if (MC_Custom_Core.getMaxPlayers() > reg_players) {
			event.allow();
		}

		//Allow players with perms to override full server.
		if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
			if (!core_player.ignoresPlayerCap()) {
				event.disallow(PlayerLoginEvent.Result.KICK_FULL, "Server is full.");
			}
			else {
				event.allow();
			}
		}
	}

	@EventHandler
	public void applyData(final PlayerJoinEvent event) {
		CorePlayer core_player = null;
		try {
			core_player = player_handler.getPlayer(event.getPlayer());
		}
		catch (NotOnlineException e) {
			//don't do anything if we can't get the player
			return;
		}

		//Set Name Data
		String name = core_player.getName();
		core_player.setDisplayName(name);
		if (name.length() >= 16) { //Only applicable for nicks (player name will be under 16 chars)
			name = name.substring(0, 15);
		}
		core_player.setPlayerListName(name);

		//Set First-Join Message
		if (!core_player.hasPlayedBefore()) {
			MC_Custom_Core.broadcastMessage(ChatColor.GOLD + "Welcome newplayer " + core_player.getPlayerName() + " to the server!");
			core_player.teleport(Configuration.getInstance().getSpawnHandler().getFirstSpawn().getLocation());
		}

		//Set Join Message
		if (!core_player.isVanished()) {
			event.setJoinMessage(ChatColor.AQUA + core_player.getName() + ChatColor.AQUA + " has joined the server. Ranked: #" + core_player.getRank());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		CorePlayer core_player = null;
		try {
			core_player = player_handler.getPlayer(event.getPlayer());
		}
		catch (NotOnlineException e) {
			//don't do anything if we can't get the player
			return;
		}

		if (!core_player.ignoresPlayerCap()) {
			reg_players--;
		}

		event.setQuitMessage(ChatColor.AQUA + core_player.getName() + ChatColor.AQUA + " has left the server.");

		core_player.getPlayerStatistics().saveStatsAsync();
		core_player.getAFKTimer().cancelTimer();
		player_handler.playerQuit(event.getPlayer());
	}
}