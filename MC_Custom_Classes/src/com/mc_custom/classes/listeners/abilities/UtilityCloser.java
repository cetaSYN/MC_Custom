package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.utils.UtilityBlock;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.ChatColor;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class UtilityCloser implements BaseListener {

	public UtilityCloser() {
	}

	/*
	 * Remove the utility
	 */
	@EventHandler
	public void closeUtility(final InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if (MC_Custom_Classes.getUtilityManager().hasUtility(player.getName())) {
			// Make sure that the utility inventory is empty
			boolean empty = true;
			for (ItemStack item : event.getInventory().getContents()) {
				if (item != null) {
					empty = false;
					break;
				}
			}
			// Remove the utility
			if (empty) {
				MC_Custom_Classes.getUtilityManager().removeUtility(player);
			}
		}
	}

	@EventHandler
	public void forceCloseUtility(final PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (MC_Custom_Classes.getUtilityManager().hasUtility(player.getName())) {
			UtilityBlock utility = MC_Custom_Classes.getUtilityManager().getUtility(player.getName());
			Block block = utility.getBlock();
			InventoryHolder container = (InventoryHolder) block.getState();
			Inventory inv = container.getInventory();
			for (ItemStack item : inv.getContents()) {
				if (item != null) {
					inv.remove(item);
					player.getInventory().addItem(item);
				}
			}
			MC_Custom_Classes.getUtilityManager().removeUtility(player);
		}
	}

	@EventHandler
	public void cancelUtilityBreak(final BlockBreakEvent event) {
		if (event.getBlock().hasMetadata("isUtility")) {
			event.getPlayer().sendMessage(ChatColor.RED + "[ItemCondenser] You cannot break a utility block!");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void cancelUtilityUse(final PlayerInteractEvent event) {
		if (event.getClickedBlock() != null) {
			if (event.getClickedBlock().hasMetadata("isUtility")) {
				event.getPlayer().sendMessage(ChatColor.RED + "[ItemCondenser] You cannot use a utility block!");
				event.setCancelled(true);
			}
		}
	}
}
