package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.classes.recipes.Custom7Recipes;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.PlayerWeatherType;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ChangeWeather implements BaseListener {
	private PlayerHandler<MCCustomPlayer> player_handler;

	public ChangeWeather() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	/**
	 * Toggle the weather
	 */
	@EventHandler
	public void changeWeather(final PlayerInteractEvent event) throws NotOnlineException {
		MCCustomPlayer player = player_handler.getPlayer(event.getPlayer());
		ItemStack item = player.getItemInHand();
		if ((event.getClickedBlock() != null && !event.getClickedBlock().getType().equals(Material.AIR)) ||
		    item == null || (!item.getType().equals(Material.GLASS_BOTTLE) && !item.getType().equals(Material.POTION))) {
			return;
		}
		// Collecting
		if (item.getType().equals(Material.GLASS_BOTTLE)
		    && (player.getPlayerWeather().equals(PlayerWeatherType.RAIN)
		        || player.getPlayerWeather().equals(PlayerWeatherType.STORM)
		        || (player.getPlayerWeather().equals(PlayerWeatherType.SERVER)
		            && player.getWorld().hasStorm()))) {
			if (player.hasAbilityInRegion(AbilityType.WEATHER_CHANGE)) {
				// Charged Rainwater
				if ((player.getWorld().isThundering()
				     && player.getPlayerWeather().equals(PlayerWeatherType.SERVER))
				    || player.getPlayerWeather().equals(PlayerWeatherType.STORM)) {
					player.setItemInHand(Custom7Recipes.CHARGED_RAINWATER_ITEM.clone());
				}
				// Regular Rainwater
				else {
					player.setItemInHand(Custom7Recipes.RAINWATER_ITEM.clone());
				}
				player.setPlayerWeather(PlayerWeatherType.CLEAR);
			}
		}
		// Emptying
		else if (item.getType().equals(Material.POTION)) {
			PlayerWeatherType new_weather = null;
			switch (item.getDurability()) {
				case Custom7Recipes.RAINWATER_DAMAGE:
					new_weather = PlayerWeatherType.RAIN;
					break;
				case Custom7Recipes.CHARGED_RAINWATER_DAMAGE:
					new_weather = PlayerWeatherType.STORM;
					break;
				case Custom7Recipes.CLEAR_SKIES_DAMAGE:
					new_weather = PlayerWeatherType.CLEAR;
					break;
				case Custom7Recipes.ATMOSPHERIC_BALANCE_DAMAGE:
					new_weather = PlayerWeatherType.SERVER;
					break;
			}
			if (new_weather == null) {
				return;
			}
			player.setPlayerWeather(new_weather);
			if (!player.getGameMode().equals(GameMode.CREATIVE)) {
				player.setItemInHand(new ItemStack(Material.GLASS_BOTTLE));
			}
		}
		player.updateInventory();
		event.setCancelled(true);
	}
}