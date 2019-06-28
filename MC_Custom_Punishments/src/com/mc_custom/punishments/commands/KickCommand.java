package com.mc_custom.punishments.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.punishments.ActionType;
import com.mc_custom.punishments.MC_Custom_Punishments;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class KickCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"kick"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.kick";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException, NoPermissionException, InvalidArgumentException {
		CommandSender command_sender = command.getSender();
		if (command.getArgs().length < 2) {
			throw new TooFewArgumentsException();
		}
		String player_name = command.getArg(0);
		Player player = Bukkit.getPlayer(player_name);

		String[] broadcast = new String[]{ChatColor.RED + player_name + " has been kicked!", Server.BROADCAST_CHANNEL_USERS};
		if (player != command_sender) {
			if (player != null && player.isOnline()) {
				if (PermissionUtils.hasWildcardPermission(command_sender, "punish.override")
						|| !PermissionUtils.hasWildcardPermission(player, "punish.kick")) {
					player.kickPlayer("You have been kicked!");
				}
				else {
					return new String[]{"You cannot kick staff!"};
				}
			}
			MC_Custom_Punishments.getActionHandler().logAction(command, ActionType.KICK, broadcast);
			return new String[]{"You have kicked " + player_name};
		}
		else {
			return new String[]{"You cannot kick yourself!"};
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{"A useful help comment goes here"};
	}
}