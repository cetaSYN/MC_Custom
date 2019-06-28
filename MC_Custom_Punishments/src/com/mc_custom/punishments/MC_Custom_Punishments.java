package com.mc_custom.punishments;

import com.mc_custom.core.MC_CustomPlugin;
import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.handlers.CommandHandler;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.punishments.players.ActionPlayer;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MC_Custom_Punishments extends MC_CustomPlugin {

	private static PlayerHandler<ActionPlayer> player_handler = new PlayerHandler<>();
	private static MC_Custom_Punishments plugin;
	private static ActionHandler action_handler = new ActionHandler();

	@Override
	public void onEnable() {
		checkCore();
		plugin = this;
		// Register Listeners
		registerEvents(this, "com.mc_custom.punishments.listeners");

		// Register Commands
		CommandHandler.getInstance().addCommands(this, "com.mc_custom.punishments.commands");
	}

	public static ActionHandler getActionHandler() {
		return action_handler;
	}

	public static PlayerHandler<ActionPlayer> getPlayerHandler() {
		return player_handler;
	}

	public static MC_Custom_Punishments getInstance() {
		return plugin;
	}

	public static void runTaskAsynchronously(Runnable runnable) {
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
	}

	// TODO: If we verify that player_handler only contains online players we can use that insteaad
	public static List<ActionPlayer> getOnlinePlayers() {
		List<ActionPlayer> list = new ArrayList<>();
		for (Player player : MC_Custom_Core.getOnlineBukkitPlayers()) {
			ActionPlayer action_player = player_handler.getPlayerSilently(player);
			if (action_player != null) {
				list.add(action_player);
			}
		}
		return list;
	}

}