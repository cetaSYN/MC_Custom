package com.mc_custom.classes;

import com.mc_custom.classes.commands.ClassCommands;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.classes.recipes.*;
import com.mc_custom.classes.utils.UtilityManager;
import com.mc_custom.core.MC_CustomPlugin;
import com.mc_custom.core.handlers.CommandHandler;
import com.mc_custom.core.handlers.PlayerHandler;

/**
 * Main class for MC_Custom_Classes
 */
public class MC_Custom_Classes extends MC_CustomPlugin {

	private static MC_Custom_Classes plugin;
	private static UtilityManager utility_manager = new UtilityManager();
	private static PlayerHandler<MCCustomPlayer> player_handler = new PlayerHandler<>();

	@Override
	public void onEnable() {
		checkCore();
		plugin = this;
		// Initialize Resources
		Custom5Recipes.addRecipes();
		Custom8Recipes.addRecipes();
		SharedRecipes.addRecipes();
		Custom7Recipes.addRecipes();
		Custom1Recipes.addRecipes();
		Custom9Recipes.addRecipes();
		Custom2Recipes.addRecipes();
		Custom10Recipes.addRecipes();
		// Register Listeners
		registerEvents(this, "com.mc_custom.classes.listeners");
		// Add Commands
		CommandHandler.getInstance().addCommand(new ClassCommands());
	}

	@Override
	public void onDisable() {
		utility_manager.forceCloseUtilities();
	}

	/**
	 * Gets the PlayerHandler for MCCustomPlayer.
	 *
	 * @return player_handler
	 */
	public static PlayerHandler<MCCustomPlayer> getPlayerHandler() {
		return player_handler;
	}

	public static MC_Custom_Classes getInstance() {
		return plugin;
	}

	/**
	 * Gets the UtilityManager
	 *
	 * @return UtilityManager
	 */
	public static UtilityManager getUtilityManager() {
		return utility_manager;
	}

	public static void runTaskAsynchronously(Runnable runnable) {
		getScheduler().runTaskAsynchronously(plugin, runnable);
	}
}