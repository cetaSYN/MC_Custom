package com.mc_custom.core.utils;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.players.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PseudoStorm implements Runnable {
	private static boolean instantiated = false;
	private final PlayerHandler<CorePlayer> player_handler = MC_Custom_Core.getPlayerHandler();
	private final Random random = new Random(System.currentTimeMillis());

	public PseudoStorm() {
		assert !instantiated;
		instantiated = true;
	}

	@Override
	public void run() {
		if (random.nextInt(15) == 0) {
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (player.getWorld().isThundering()) {
					continue;
				}
				try {
					CorePlayer mc_custom_player = player_handler.getPlayer(player);
					if (mc_custom_player.getPlayerWeather().equals(PlayerWeatherType.STORM)) {
						mc_custom_player.playSound(Sound.AMBIENCE_THUNDER, 100000.0f, 1.0f);
						Block eye_block = player.getWorld().getBlockAt(player.getEyeLocation());
						if (!mc_custom_player.hasOpenInventory() && (eye_block.getY() < 0
								|| eye_block.getY() >= player.getWorld().getMaxHeight()
								|| eye_block.getLightFromSky() > 0)) {
							flickerLightning(player, 3);
						}
					}
				} catch (NotOnlineException e) {
					// Do nothing
				}
			}
		}
	}

	private void flickerLightning(final Player player, final int max_recursion) {
		if (max_recursion <= 0) {
			return;
		}
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 5, 5, true, false));
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
				MC_Custom_Core.getInstance(),
				new FlickerLightningTask(player, max_recursion - (random.nextInt(Math.max(max_recursion - 1, 1)) + 1)),
				5);
	}

	class FlickerLightningTask implements Runnable {
		private final Player player;
		private final int max_recursion;

		protected FlickerLightningTask(Player player, int max_recursion) {
			this.player = player;
			this.max_recursion = max_recursion;
		}

		@Override
		public void run() {
			flickerLightning(player, max_recursion - random.nextInt(3));
		}
	}

}
