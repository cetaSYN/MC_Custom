package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

public class HealCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"heal"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.heal";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		if (command.getArgs().length > 1) {
			throw new TooManyArgumentsException();
		}

		if (command.getArgs().length > 0) {
			String player_name = command.getArg(0);
			CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(player_name);
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.sendMessage("You have been healed.");
			return new String[]{player_name + " has been healed."};
		}
		else {
			CorePlayer sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
			sender.setHealth(sender.getMaxHealth());
			sender.setFoodLevel(20);
			return new String[]{"You have been healed."};
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Toggles the afk status of a player",
				"Syntax: /afk"
		};
	}
}