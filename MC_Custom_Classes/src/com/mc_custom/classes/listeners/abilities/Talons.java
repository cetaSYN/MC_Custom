package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Talons implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public Talons() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void talons(final EntityDamageByEntityEvent event) {
		Entity attacker = event.getDamager();
		if(attacker instanceof Player) {
			Player player = (Player)attacker;
			try {
				if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.TALONS)
						&& player.getItemInHand().getType().equals(Material.AIR)) {
					event.setDamage(4.0); //damage dealt with a wooden axe
				}

			}
			catch(NotOnlineException e) {
				e.printStackTrace();
			}
		}
	}
}