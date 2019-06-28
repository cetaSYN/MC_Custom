package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HatCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"hat"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.hat";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		if (player.getItemInHand() != null && !player.getItemInHand().getType().equals(Material.AIR)) {
			ItemStack swap_item = player.getItemInHand().clone();
			player.setItemInHand(player.getInventory().getHelmet());
			player.getInventory().setHelmet(swap_item);
			return new String[]{"Enjoy your new hat!"};
		}
		return new String[]{"You must be holding an item"};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /hat",
				"Puts the block you're holding on your head.",
				"Blockhead."
		};
	}
}
