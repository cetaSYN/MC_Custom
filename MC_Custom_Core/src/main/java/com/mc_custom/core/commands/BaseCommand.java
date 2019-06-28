package com.mc_custom.core.commands;

import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotImplementedException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;

public abstract class BaseCommand {

	public abstract String[] getCommandNames();

	public abstract String getRequiredPermissions();

	public abstract String[] exec(MC_CustomCommand command) throws
			TooManyArgumentsException,
			TooFewArgumentsException,
			NoPermissionException,
			NotImplementedException,
			InvalidArgumentException,
			PlayerOnlyException,
			ConsoleOnlyException,
			NotOnlineException;

	public abstract String[] getHelp();
}