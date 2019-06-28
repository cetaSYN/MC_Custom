package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class IncreaseXP implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public IncreaseXP() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	/**
	 * XP received is multiplied by 1.5x.
	 */
	@EventHandler
	public void onXPChange(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();

		try {
			if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.INCREASE_XP)) {
				event.setAmount((int)Math.ceil((event.getAmount() * 1.5)));
			}
		}
		catch(NotOnlineException e) {
			e.printStackTrace();
		}
	}

}