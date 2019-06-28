package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LifeSteal implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public LifeSteal() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void lifeSteal(final EntityDamageByEntityEvent event) {
		Entity attacker = event.getDamager();
		if (attacker instanceof Player && event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof ArmorStand)) {
			Player player = (Player) attacker;
			try {
				if (player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.LIFE_STEAL)) {
					double health = player.getHealth() + (event.getDamage() * .25);
					if (health >= 20) {
						player.setHealth(20);
					}
					else {
						player.setHealth(health);
					}
					if (!player.hasMetadata("vanished") || !player.getMetadata("vanished").get(0).asBoolean()) {
						player.getWorld().playEffect(player.getEyeLocation().subtract(0, 0.5, 0), Effect.HEART, 10);
						player.getWorld().playSound(player.getLocation(), Sound.EAT, 1, 1);
						player.getWorld().playSound(player.getLocation(), Sound.DRINK, 1, 1);
					}
				}
			}
			catch (NotOnlineException e) {
				e.printStackTrace();
			}
		}
	}

}