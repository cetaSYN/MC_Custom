package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

public class CinematicFlightCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"cinematicflight", "cinematicfly", "cflight", "cfly"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.flight.cinematic";
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
		String command_arg = command.getArg(0);

		if (command.getArgs().length == 0) {
			if (core_player.getFlightHandler().hasCinematic()) {
				return new String[]{"Cinematic Flight: On"};
			}
			else {
				return new String[]{"Cinematic Flight: Off"};
			}
		}
		if (command_arg.equalsIgnoreCase("true") || command_arg.equalsIgnoreCase("on")) {
			core_player.getFlightHandler().setCinematic(true);
			return new String[]{"Cinematic Flight: On"};
		}
		else if (command_arg.equalsIgnoreCase("false") || command_arg.equalsIgnoreCase("off")) {
			core_player.getFlightHandler().setCinematic(false);
			return new String[]{"Flight Cinematic: Off"};
		}
		throw new InvalidArgumentException();
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /cinematicfly <On/Off>",
				"Toggles cinematic-mode flight.",
				"Can also use \"cinematicfly\", \"cflight\", and \"cfly\""
		};
	}
}