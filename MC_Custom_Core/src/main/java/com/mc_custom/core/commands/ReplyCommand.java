package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;

public class ReplyCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"reply", "remessage", "r", "rtell"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.message";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			InvalidArgumentException, PlayerOnlyException, NotOnlineException {
		if (!command.fromPlayer()) {
			throw new PlayerOnlyException(); //The later cast from sender to player will cause errors from console.
		}

		if (command.getArgs().length < 1) {
			throw new TooFewArgumentsException();
		}

		String message = "";
		final CorePlayer sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());

		//Get message from text following name.
		for (int i = 0; i < command.getArgs().length; i++) {
			message += " ";
			message += command.getArg(i);
		}

		final CorePlayer recipient = sender.getLastCommunicationFrom();

		try {
			sender.sendMessageTo(recipient, message);
		}
		catch (NullPointerException ex) { //Caused by having no player previously spoken to.
			throw new InvalidArgumentException("Nobody to reply to! Try using /msg to start a conversation!", false);
		}

		for (CorePlayer player : MC_Custom_Core.getPlayerHandler().getPlayerList().values()) {
			if (player.hasPermission("core.socialspy") && !player.getName().equalsIgnoreCase(command.getSender().getName())
					&& !player.getName().equalsIgnoreCase(recipient.getPlayerName())) {
				player.sendMessage(ChatColor.YELLOW + "[" + sender.getName() + ChatColor.YELLOW + "] -> [" + recipient.getName() + ChatColor.YELLOW + "]:" + ChatColor.RESET + message);
			}
		}
		return new String[]{"[Me] -> [" + recipient.getName() + ChatColor.AQUA + "]:" + ChatColor.RESET + message};
	}

	@Override
	public String[] getHelp() {
		//TODO Staff help
		return new String[]{"Syntax: /r <message>",
				"Sends a message to a recently-messaged player."};
	}
}