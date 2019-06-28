package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReduceDamageSelf implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public ReduceDamageSelf() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void reduceDamage(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player)event.getEntity();

			try {
				if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.REDUCE_DAMAGE_SELF)) {
					event.setDamage(Math.ceil((event.getDamage() / 1.5)));
				}
			}
			catch(NotOnlineException e) {
				e.printStackTrace();
			}
		}
	}
}