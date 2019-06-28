package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.core.utils.PrismUtils;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.Utils;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class FireBreath implements BaseListener {

	@EventHandler
	public void fireBreath(final PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (event.getAction() == Action.RIGHT_CLICK_AIR) {
			try {
				if (MC_Custom_Classes.getPlayerHandler().getPlayer(player).hasAbilityInRegion(AbilityType.FIRE_BREATH)) {
					Material held_item = player.getItemInHand().getType();

					if (held_item == Material.SADDLE) {
						if ((player.getFoodLevel() >= 4 || Utils.hasNegativePotionEffect(player) || player
								.getEyeLocation().getBlock().isLiquid())
								&& (player.getGameMode() != GameMode.CREATIVE)) {
							Vector to = player.getPlayer().getEyeLocation().getDirection();
							to.multiply(3);
							player.getWorld().spawn(player.getEyeLocation().add(to.getX(), to.getY(), to.getZ()), Fireball.class);
							if (MC_Custom_Classes.pluginExists("Prism")) {
								PrismUtils.getInstance().callPrismEvent("pmc-custom4-fireball", player, null);
							}
							player.setFoodLevel(player.getFoodLevel() - 4);
							if (!player.hasMetadata("vanished") || !player.getMetadata("vanished").get(0).asBoolean()) {
								player.getWorld().playEffect(player.getEyeLocation(), Effect.LARGE_SMOKE, 10);
								player.getWorld().playSound(player.getEyeLocation(), Sound.GHAST_FIREBALL, 2, 1);
							}
						}
					}
				}
			}
			catch (NotOnlineException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Handles splash damage when a fireball impacts a block.
	 */
	@EventHandler
	public void fireball(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Fireball) {
			Fireball fireball = (Fireball) event.getEntity();
			if (fireball.getShooter() == null || fireball.getShooter() instanceof Player) {

				List<Entity> splash_entities = fireball.getNearbyEntities(4, 4, 4);

				for (Entity entity : splash_entities) {
					if (entity instanceof LivingEntity && !(entity instanceof Player)) {
						LivingEntity living_entity = (LivingEntity) entity;

						living_entity.damage(5.0, fireball);
						living_entity.setVelocity(living_entity.getVelocity().setY(1));
					}
				}
			}
		}
	}

	/**
	 * Handles splash damage and damage multiplication when a fireball impacts an entity.
	 */
	@EventHandler
	public void fireball(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			if (event.getDamager() instanceof Fireball) {
				Fireball fireball = (Fireball) event.getDamager();
				if (fireball.getShooter() == null || fireball.getShooter() instanceof Player) {

					event.setCancelled(true);
					List<Entity> splash_entities = fireball.getNearbyEntities(4, 4, 4);

					for (Entity entity : splash_entities) {
						if (entity instanceof LivingEntity && !(entity instanceof Player)) {
							LivingEntity living_entity = (LivingEntity) entity;

							living_entity.damage(5.0, fireball);
							living_entity.setVelocity(living_entity.getVelocity().setY(1));
						}
					}
				}
			}
		}
	}
}