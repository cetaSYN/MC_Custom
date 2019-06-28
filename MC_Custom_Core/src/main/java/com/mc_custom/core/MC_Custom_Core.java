package com.mc_custom.core;

import com.mc_custom.core.configuration.Configuration;
import com.mc_custom.core.configuration.DatabaseConfiguration;
import com.mc_custom.core.handlers.CommandHandler;
import com.mc_custom.core.handlers.PermissionsHandler;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.PacketListener;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.timers.TimerHandler;
import com.mc_custom.core.utils.PluginLogger;

import com.comphenix.protocol.ProtocolLibrary;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MC_Custom_Core extends MC_CustomPlugin {

	//Static, because there can only be one
	private static int server_id;
	private static MC_Custom_Core plugin;
	private static SimpleCommandMap known_commands;
	private static HikariDataSource db_connection_pool;
	private static PlayerHandler<CorePlayer> player_handler = new PlayerHandler<>();
	private static TimerHandler timer_handler = new TimerHandler();

	@Override
	public void onEnable() {
		plugin = this;

		// Load DatabaseConfiguration
		db_connection_pool = DatabaseConfiguration.loadConfig(getDataFolder());
		// Must be loaded after the database is configured
		Configuration.getInstance();
		server_id = Configuration.getServerId();

		// Load permission groups
		PermissionsHandler.getInstance();

		// Initialize Commands
		CommandHandler.getInstance().addCommands(this, "com.mc_custom.core.commands");

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener());

		// Register Listeners
		registerEvents(this, "com.mc_custom.core.listeners");

		// Start Timer
		getServer().getScheduler().scheduleSyncRepeatingTask(this, timer_handler, 1l, 100l);

		//Command Hooking
		hookKnownCommands();

		//Start pseudo lightning storm
		//getServer().getScheduler().scheduleSyncRepeatingTask(this, new PseudoStorm(), 0, 40);

		getServer().setIdleTimeout(-1);
	}

	@Override
	public void onDisable() {
		// Remove players from memory and update database.
		for (CorePlayer core_player : MC_Custom_Core.getOnlinePlayers()) {
			core_player.getPlayerStatistics().saveStats();
		}
	}

	public static Connection getDBConnection() {
		try {
			return db_connection_pool.getConnection();
		}

		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getServerId() {
		return server_id;
	}

	public static MC_Custom_Core getInstance() {
		return plugin;
	}

	public static TimerHandler getTimerHandler() {
		return timer_handler;
	}

	public static void runTaskAsynchronously(Runnable runnable) {
		getScheduler().runTaskAsynchronously(plugin, runnable);
	}

	public static void runTaskSynchronously(Runnable runnable) {
		getScheduler().runTask(plugin, runnable);
	}

	public static PlayerHandler<CorePlayer> getPlayerHandler() {
		return player_handler;
	}

	public static SimpleCommandMap getKnownCommands() {
		return known_commands;
	}

	// TODO: If we verify that player_handler only contains online players we can use that instead
	public static List<CorePlayer> getOnlinePlayers() {
		List<CorePlayer> list = new ArrayList<>();
		for (Player player : MC_Custom_Core.getOnlineBukkitPlayers()) {
			CorePlayer core_player = player_handler.getPlayerSilently(player);
			if (core_player != null) {
				list.add(core_player);
			}
		}
		return list;
	}

	private void hookKnownCommands() {
		PluginManager manager = getServer().getPluginManager();
		SimplePluginManager splugin_manager = (SimplePluginManager) manager;
		Field command_map;
		try {
			if (splugin_manager != null) {
				Field plugins = splugin_manager.getClass().getDeclaredField("plugins");
				Field lookup_names = splugin_manager.getClass().getDeclaredField("lookupNames");
				command_map = splugin_manager.getClass().getDeclaredField("commandMap");
				plugins.setAccessible(true);
				lookup_names.setAccessible(true);
				command_map.setAccessible(true);
				known_commands = (SimpleCommandMap) command_map.get(splugin_manager);
			}
		}
		catch (Exception e) {
			PluginLogger.core().warning(e.getMessage());
		}
	}
}
