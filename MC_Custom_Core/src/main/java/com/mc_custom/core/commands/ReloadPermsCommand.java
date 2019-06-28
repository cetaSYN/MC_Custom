package com.mc_custom.core.commands;

import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.handlers.PermissionsHandler;

public class ReloadPermsCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"reload_perms"}; //Yes, the command is meant to be difficult.
	}

	@Override
	public String getRequiredPermissions() {
		return "core.reloadpermissions";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length >= 1) {
			throw new TooManyArgumentsException();
		}
		PermissionsHandler.getInstance().updatePermissionGroups();
		return new String[]{"Permissions reloaded."};
	}

	@Override
	public String[] getHelp() {
		return new String[]{"/reload_mc_custom_configurations"};
	}
}