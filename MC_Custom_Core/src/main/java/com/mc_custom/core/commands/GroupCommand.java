package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotImplementedException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.handlers.PermissionsHandler;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.players.PermissionGroup;
import com.mc_custom.core.utils.ChatColor;

public class GroupCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"group", "grp"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.group";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NotImplementedException,
			NoPermissionException, InvalidArgumentException, ConsoleOnlyException, NotOnlineException {

		if (command.getArgs().length > 3) { //Max of 2 Args
			throw new TooManyArgumentsException();
		}
		if (command.getArgs().length < 3) { //Min of 2 Args
			throw new TooFewArgumentsException();
		}

		//group add player group
		//group remove player group
		switch (command.getArg(0).toLowerCase()) {
			case "add":
				CorePlayer cplayer = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(1));
				String group_name = command.getArg(2);
				for (PermissionGroup group : PermissionsHandler.getInstance().getPermissionGroups().values()) {
					if (group.getName().equalsIgnoreCase(group_name)) {
						cplayer.addPlayerGroup(group.getId());
						if (cplayer.isOnline()) {
							cplayer.sendMessage(ChatColor.AQUA + "You have been added to group: " + group.getPrefix() + group.getName().toUpperCase());
							cplayer.sendMessage(ChatColor.AQUA + "Please rejoin to have your new group take effect!");
						}
						return new String[]{cplayer.getName() + " has been added to group " + group_name};
					}
				}
				throw new InvalidArgumentException("Could not find specified group. Group not added.", false);
			case "remove":
			case "rm":
			default:
				throw new NotImplementedException();
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Add or remove players from groups",
				"Syntax: /group <add|remove> <name> <group>"
		};
	}
}