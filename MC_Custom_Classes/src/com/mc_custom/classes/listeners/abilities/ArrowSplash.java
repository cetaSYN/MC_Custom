package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class ArrowSplash implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public ArrowSplash() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	/**
	 * Handles splash damage when an arrow impacts a block.
	 */
	@EventHandler
	public void arrowSplashBlock(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();

			if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
				Player player = (Player) arrow.getShooter();

				try {
					MCCustomPlayer mc_custom_player = player_handler.getPlayer(player);
					if (mc_custom_player != null) {
						if (player_handler.getPlayer(player) != null
								&& player_handler.getPlayer(player)
								.hasAbilityInRegion(AbilityType.ARROW_SPLASH)) {
							List<Entity> splash_entities = arrow.getNearbyEntities(3, 3, 3);
							if (!mc_custom_player.isVanished()) {

								player.getWorld().playEffect(arrow.getLocation(), Effect.WITCH_MAGIC, 10);
								player.getWorld().playEffect(arrow.getLocation(), Effect.PORTAL, 10);
							}
							for (Entity entity : splash_entities) {
								if (entity != player) {
									if (entity instanceof LivingEntity) {
										LivingEntity living_entity = (LivingEntity) entity;

										living_entity.damage(1.0, player);
									}
								}
							}
						}
					}
				}
				catch (NotOnlineException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Handles splash damage and damage multiplication when an arrow impacts an entity.
	 */
	@EventHandler
	public void arrowSplashEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
				Player player = (Player) arrow.getShooter();
				try {
					MCCustomPlayer mc_custom_player = player_handler.getPlayer(player);
					if (mc_custom_player != null) {
						if (player_handler.getPlayer(player).getMCCustomClass() != null
								&& player_handler.getPlayer(player)
								.hasAbilityInRegion(AbilityType.ARROW_SPLASH)) {
							event.setDamage(event.getDamage() + 10);
							if (!mc_custom_player.isVanished()) {
								player.getWorld().playEffect(arrow.getLocation(), Effect.WITCH_MAGIC, 10);
								player.getWorld().playSound(player.getLocation(), Sound.FALL_BIG, 1, 1);
							}

							List<Entity> splash_entities = arrow.getNearbyEntities(3, 3, 3);

							for (Entity entity : splash_entities) {
								if (entity != player) {
									if (entity instanceof LivingEntity) {
										LivingEntity living_entity = (LivingEntity) entity;

										living_entity.damage(1.0, player);
									}
								}
							}
						}
					}
				}
				catch (NotOnlineException e) {
					e.printStackTrace();
				}
			}
		}
	}
}