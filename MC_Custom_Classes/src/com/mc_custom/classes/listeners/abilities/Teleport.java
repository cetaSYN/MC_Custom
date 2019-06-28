package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class Teleport implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public Teleport() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	/**
	 * Handles Custom9 class teleportation.
	 * Used by right-clicking saddle.
	 * Attempts to teleport players to a safe location.
	 */
	@EventHandler
	public void teleport(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		try {
			if (player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.TELEPORT)) {
				if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
					if (player.getItemInHand().getType() == Material.SADDLE && player.getFoodLevel() > 10) {
						Location init_loc = player.getLocation();
						try {
							Location tele_loc = player.getTargetBlock(Utils.getTransparentBlocks(), 50).getLocation();
							Vector vector = player.getVelocity();

							tele_loc.setPitch(player.getLocation().getPitch());
							tele_loc.setYaw(player.getLocation().getYaw());

							while (tele_loc.getBlock().getType() == Material.AIR) {
								tele_loc.setY(tele_loc.getY() - 1);
								if (tele_loc.getY() < 0) {
									//Ensures no server lock due to endless loop if there is no floor.
									return;
								}
							}
							double temp_y = init_loc.getY();

							Material bedrock_test = Material.AIR;
							while (tele_loc.add(0, 1, 0).getBlock().getType() != Material.AIR
									&& tele_loc.add(0, 2, 0).getBlock().getType() != Material.AIR) {
								if (tele_loc.add(0, 2, 0).getBlock().getType() == Material.BEDROCK) {
									tele_loc.setY(temp_y);
									bedrock_test = Material.BEDROCK;
									break;
								}
								tele_loc.setY(tele_loc.getY() + 1);
							}

							vector.setY(0);
							Block check_block = tele_loc.getBlock().getRelative(BlockFace.DOWN, 1);

							if (check_block.getType() != Material.LAVA
									&& check_block.getType() != Material.STATIONARY_LAVA
									&& check_block.getType() != Material.BEDROCK
									&& bedrock_test != Material.BEDROCK) {
								//&& Utils.checkBounds(tele_loc)) { TODO
								player.setVelocity(vector);
								if (!player.hasMetadata("vanished") || !player.getMetadata("vanished").get(0).asBoolean()) {
									player.getWorld().playEffect(player.getLocation(), Effect.WITCH_MAGIC, 30);

									player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
									player.teleport(tele_loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
									player.setFallDistance(0);

									player.getWorld().playEffect(player.getLocation(), Effect.WITCH_MAGIC, 30);
									player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
								}

								int distance_travelled = (int) Math.sqrt(
										Math.pow(init_loc.getX() - tele_loc.getX(), 2)
												+ Math.pow(init_loc.getZ() - tele_loc.getZ(), 2)
												+ Math.pow(init_loc.getY() - tele_loc.getY(), 2));

								if (player.getGameMode() == GameMode.SURVIVAL) {
									if (distance_travelled < 20) {
										player.setFoodLevel(player.getFoodLevel() - 2);
									}
									else if (distance_travelled > 50) {
										player.setFoodLevel(player.getFoodLevel() - 10);
									}
									else {
										player.setFoodLevel(player.getFoodLevel() - (distance_travelled / 10));
									}
								}
							}
							else {
								player.sendMessage(ChatColor.GOLD + "Cannot teleport to that location");
							}

						}
						catch (Exception ex) {
							PluginLogger.core().warning(ex.getMessage());
						}
					}
				}
			}
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}
}