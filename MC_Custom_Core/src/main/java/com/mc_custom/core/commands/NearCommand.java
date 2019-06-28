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

import org.bukkit.Location;
import org.bukkit.World;


public class NearCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"near"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.near";
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

		double radius = 100;
		if (command.getArgs().length > 0) {
			try {
				radius = Double.parseDouble(command.getArg(0));
			}
			catch (NumberFormatException e) {
				throw new InvalidArgumentException(command.getArg(0) + " is not a valid radius.");
			}
		}
		radius = radius * radius;

		CorePlayer sender_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		Location sender_loc = sender_player.getLocation();
		World world = sender_player.getWorld();
		StringBuilder string_builder = new StringBuilder();
		String open_tag = ChatColor.YELLOW + "(";
		String close_tag = " blocks )";

		for (CorePlayer core_player : MC_Custom_Core.getOnlinePlayers()) {
			if (core_player != sender_player && !core_player.isVanished() && core_player.getWorld() == world) {
				Location player_loc = core_player.getLocation();
				double distance = player_loc.distanceSquared(sender_loc);
				if (distance < radius) {
					if (string_builder.length() > 0) {
						string_builder.append(", ");
					}
					string_builder.append(core_player.getName())
							.append(open_tag)
							.append(Math.round(Math.sqrt(distance)))
							.append(close_tag);
				}
			}
		}
		String[] return_array = new String[1];
		if (string_builder.length() > 1) {
			return_array[0] = string_builder.toString();
		}
		else {
			return_array[0] = "No players found";
		}
		return return_array;
	}

	@Override
	public String[] getHelp() {
		return new String[]{"Gets players near you",
				"Syntax: /near"
		};
	}
}