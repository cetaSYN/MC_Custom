package com.mc_custom.core.commands;

import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class PlayerCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"playerinfo", "player", "pl", "who", "info"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.playerinfo";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 1) {
			throw new TooManyArgumentsException();
		}
		if (command.getArgs().length == 0) {
			throw new TooFewArgumentsException();
		}
		// TODO: coreplayer, nicknames and offline usage
		Player target = Bukkit.getPlayer(command.getArg(0));
		if (!target.isOnline()) {
			return new String[]{
					"Only works for online players partially now."
			};
		}

		return new String[]{
				"Name: " + target.getName(),
				"Display Name: " + target.getDisplayName(),
				"UUID: " + target.getUniqueId(),
				"Address: " + target.getAddress().toString(),
				"GameMode: " + target.getGameMode(),
				"Health: " + target.getHealth(),
				"Exhaustion: " + target.getExhaustion(),
				"Food: " + target.getFoodLevel()
		};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /playerinfo <player>",
				"Gets information on a player.",
				/*"You may use either nickname or IGN for online players.",*/
				/*"If the player is offline, you must use IGN.",*/
				"Alternatives: \"playerinfo\", \"player\", \"pl\", \"who\", \"info\""
		};
	}
}