package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.utils.ChatColor;

public class SayCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"say"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.say";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length < 1) {
			throw new TooFewArgumentsException();
		}

		String broadcast = "";
		for (String arg : command.getArgs()) {
			broadcast += arg + " ";
		}
		MC_Custom_Core.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Say]: " + ChatColor.convertColor(broadcast));

		return new String[]{};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /say [message]"
		};
	}
}