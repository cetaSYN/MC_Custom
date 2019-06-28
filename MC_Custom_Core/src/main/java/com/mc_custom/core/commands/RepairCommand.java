package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

import org.bukkit.inventory.ItemStack;

public class RepairCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"repair"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.repair";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 1) {
			throw new TooManyArgumentsException();
		}

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}

		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		boolean all = false;
		if (command.getArgs().length == 1) {
			if (command.getArg(0).equals("all")) {
				all = true;
			}
		}

		repair(player, all);
		return new String[]{""};
	}

	@Override
	public String[] getHelp() {
		return new String[]
				{"Syntax: /more",
						"Maxes the stack for the Item in hand."};
	}

	private String[] repair(CorePlayer player, boolean all) {
		if ((player.getItemInHand() == null || player.getItemInHand().getType().isBlock()) && !all) {
			return new String[]{"Item cannot be repaired"};
		}
		ItemStack[] items = new ItemStack[]{player.getItemInHand()};
		if (all) {
			items = player.getInventory().getContents();
		}

		for (ItemStack item : items) {
			if (item != null && !item.getType().isBlock()) {
				item.setDurability((short) 0);
			}
		}
		if (all) {
			return new String[]{"Items in inventory repaired"};
		}
		return new String[]{"Item repaired"};
	}
}