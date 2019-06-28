package com.mc_custom.punishments.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.punishments.ActionType;
import com.mc_custom.punishments.MC_Custom_Punishments;
import com.mc_custom.punishments.players.ActionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"freeze"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.freeze";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException,
			InvalidArgumentException, NotOnlineException {

		CommandSender command_sender = command.getSender();

		if (command.getArgs().length < 2) {
			command_sender.sendMessage("/freeze [player] [comment]");
			throw new TooFewArgumentsException();
		}
		String player_name = command.getArg(0);
		Player player = Bukkit.getPlayer(player_name);
		if (player != command_sender) {
			String[] broadcast = new String[]{ChatColor.RED + player_name + " has been frozen by " + command_sender.getName(), "punish.receive_warnings"};
			try {
				ActionPlayer action_player = MC_Custom_Punishments.getPlayerHandler().getPlayer(player);
				if (!action_player.isFrozen() && player.isOnline()) {
					if (PermissionUtils.hasWildcardPermission(command_sender, "punish.override")
							|| !action_player.hasPermission("punish.freeze")) { //can't freeze others with that permission
						action_player.setFrozen(true);
						action_player.setMuted(true);
						action_player.sendMessage(ChatColor.RED + "You have been frozen!");
						MC_Custom_Punishments.getActionHandler().logAction(command, ActionType.FREEZE, broadcast);
					}
					else {
						return new String[]{"You cannot freeze staff!"};
					}
				}
				else {
					return new String[]{player_name + " is already frozen."};
				}
			}
			catch (NotOnlineException e) {
				command_sender.sendMessage(ChatColor.BLUE + player_name + " will be frozen next time they join");
				MC_Custom_Punishments.getActionHandler().logOfflineAction(command, ActionType.FREEZE, broadcast);
			}
			return new String[]{""};
		}
		else {
			return new String[]{"You cannot freeze yourself!"};
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{"Syntax: /freeze [player] [comment]",
				"Freezes and mutes the player"};
	}
}