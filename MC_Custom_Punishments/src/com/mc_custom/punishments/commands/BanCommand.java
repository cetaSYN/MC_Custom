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


public class BanCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"ban", "permban"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.ban";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException, NoPermissionException, InvalidArgumentException {
		CommandSender command_sender = command.getSender();
		String player_name = command.getArg(0);
		if (command.getArgs().length < 2) {
			throw new TooFewArgumentsException();
		}
		String[] broadcast = new String[]{ChatColor.RED + player_name + " has been banished to the moon!", Server.BROADCAST_CHANNEL_USERS};
		Player player = Bukkit.getPlayer(command.getArg(0));
		if (player != command_sender) {
			if (player != null && player.isOnline()) { //if they are online, they are not banned
				if (PermissionUtils.hasWildcardPermission(command_sender, "punish.override")
						|| !PermissionUtils.hasWildcardPermission(player, "punish.ban")) {
					MC_Custom_Punishments.getActionHandler().logAction(command, ActionType.BAN, broadcast);
					player.kickPlayer("You have been sent to the moon!");
				}
				else {
					return new String[]{"You cannot ban staff!"};
				}
			}
			else { //we will check if they are banned when we log action
				MC_Custom_Punishments.getActionHandler().logOfflineAction(command, ActionType.BAN, broadcast);
			}
			return new String[]{player_name + " is banned"};
		}
		else {
			return new String[]{"You cannot ban yourself!"};
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{"A useful help comment goes here"};
	}
}