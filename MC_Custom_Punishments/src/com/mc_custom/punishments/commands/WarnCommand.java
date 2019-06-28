package com.mc_custom.punishments.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.punishments.ActionType;
import com.mc_custom.punishments.MC_Custom_Punishments;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarnCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"warn"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.warn";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException {
		CommandSender command_sender = command.getSender();
		if (command.getArgs().length < 2) {
			throw new TooFewArgumentsException();
		}
		String player_name = command.getArg(0);

		String[] broadcast = new String[]{ChatColor.RED + player_name + " has been warned by " + command_sender.getName(), "punish.receive_warnings"};
		Player player = Bukkit.getPlayer(command.getArg(0));
		if (player != command_sender) {
			MC_Custom_Punishments.getActionHandler().logOfflineAction(command, ActionType.WARN, broadcast);
			return new String[]{player_name + " has been warned"};
		}
		else {
			return new String[]{"You cannot warn yourself!"};
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{"A useful help comment goes here"};
	}
}