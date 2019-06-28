package com.mc_custom.core.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.configuration.Configuration;
import com.mc_custom.core.configuration.WorldBounds;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.Utils;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class BoundsListener implements BaseListener {

	public BoundsListener() {
	}

	@EventHandler
	public void onMove(final PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayerSilently(event.getPlayer());
		WorldBounds bounds = Configuration.getInstance().getWorldBoundaries(from.getWorld().getName());
		if (player != null && bounds != null) {
			//Bypass Boundary
			if (player.hasPermission("core.bounds.bypass")) {
				return;
			}
			if (to.getBlockX() > bounds.getMaxPoint().getBlockX()) {
				player.sendMessage(ChatColor.RED + "Cannot leave the boundary.");
				player.teleport(new Location(to.getWorld(), bounds.getMaxPoint().getBlockX() - 1, Utils.getSafeY(from), to.getBlockZ(), to.getYaw(), to.getPitch()));
			}
			else if (to.getBlockZ() > bounds.getMaxPoint().getBlockZ()) {
				player.sendMessage(ChatColor.RED + "Cannot leave the boundary.");
				player.teleport(new Location(to.getWorld(), to.getBlockX(), Utils.getSafeY(from), bounds.getMaxPoint().getBlockZ() - 1, to.getYaw(), to.getPitch()));
			}
			else if (to.getBlockX() < bounds.getMinPoint().getBlockX()) {
				player.sendMessage(ChatColor.RED + "Cannot leave the boundary.");
				player.teleport(new Location(to.getWorld(), bounds.getMinPoint().getBlockX() + 1, Utils.getSafeY(from), to.getBlockZ(), to.getYaw(), to.getPitch()));
			}
			else if (to.getBlockZ() < bounds.getMinPoint().getBlockZ()) {
				player.sendMessage(ChatColor.RED + "Cannot leave the boundary.");
				player.teleport(new Location(to.getWorld(), to.getBlockX(), Utils.getSafeY(from), bounds.getMinPoint().getBlockZ() + 1, to.getYaw(), to.getPitch()));
			}
		}
	}
}