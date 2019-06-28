package com.mc_custom.punishments.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.punishments.ActionType;
import com.mc_custom.punishments.MC_Custom_Punishments;
import org.bukkit.Server;

import static com.mc_custom.core.utils.ChatColor.BLUE;


public class UnbanCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"unban"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.unban";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException, NoPermissionException, InvalidArgumentException {

		if (command.getArgs().length < 2) {
			throw new TooFewArgumentsException();
		}
		String player_name = command.getArg(0);
		String[] broadcast = new String[]{BLUE + player_name + " has returned from the moon!", Server.BROADCAST_CHANNEL_USERS};
		MC_Custom_Punishments.getInstance().getActionHandler().logOfflineAction(command, ActionType.UNBAN, broadcast);
		return new String[]{player_name + " is unbanned."};
	}

	@Override
	public String[] getHelp() {
		return new String[]{"A useful help comment goes here"};
	}
}
