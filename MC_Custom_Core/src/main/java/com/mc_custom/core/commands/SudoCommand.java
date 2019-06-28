package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.players.PermissionGroup;
import com.mc_custom.core.utils.ChatColor;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Collection;

public class SudoCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"sudo"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.sudo";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, ConsoleOnlyException, NotOnlineException {

		if (command.getArgs().length < 2) {
			throw new TooFewArgumentsException();
		}

		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0));

		String message = "/";
		for (int i = 1; i < command.getArgs().length; i++) {
			message += command.getArg(i) + " ";
		}

		message = message.trim();

		if (command.fromConsole()) {
			return sudoPlayer(player, message);
		}
		else {
			CorePlayer sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
			Collection<PermissionGroup> sender_groups = sender.getGroups();
			for (PermissionGroup group : sender_groups) {
				if (player.inGroup(group.getName())) {
					return new String[]{"You cannot sudo this player"};
				}
			}
			return sudoPlayer(player, message);
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Force a player to chat or run a command",
				"Syntax: /sudo [player] [command]"};
	}

	private String[] sudoPlayer(CorePlayer player, String message) throws NotOnlineException {
		MC_Custom_Core.callEvent(new PlayerCommandPreprocessEvent(player.getPlayer(), message));
		return new String[]{"Forcing " + player.getName() + ChatColor.AQUA + " to run command: " + message};
	}
}
