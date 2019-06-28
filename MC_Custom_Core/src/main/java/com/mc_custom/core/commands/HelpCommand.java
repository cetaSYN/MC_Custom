package com.mc_custom.core.commands;

import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.utils.ChatColor;

public class HelpCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"help"};
	}

	@Override
	public String getRequiredPermissions() {
		return null;
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 0) {
			throw new TooManyArgumentsException();
		}

		return new String[]{"Please visit ",
				ChatColor.GREEN + "http://url_removed/kb/articles/commands",
				"for detailed command information!"};
	}

	@Override
	public String[] getHelp() {
		return new String[]{"Wow, you must really need help!",
				"Try \"/<command> help\" for help with a specific command",
				"or visit " + ChatColor.GREEN + "http://url_removed/kb/articles/commands"
		};
	}
}