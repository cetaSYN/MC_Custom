package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SnowballDamage implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public SnowballDamage() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	/**
	 * Handles damage multiplication when a snowball impacts an entity.
	 */
	@EventHandler
	public void snowball(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Snowball && !(event.getEntity() instanceof Player)) {
			Snowball snowball = (Snowball) event.getDamager();

			if (snowball.getShooter() == null || snowball.getShooter() instanceof Player) {
				try {
					Player player = (Player) snowball.getShooter();
					if (player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.SNOWBALL_DAMAGE)) {
						event.setDamage(1.0);
					}
				}
				catch (NotOnlineException e) {
					e.printStackTrace();
				}
			}
		}
	}
}