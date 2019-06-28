package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

public class NoDriftCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"nodrift", "nd"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.flight.nodrift";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 1) {
			throw new TooManyArgumentsException();
		}

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}

		CorePlayer core_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());

		if (command.getArgs().length == 0) {
			if (core_player.getFlightHandler().hasDrift()) {
				return new String[]{"Flight Drift: On"};
			}
			else {
				return new String[]{"Flight Drift: Off"};
			}
		}
		// nodrift on == flight drift off
		if (command.getArg(0).equalsIgnoreCase("true") || command.getArg(0).equalsIgnoreCase("on")) {
			core_player.getFlightHandler().setDrift(false);
			return new String[]{"Flight Drift: Off"};
		}
		else if (command.getArg(0).equalsIgnoreCase("false") || command.getArg(0).equalsIgnoreCase("off")) {
			core_player.getFlightHandler().setDrift(true);
			return new String[]{"Flight Drift: On"};
		}
		throw new InvalidArgumentException();
	}

	@Override
	public String[] getHelp() {
		return new String[]
				{"Syntax: /nodrift <On/Off>",
						"Toggles flight drift.",
						"Can also use /nd"};
	}
}