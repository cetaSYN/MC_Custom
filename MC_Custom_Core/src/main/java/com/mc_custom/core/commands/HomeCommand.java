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
import com.mc_custom.core.utils.TeleportDestination;

import java.util.ArrayList;

public class HomeCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"home", "homes"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.home";
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

		CorePlayer core_sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		switch (command.getArgs().length) {
			case 0: // /home
				return listHomes(core_sender);
			case 1: //  /home [place]
				String place = command.getArg(0);
				String[] split = place.split(";|,|:");
				if (split.length == 1 && split[0].equalsIgnoreCase(place)) {
					return teleportPlayer(core_sender, place, true);
				}
				return teleportPlayer(core_sender, split[0], split[1]);
			case 2:
				//  /home [player] [place]
				if (!core_sender.hasPermission("core.home.other")) {
					throw new NoPermissionException();
				}
				CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0));
				if (command.getArg(1).equalsIgnoreCase("list")) {
					listHomes(player);
				}
				return teleportPlayer(player, command.getArg(1), false);
			default:
				throw new TooFewArgumentsException();
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /home [list|name]",
				"Teleports you to your selected home, or lists available homes."
		};
	}

	private String[] teleportPlayer(CorePlayer player, String home_name, boolean self) {
		TeleportDestination home = player.getHomes().get(home_name);
		if (home == null) {
			return new String[]{"Silly player. There's no home by that name."};
		}
		player.teleport(home.getLocation());

		if (self) {
			return new String[]{"Teleporting to " + home.getName()};
		}
		player.sendMessage(ChatColor.AQUA + "Teleporting to " + home.getName());
		return new String[]{"Teleporting " + player.getName() + " to " + home.getName()};
	}

	private String[] teleportPlayer(CorePlayer player, String player_name, String home_name) throws NotOnlineException {
		CorePlayer home_player = MC_Custom_Core.getPlayerHandler().getPlayer(player_name);
		TeleportDestination home = home_player.getHomes().get(home_name);
		if (home == null) {
			return new String[]{"Silly player. There's no home by that name."};
		}
		player.teleport(home.getLocation());
		return new String[]{"Teleporting " + player.getName() + " to " + home.getName()};
	}

	private String[] listHomes(CorePlayer player) {
		ArrayList<TeleportDestination> homes = player.getHomes().getHomes();
		StringBuilder string_builder = new StringBuilder();
		string_builder.append("List of homes: ");
		for (TeleportDestination home : homes) {
			string_builder.append(home.getName());
			string_builder.append(", ");
		}
		return new String[]{string_builder.toString()};
	}
}