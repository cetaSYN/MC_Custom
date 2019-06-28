package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.handlers.PermissionsHandler;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.players.PermissionGroup;
import com.mc_custom.core.utils.ChatColor;

public class ChangeGroupCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"changegroup", "chgrp"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.group.change";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, ConsoleOnlyException, NotOnlineException {

		if (!(command.fromConsole())) { //Console Only
			throw new ConsoleOnlyException();
		}
		if (command.getArgs().length > 2) { //Max of 2 Args
			throw new TooManyArgumentsException();
		}
		if (command.getArgs().length < 2) { //Min of 2 Args
			throw new TooFewArgumentsException();
		}

		CorePlayer cplayer = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0)); //this throws NOE on error
		String group_name = command.getArg(1);

		for (PermissionGroup group : PermissionsHandler.getInstance().getPermissionGroups().values()) {
			if (group.getName().equalsIgnoreCase(group_name)) {
				cplayer.changePlayerGroup(group.getId());
				if (cplayer.isOnline()) {
					cplayer.sendMessage(ChatColor.AQUA + "You have been moved to group: " + group.getPrefix() + group.getName().toUpperCase());
					cplayer.sendMessage(ChatColor.AQUA + "Please rejoin to have your new group take effect!");
				}
				return new String[]{cplayer.getName() + " has been changed to group " + group_name};
			}
		}
		throw new InvalidArgumentException("Could not find specified group. Group not changed.", false);
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Changes group of the player",
				"Syntax: /chgrp <name> <group>"
		};
	}
}