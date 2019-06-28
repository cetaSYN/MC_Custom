package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SortCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"sort"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.sort";
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Sorts all items in the inventory numerically",
				"/sort [chest]"};
	}

	@Override
	public String[] exec(MC_CustomCommand command)
			throws TooFewArgumentsException, TooManyArgumentsException, NoPermissionException,
			PlayerOnlyException, ConsoleOnlyException, InvalidArgumentException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}

		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		// Get the player's inventory
		Inventory inv = player.getInventory();
		if (command.getArgs().length >= 1) {
			if (command.getArg(0).equalsIgnoreCase("chest")) {
				Block block = player.getTargetBlock(null, 5);
				if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
					if (!Utils.canBuild(block.getLocation(), player)) {
						return new String[]{ChatColor.RED + "That chest is private!"};
					}
					Chest chest = (Chest) block.getState();
					inv = chest.getInventory();
				}
				else if (block.getType().equals(Material.ENDER_CHEST)) {
					inv = player.getEnderChest();
				}
				else {
					return new String[]{ChatColor.RED + "No chest to sort"};
				}
			}
			else {
				throw new InvalidArgumentException(command.getArg(0));
			}
		}
		Inventory new_inv = Bukkit.createInventory(player.getPlayer(), 54);
		for (Material material : Material.values()) {
			for (ItemStack item : inv.getContents()) {
				if (item != null) {
					if (item.getType().equals(material)) {
						inv.removeItem(item);
						new_inv.addItem(item);
					}
				}
			}
		}
		for (ItemStack item : new_inv.getContents()) {
			if (item != null) {
				new_inv.removeItem(item);
				inv.addItem(item);
			}
		}
		return new String[]{ChatColor.GREEN + "Inventory sorted"};
	}
}