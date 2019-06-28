package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.classes.MCCustomClass;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.Utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class Flight implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public Flight() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void flight(final PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Block check_block = player.getLocation().getBlock();
		//Location player_location = player.getLocation();
		try {
			if (player.isFlying()
					&& player.getGameMode() == GameMode.SURVIVAL) { //Preliminary flight-worthy checks.
				MCCustomPlayer mc_custom_player = player_handler.getPlayer(player);
				MCCustomClass player_class = mc_custom_player.getMCCustomClass(); //Pull class

				if (!mc_custom_player.hasAbilityInRegion(AbilityType.FLIGHT)) {
					player.setFlying(false);
				}

				player.setExhaustion(player.getExhaustion() + 0.05f);

					/* Disable flight on low food, negative potions, liquid, or web */
				if ((player.getFoodLevel() < 10 || Utils.hasNegativePotionEffect(player)
						|| check_block.isLiquid() || check_block.getType() == Material.WEB
						|| check_block.getRelative(BlockFace.UP, 1).getType() == Material.WEB)) {
					player.setFlying(false);
				}

					/* Weather Slowing */
				if (event.getPlayer().getWorld().hasStorm()) {
					player.setFlySpeed(player_class.getWeatherFlySpeed());
				}
				else {
					player.setFlySpeed(player_class.getFlySpeed());
				}

				/* Particle Effects */ //TODO: Re-enable when suitable particle replacement is found.
				/*Location check_loc = player_location;
				for (int i = 0; i < 5; i++) {
					check_loc.subtract(0, 1, 0);
					if (!check_loc.getBlock().isEmpty()) {
						check_loc.add(0, 1, 0);
						if (!player.hasMetadata("vanished") || !player.getMetadata("vanished").get(0).asBoolean()) {
							player_location.getWorld().playEffect(check_loc, Effect.SMOKE, 0);
						}
						break;
					}
				}*/
			}
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void blockFlightInRegions(final PlayerToggleFlightEvent event) throws NotOnlineException {
		Player player = event.getPlayer();
		if (event.isCancelled() || player.isFlying()) {
			return;
		}
		MCCustomPlayer mc_custom_player = player_handler.getPlayer(player);
		if (!(player.getGameMode().equals(GameMode.SPECTATOR)
				|| player.getGameMode().equals(GameMode.CREATIVE)
				|| mc_custom_player.hasAbilityInRegion(AbilityType.FLIGHT))) {
			event.setCancelled(true);
		}
	}
}