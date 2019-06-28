package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

public class GodModeCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"god", "godmode"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.godmode";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}

		if (command.getArgs().length > 2) {
			throw new TooManyArgumentsException();
		}

		CorePlayer sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());

		switch (command.getArgs().length) {
			case 0: // /god
				sender.toggleGodMode();
				return new String[]{"Godmode: " + (sender.getGodMode() ? "enabled" : "disabled")};
			case 1: // /god [player]
				if (!sender.hasPermission("core.god.other")) {
					throw new NoPermissionException();
				}
				CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0));
				player.toggleGodMode();
				player.sendMessage("Godmode: " + (sender.getGodMode() ? "enabled" : "disabled"));
				return new String[]{""};
			default:
				throw new TooFewArgumentsException();
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /give [player] [item:data] [amount]"
		};
	}
}