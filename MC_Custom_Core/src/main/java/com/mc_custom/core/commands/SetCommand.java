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
import com.mc_custom.core.utils.TeleportDestination;

import java.util.regex.Pattern;

/**
 * Command to set homes/warps/spawns
 */
public class SetCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"set", "setspawn", "sethome", "setwarp", "spawnset", "warpset", "homeset"};
	}

	@Override
	public String getRequiredPermissions() {
		return null;
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws NoPermissionException, PlayerOnlyException,
			NotOnlineException, TooManyArgumentsException, TooFewArgumentsException, InvalidArgumentException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		if (command.getArgs().length < 1) {
			throw new TooFewArgumentsException();
		}
		if (command.getArgs().length > 2) {
			throw new TooManyArgumentsException();
		}

		String command_name = command.getCommand().toLowerCase();
		String loc_name = command.getArg(0);
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());

		// /set [home|warp|spawn]
		if (command_name.equals("set")) {
			if (command.getArgs().length < 2) {
				throw new TooFewArgumentsException();
			}
			command_name = command.getArg(0);
			loc_name = command.getArg(1);
		}
		// /set[home|warp|spawn]
		else if (command_name.startsWith("set")) {
			command_name = command_name.substring(3);
		}
		// /[home|warp|spawn]set
		else if (command_name.endsWith("set")) {
			command_name = command_name.substring(0, command_name.length() - 3);
		}
		else {
			// something must not be right...
			throw new InvalidArgumentException();
		}

		return setItem(command_name, loc_name, player);
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Sets a home",
				"Syntax: /sethome [name]"
		};
	}

	private String[] setItem(String command_name, String loc_name, CorePlayer player) throws NoPermissionException, InvalidArgumentException {
		checkName(loc_name);
		TeleportDestination td = new TeleportDestination(player.getLocation(), loc_name);
		switch (command_name.toLowerCase()) {
			case "warp":
				if (!player.hasPermission("core.setwarp")) {
					throw new NoPermissionException();
				}
				Configuration.getInstance().getWarpHandler().add(td);
				Configuration.getInstance().updateNode("warps", td.getName(), td.toJson());
				return new String[]{"Warp " + loc_name + " has been set"};
			case "home":
				if (!player.hasPermission("core.sethome")) {
					throw new NoPermissionException();
				}
				//permissions for staff are checked in this method
				if (!player.getHomes().canSetHome()) {
					return new String[]{"You have reached your maximum homes count"};
				}
				player.getHomes().add(td);
				return new String[]{"Home " + loc_name + " has been set"};
			case "spawn":
				if (!player.hasPermission("core.setspawn")) {
					throw new NoPermissionException();
				}
				Configuration.getInstance().getSpawnHandler().add(td);
				Configuration.getInstance().updateNode("spawns", td.getName(), td.toJson());
				return new String[]{"Spawn " + loc_name + " has been set"};
			default:
				throw new NoPermissionException();
		}
	}

	private void checkName(String name) throws InvalidArgumentException {
		if (name.length() > 20) {
			throw new InvalidArgumentException("The name is too long!");
		}
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
		if (!pattern.matcher(name).matches()) {
			throw new InvalidArgumentException("The name must be letters, numbers, or an underscore!");
		}
	}
}