package com.mc_custom.pds.listeners;

import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.core.utils.WorldGuardHook;
import com.mc_custom.pds.MC_Custom_PDS;
import com.mc_custom.pds.players.PDSPlayer;
import com.mc_custom.punishments.Action;
import com.mc_custom.punishments.ActionType;
import com.mc_custom.punishments.database.ActionQuery;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PDSRegionListener implements BaseListener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		RegionManager region_manager = WorldGuardHook.WORLD_GUARD.getRegionManager(player.getWorld());
		ApplicableRegionSet regions = region_manager.getApplicableRegions(event.getBlock().getLocation());
		for (ProtectedRegion region : regions) {
			if (MC_Custom_PDS.isPDSRegion(region)) {
				if (PermissionUtils.hasWildcardPermission(player, "pds.ignore_attempts")) {
					player.sendMessage(ChatColor.RED + "This region is protected by PDS: " + region.getId());
					event.setCancelled(true);
					return;
				}
				try {
					MC_Custom_PDS.getPlayerHandler().getPlayer(event.getPlayer())
							.incrementViolationLevel(getViolationLevelBlocks(event.getBlock().getType()));
				}
				catch (NotOnlineException e) {
					e.printStackTrace();
				}
				try {
					banIfExceedsViolations(MC_Custom_PDS.getPlayerHandler().getPlayer(player));
				}
				catch (NotOnlineException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		RegionManager region_manager = WorldGuardHook.WORLD_GUARD.getRegionManager(player.getWorld());
		ApplicableRegionSet regions = region_manager.getApplicableRegions(event.getBlock().getLocation());
		for (ProtectedRegion region : regions) {
			if (MC_Custom_PDS.isPDSRegion(region)) {
				if (PermissionUtils.hasWildcardPermission(player, "pds.ignore_attempts")) {
					player.sendMessage(ChatColor.RED + "This region is protected by PDS: " + region.getId());
				}
				event.setCancelled(true);
				return;
			}
		}
	}

	private void banIfExceedsViolations(final PDSPlayer pds_player) {
		if (pds_player.exceedsBanThreshold()) {
			final int player_id = pds_player.getId();
			final int moderator_id = ActionQuery.getPlayerIdByName("PDS");
			Action action = new Action(ActionType.BAN, player_id, moderator_id, System.currentTimeMillis(), "PDS Violation Level: " + pds_player.getViolationLevel());
			action.insert();
			Player player = pds_player.getPlayer();
			if (player != null && player.isOnline()) {
				player.kickPlayer("You have been banished to the moon!");
			}
			Bukkit.getServer().broadcastMessage(ChatColor.RED + pds_player.getPlayerName() + " has been banished to the moon!");
		}
	}

	private int getViolationLevelBlocks(Material material) {
		switch (material) {
			case MELON:
			case MELON_BLOCK:
			case MELON_SEEDS:
			case MELON_STEM:
			case WHEAT:
			case RED_MUSHROOM:
			case BROWN_MUSHROOM:
			case SUGAR_CANE:
			case SUGAR_CANE_BLOCK:
			case LEAVES:
			case LEAVES_2:
			case LONG_GRASS:
			case DEAD_BUSH:
			case YELLOW_FLOWER:
			case RED_ROSE:
			case CACTUS:
			case WATER_LILY:
			case VINE:
			case CARROT:
			case POTATO:
			case REDSTONE_WIRE:
			case NETHER_WARTS:
			case FIRE:
				return 0; //These block types are easily broken or easily mistaken for free-take.
			case STONE:
			case DIRT:
			case GRASS:
			case CLAY:
			case WOOD:
			case LOG:
			case LOG_2:
			case SAND:
			case GRAVEL:
			case SNOW:
			case COBBLESTONE:
			case ENDER_STONE:
				return 5; //Common blocks
			case IRON_ORE:
			case IRON_BLOCK:
			case GOLD_ORE:
			case GOLD_BLOCK:
			case LAPIS_ORE:
			case LAPIS_BLOCK:
			case EMERALD_ORE:
			case EMERALD_BLOCK:
			case DIAMOND_ORE:
			case DIAMOND_BLOCK:
			case OBSIDIAN:
				return 40; //Pretty obvious. Three breaks of these pushes you over the limit.
			case CHEST:
			case TRAPPED_CHEST:
				return 100; //You 'dun fucked up.
			default:
				return 20;
		}
	}
}
