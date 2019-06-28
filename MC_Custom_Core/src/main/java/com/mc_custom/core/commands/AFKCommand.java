package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

public class AFKCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"afk", "awayfromkeyboard", "brb"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.afk";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		if (command.getArgs().length > 0) {
			throw new TooManyArgumentsException();
		}
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		player.setAFK(!player.isAFK());
		if(player.isAFK()) {
			player.getAFKTimer().beginTimer();
		}
		return player.isAFK() ? new String[]{"You are now AFK"} : new String[]{"You are no longer AFK"};
	}

	@Override
	public String[] getHelp() {
		return new String[]{"Syntax: /afk",
				"Toggles the afk status of a player"
		};
	}
}