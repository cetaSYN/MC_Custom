package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

public class ClearInventoryCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"clearinv", "ci", "clearinventory"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.clearinventory";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		if (command.getArgs().length > 0) {
			throw new TooManyArgumentsException();
		}
		CorePlayer core_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		core_player.getInventory().clear();

		return new String[]{"Inventory Cleared"};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /ci"
		};
	}
}