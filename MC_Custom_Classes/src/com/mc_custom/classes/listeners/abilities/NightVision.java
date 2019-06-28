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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVision implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public NightVision() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void nightVision(final PlayerInteractEvent event) {
		Player player = event.getPlayer();

		try {
			if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.NIGHT_VISION)
					&& player.getItemInHand().getType() == Material.SADDLE
					&& event.getAction() == Action.RIGHT_CLICK_AIR
					&& !player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2400, 0,
						false)); //600 ticks = 30 seconds
				player.setFoodLevel(player.getFoodLevel() - 2);
			}
		}
		catch(NotOnlineException e) {
			e.printStackTrace();
		}
	}
}