package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

public class EnderchestCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"echest", "enderchest", "enc"};
	} //"ec" required for Buycraft

	@Override
	public String getRequiredPermissions() {
		return "core.enderchest";
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
		CorePlayer core_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		if (command.getArgs().length == 1) {
			if (core_player.hasPermission("core.enderchest.other")) {
				CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0));
				core_player.openInventory(player.getEnderChest());
			}
			else {
				throw new NoPermissionException();
			}
		}
		core_player.openInventory(core_player.getEnderChest());
		return new String[]{};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /enderchest [player]"
		};
	}
}