package com.mc_custom.punishments.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.*;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.punishments.ActionType;
import com.mc_custom.punishments.MC_Custom_Punishments;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"mute"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.mute";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws NoPermissionException,
			InvalidArgumentException, TooManyArgumentsException, TooFewArgumentsException {

		CommandSender command_sender = command.getSender();

		if (command.getArgs().length < 2) {
			command_sender.sendMessage("/mute [player] [comment]");
			throw new TooFewArgumentsException();
		}
		String player_name = command.getArg(0);
		Player player = Bukkit.getPlayer(command.getArg(0));

		if (player != command_sender) {
			String[] broadcast = new String[]{ChatColor.RED + player_name + " has been muted by " + command_sender.getName(), "punish.receive_warnings"};
			try {//returns NotOnlineException if player is null
				if (!MC_Custom_Punishments.getPlayerHandler().getPlayer(player).isMuted() && player.isOnline()) {
					if (PermissionUtils.hasWildcardPermission(command_sender, "punish.override")
							|| !PermissionUtils.hasWildcardPermission(player, "punish.mute")) {  //can't mute others with that permission
						MC_Custom_Punishments.getPlayerHandler().getPlayer(player).setMuted(true);
						player.sendMessage(ChatColor.RED + "You have been muted");
						MC_Custom_Punishments.getActionHandler().logAction(command, ActionType.MUTE, broadcast);
					}
					else {
						return new String[]{"You cannot mute staff!"};
					}
				}
				else {
					return new String[]{player_name + " is already muted."};
				}
			}
			catch (NotOnlineException e) {
				command_sender.sendMessage(ChatColor.BLUE + player_name + " will be muted next time they join");
				MC_Custom_Punishments.getActionHandler().logOfflineAction(command, ActionType.MUTE, broadcast);
			}
			return new String[]{""};
		}
		else {
			return new String[]{"You cannot mute yourself!"};
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{"A useful help comment goes here"};
	}
}