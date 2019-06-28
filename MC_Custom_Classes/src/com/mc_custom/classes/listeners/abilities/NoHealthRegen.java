package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class NoHealthRegen implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public NoHealthRegen() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void noHealthRegen(final EntityRegainHealthEvent event) {
		Entity player_entity = event.getEntity();
		if(player_entity instanceof Player) {
			Player player = (Player)player_entity;
			try {
				if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.NO_HEALTH_REGEN)) {
					if(event.getRegainReason() == RegainReason.SATIATED || event.getRegainReason() == RegainReason.REGEN) {
						event.setCancelled(true);
					}
				}

			}
			catch(NotOnlineException e) {
				e.printStackTrace();
			}
		}
	}
}