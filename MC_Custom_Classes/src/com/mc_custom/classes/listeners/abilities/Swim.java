package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.Utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Swim implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public Swim() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void swim(final PlayerMoveEvent event) {
		Player player = event.getPlayer();
		try {
			if (player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.SWIM)) {
				if ((player.getLocation().getBlock().getType() == Material.WATER || player.getLocation().getBlock().getType() == Material.STATIONARY_WATER)
						&& player.getFoodLevel() >= 10 && !Utils.hasNegativePotionEffect(player)) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 0, false)); //600 ticks = 30 seconds

					if (player.getGameMode() == GameMode.SURVIVAL) {
						player.setAllowFlight(true);
						player.setFlying(true);
						if (player.isFlying()) {
							player.setExhaustion(player.getExhaustion() + 0.07f);
						}
					}
					player.setFallDistance(0); // fix for dying when you leave water?
				}
				else if ((player.getFoodLevel() < 10 || Utils.hasNegativePotionEffect(player)
						|| (!player.getLocation().getBlock().isLiquid()))
						&& (player.getGameMode() != GameMode.CREATIVE)) {
					player.setFlying(false);
					player.setAllowFlight(false);
				}
			}
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}
}