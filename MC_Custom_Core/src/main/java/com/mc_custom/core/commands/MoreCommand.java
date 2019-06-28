package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

public class MoreCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"more"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.more";
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
		player.getItemInHand().setAmount(64); //Max stack size.
		return new String[]{""};
	}

	@Override
	public String[] getHelp() {
		return new String[]
				{"Syntax: /more",
						"Maxes the stack for the Item in hand."};
	}
}