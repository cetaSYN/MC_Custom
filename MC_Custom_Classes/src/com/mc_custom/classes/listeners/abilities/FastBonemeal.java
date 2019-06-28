package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class FastBonemeal implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public FastBonemeal() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@EventHandler
	public void fasterBonemeal(final PlayerInteractEvent event) {
/*		Player player = event.getPlayer();
		Block clicked_block = event.getClickedBlock();
		try {
			if (player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.FAST_BONEMEAL)
					&& event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
					&& event.getItem().isSimilar(new ItemStack(Material.INK_SACK, 1, (short) 15))
					&& Utils.isCrop(clicked_block.getType())) {
				clicked_block.setData(CropState.RIPE.getOrCreate());
				//ParticleEffects.sendToLocation(
				//		ParticleEffects.GREEN_SPARKLE, clicked_block.getLocation(), 0.0f, 0.3f, 0.0f, 1.0f, 8);
			}
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}*/
	}
}