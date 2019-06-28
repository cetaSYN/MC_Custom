package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Workbench implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public Workbench() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void workbenchAccess(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if(event.getAction() == Action.RIGHT_CLICK_BLOCK
				|| event.getAction() == Action.RIGHT_CLICK_AIR) {
			try {
				if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.WORKBENCH)) {
					if(player.getItemInHand().getType() == Material.SADDLE) {
						player.openWorkbench(player.getLocation(), true);
					}
				}
			}
			catch(NotOnlineException ex) {
				ex.printStackTrace();
			}
		}
	}
}