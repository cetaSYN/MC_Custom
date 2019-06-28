package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


//TODO Make this work in a way that doesn't look like an 8 year old made it.
public class ChangeTime implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public ChangeTime(){
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	/**
	 * Toggle the weather
	 */
	@EventHandler
	public void changeTime(final PlayerInteractEvent event){
		Player player = event.getPlayer();
		try{
			if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.WEATHER_CHANGE)){
				if(event.getItem() != null && event.getItem().getType().equals(Material.BLAZE_ROD) && (player.getLevel() >= 1
						|| player.getGameMode().equals(GameMode.CREATIVE))){
					if(event.getAction().equals(Action.RIGHT_CLICK_AIR)
							|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
						player.setPlayerTime(player.getPlayerTime() - 500, false);
						if(player.getPlayerTime() - player.getWorld().getTime() > 100
								&& player.getPlayerTime() - player.getWorld().getTime() < 100){
							//TimeChangeUpdater.removePlayer(player);
						}
						else{
							//TimeChangeUpdater.addPlayer(player);
						}
						player.setPlayerTime(player.getPlayerTime() - 500, false);
						player.setLevel(player.getLevel() - 1);
					}
					else if(event.getAction().equals(Action.LEFT_CLICK_AIR)
							|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
						if(player.getPlayerTime() - player.getWorld().getTime() > 100
								&& player.getPlayerTime() - player.getWorld().getTime() < 100){
							//TimeChangeUpdater.removePlayer(player);
						}
						else{
							//TimeChangeUpdater.addPlayer(player);
						}
						player.setPlayerTime(player.getPlayerTime() + 500, false);
						player.setLevel(player.getLevel() - 1);
					}
					event.setCancelled(true);
				}
			}
		}
		catch(NotOnlineException e){
			e.printStackTrace();
		}
	}

}
