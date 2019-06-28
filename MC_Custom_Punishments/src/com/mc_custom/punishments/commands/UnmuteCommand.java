package com.mc_custom.punishments.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.*;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.punishments.ActionType;
import com.mc_custom.punishments.MC_Custom_Punishments;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class UnmuteCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"unmute"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.unmute";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws NoPermissionException,
			InvalidArgumentException, TooManyArgumentsException, TooFewArgumentsException {

		CommandSender command_sender = command.getSender();

		if (command.getArgs().length < 2) {
			command_sender.sendMessage("/unmute [player] [comment]");
			throw new TooFewArgumentsException();
		}
		String player_name = command.getArg(0);
		Player player = Bukkit.getPlayer(command.getArg(0));

		if (player != command_sender) {
			String[] broadcast = new String[]{ChatColor.BLUE + player_name + " has been unmuted by " + command_sender.getName(), "punish.receive_warnings"};
			try {
				if (MC_Custom_Punishments.getPlayerHandler().getPlayer(player).isMuted() && player.isOnline()) {
					MC_Custom_Punishments.getPlayerHandler().getPlayer(player).setMuted(false);
					player.sendMessage(ChatColor.BLUE + "You have been unmuted");
					MC_Custom_Punishments.getActionHandler().logAction(command, ActionType.UNMUTE, broadcast);
				}
				else {
					return new String[]{player_name + " is not currently muted."};
				}
			}
			catch (NotOnlineException e) {
				command_sender.sendMessage(ChatColor.BLUE + player_name + " will be unmuted next time they join");
				MC_Custom_Punishments.getActionHandler().logOfflineAction(command, ActionType.UNMUTE, broadcast);
			}
			return new String[]{""};
		}
		else {
			return new String[]{ChatColor.RED + "You cannot unmute yourself!"};
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{"A useful help comment goes here"};
	}
}