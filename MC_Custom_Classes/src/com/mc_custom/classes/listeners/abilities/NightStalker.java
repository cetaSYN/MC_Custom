package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class NightStalker implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public NightStalker() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	/**
	 * Faster flight/movement at night, slower flight/movement during the day.
	 *
	 * @param event
	 */
	@EventHandler
	public void nightStalker(final PlayerMoveEvent event) {
		Player player = event.getPlayer();
		try {
			if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.NIGHT_STALKER)
					&& player.getGameMode() == GameMode.SURVIVAL) {
				if(player.getLocation().getBlock().getLightLevel() <= 7) {
					player.setFlySpeed(0.08f);
					player.setWalkSpeed(0.2f);
				}
				else {
					player.setFlySpeed(player_handler.getPlayer(player).getMCCustomClass()
							.getFlySpeed());
					player.setWalkSpeed(player_handler.getPlayer(player).getMCCustomClass()
							.getWalkSpeed());
				}
			}
		}
		catch(NotOnlineException e) {
			e.printStackTrace();
		}
	}

}