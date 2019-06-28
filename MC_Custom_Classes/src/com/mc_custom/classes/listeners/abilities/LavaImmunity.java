package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class LavaImmunity implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public LavaImmunity() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void lavaImmunity(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {

			Player player = (Player)event.getEntity();

			if(event.getCause() == DamageCause.LAVA || event.getCause() == DamageCause.FIRE_TICK
					|| event.getCause() == DamageCause.FIRE) {
				try {
					if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.LAVA_IMMUNITY)) {
						event.setDamage(0.0);
						event.setCancelled(true);
					}
				}
				catch(NotOnlineException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}