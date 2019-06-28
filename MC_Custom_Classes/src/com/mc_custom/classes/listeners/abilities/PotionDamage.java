package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionDamage implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public PotionDamage() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	public void increasePotionEffect(final PotionSplashEvent event) {
		Player player = (Player)event.getEntity().getShooter();
		try {
			if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.POTION_BOOST)) {
				for(LivingEntity target : event.getAffectedEntities()) {
					event.setIntensity(target, event.getIntensity(target) * 1.5);
				}
			}
		}
		catch(NotOnlineException e) {
			e.printStackTrace();
		}
	}

	public void increasePotionEffect(final PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		try {
			if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.POTION_BOOST)) {
				if(event.getItem().getType().equals(Material.POTION)) {
					Potion potion = Potion.fromItemStack(event.getItem());
					for(PotionEffect effect : potion.getEffects()) {
						if(effect.getType().equals(PotionEffectType.HEAL)
								|| effect.getType().equals(PotionEffectType.HARM)) {
							potion.setLevel(potion.getLevel() * 2);
						}
					}
					event.setItem(potion.toItemStack(1));
				}
			}
		}
		catch(NotOnlineException e) {
			e.printStackTrace();
		}
	}
}
