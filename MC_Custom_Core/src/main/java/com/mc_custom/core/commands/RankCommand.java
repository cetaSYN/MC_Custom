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

public class RankCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"rank"};
	}

	@Override
	public String getRequiredPermissions() {
		return null;
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

		switch (command.getArgs().length) {
			case 0:
				CorePlayer core_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
				return new String[]{ChatColor.BLUE + "Your current player rank is: #" + core_player.getRank(),
						"You currently have " + core_player.getPlayerStatistics().getTotalPoints() + " player points"};
			case 1:
				CorePlayer core_target_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0));
				return new String[]{ChatColor.BLUE + core_target_player.getName() + "'s player rank is: #" + core_target_player.getRank()};
		}
		return null;
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /rank",
				"Gets your player rank"
		};
	}
}