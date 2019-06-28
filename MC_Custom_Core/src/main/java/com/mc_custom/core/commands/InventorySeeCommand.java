package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;

public class InventorySeeCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"invsee", "ie"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.invsee";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		if (command.getArgs().length > 1) {
			throw new TooManyArgumentsException();
		}
		if (command.getArgs().length < 1) {
			throw new TooFewArgumentsException();
		}

		CorePlayer core_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0));

		if (player.getPrimaryGroup().valueOf() < core_player.getPrimaryGroup().valueOf()) {
			core_player.openInventory(player.getInventory());
			return new String[]{};
		}
		return new String[]{ChatColor.RED + "You don't have permission to view this player's inventory."};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /invsee [player]"
		};
	}
}