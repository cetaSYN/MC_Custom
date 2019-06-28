package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.configuration.Configuration;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.TeleportDestination;

import java.util.LinkedList;

public class WarpCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"warp", "warps"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.warp";
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
			case 0: // /warp
				return listWarps();
			case 1: //  /warp [place]
				return teleportPlayer(core_sender, command.getArg(0), true);
			case 2:
				//  /warp [player] [place]
				if (!core_sender.hasPermission("core.warp.other")) {
					throw new NoPermissionException();
				}
				CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0));
				return teleportPlayer(player, command.getArg(1), false);
			default:
				throw new TooFewArgumentsException();
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /warp [name]",
				"Teleports you to the specified warp or lists warps if none specified"
		};
	}

	private String[] teleportPlayer(CorePlayer player, String warp_name, boolean self) {
		TeleportDestination warp = Configuration.getInstance().getWarpHandler().get(warp_name);
		if (warp == null) {
			return new String[]{"That warp does not exist."};
		}
		player.teleport(warp.getLocation());

		if (self) {
			return new String[]{"Warping to " + warp.getName()};
		}
		player.sendMessage(ChatColor.AQUA + "Warping to " + warp.getName());
		return new String[]{"Warping " + player.getName() + " to " + warp.getName()};
	}

	private String[] listWarps() {
		LinkedList<TeleportDestination> warps = Configuration.getInstance().getWarpHandler().getAll();
		StringBuilder string_builder = new StringBuilder();
		string_builder.append("List of Warps: ");
		for (TeleportDestination warp : warps) {
			string_builder.append(warp.getName());
			string_builder.append(", ");
		}
		return new String[]{string_builder.toString()};
	}
}