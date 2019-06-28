package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

import org.bukkit.GameMode;


public class GameModeCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"gamemode", "gmode", "gm", "gmc", "gms", "gma"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.gamemode";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 2) {
			throw new TooManyArgumentsException();
		}

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		CorePlayer sender_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		CorePlayer target_player = sender_player;

		if (command.getArgs().length == 2) {
			if (sender_player.hasPermission("core.gamemode.other")) {
				target_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(1));
			}
			else {
				throw new NoPermissionException();
			}
		}
		switch (command.getCommand()) {
			case "gms":
				target_player.setGameMode(GameMode.SURVIVAL);
				return new String[]{"GameMode: Survival."};
			case "gmc":
				target_player.setGameMode(GameMode.CREATIVE);
				return new String[]{"GameMode: Creative."};
			case "gma":
				target_player.setGameMode(GameMode.ADVENTURE);
				return new String[]{"GameMode: Adventure."};
		}

		if (command.getArgs().length < 1) {
			throw new TooFewArgumentsException();
		}

		switch (command.getArg(0).toLowerCase()) {
			case "0":
			case "survival":
			case "s":
				target_player.setGameMode(GameMode.SURVIVAL);
				return new String[]{"GameMode: Survival."};
			case "1":
			case "creative":
			case "c":
				target_player.setGameMode(GameMode.CREATIVE);
				return new String[]{"GameMode: Creative."};
			case "2":
			case "adventure":
			case "a":
				target_player.setGameMode(GameMode.ADVENTURE);
				return new String[]{"GameMode: Adventure."};
		}

		throw new InvalidArgumentException();
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Changes gamemodes.",
				"Syntax: /gamemode <mode> [player]",
				"You may also use \"gmode\" or \"gm\"."
		};
	}
}