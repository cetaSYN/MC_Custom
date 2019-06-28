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

public class MessageCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"tell", "message", "msg", "t", "m", "whisper", "w"};
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
		if (command.getArgs().length < 2) {
			throw new TooFewArgumentsException();
		}

		String message = "";
		final String recipient_name = command.getArg(0);
		final CorePlayer sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		CorePlayer recipient;

		//Get message from text following name.
		for (int i = 1; i < command.getArgs().length; i++) {
			message += " ";
			message += command.getArg(i);
		}

		//check ign
		try {
			recipient = MC_Custom_Core.getPlayerHandler().getPlayerByNameFuzzy(recipient_name);
		}
		catch (NotOnlineException noex) {
			//if not ign, check nick
			try {
				recipient = MC_Custom_Core.getPlayerHandler().getPlayerByNicknameFuzzy(recipient_name);
			}
			catch (NotOnlineException ex) { //not sure if needed?
				throw new NotOnlineException();
			}
		}

		if (recipient.isVanished()) {
			throw new NotOnlineException();
		}

		sender.sendMessageTo(recipient, message);
		for (CorePlayer player : MC_Custom_Core.getPlayerHandler().getPlayerList().values()) {
			if (player.hasPermission("core.socialspy")
					&& !player.equals(sender)
					&& !player.equals(recipient)) {
				player.sendMessage(ChatColor.YELLOW + "[" + sender.getName() + ChatColor.YELLOW + "] -> [" + recipient.getName() + ChatColor.YELLOW + "]:" + ChatColor.RESET + message);
			}
		}
		return new String[]{ChatColor.BLUE + "[Me] -> [" + recipient.getName() + ChatColor.BLUE + "]:" + ChatColor.RESET + message};
	}

	@Override
	public String[] getHelp() {
		//TODO Staff help
		return new String[]{"Syntax: /msg <name> <message>",
				"Sends a message to another player."};
	}
}