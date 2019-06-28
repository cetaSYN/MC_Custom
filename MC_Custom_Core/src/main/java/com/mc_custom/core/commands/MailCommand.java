package com.mc_custom.core.commands;

import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotImplementedException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;

public class MailCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"mail"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.mail";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			NotImplementedException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {
		throw new NotImplementedException();
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /mail <send|read|clear> <player> <message>"
		};
	}
}