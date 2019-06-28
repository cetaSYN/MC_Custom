package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.configuration.Configuration;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.handlers.SpawnHandler;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.TeleportDestination;

public class SpawnCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"spawn"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.spawn";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws NoPermissionException, PlayerOnlyException,
			NotOnlineException, InvalidArgumentException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}

		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		SpawnHandler spawns = Configuration.getInstance().getSpawnHandler();

		if (command.getArgs().length == 0) { // teleport to random spawn
			player.teleport(spawns.getRandomSpawn(player).getLocation());
			return new String[]{ChatColor.BLUE + "Teleported to spawn"};
		}
		else { // teleport to a spawn
			TeleportDestination spawn = spawns.get(command.getArg(0));
			if (spawn != null) {
				player.teleport(spawn.getLocation());
				return new String[]{ChatColor.BLUE + "Teleported to spawn"};
			}
			else { // teleport player to random spawn
				// Check if they have permission to send players to spawn
				if (!player.hasPermission("core.spawn.other")) {
					return new String[]{ChatColor.RED + "Spawn does not exist."};
				}

				CorePlayer target_player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getArg(0));
				if (target_player == null) {
					return new String[]{ChatColor.RED + "Player not found!"};
				}

				target_player.teleport(spawns.getRandomSpawn(target_player).getLocation());
				return new String[]{ChatColor.BLUE + target_player.getName() + " teleported to spawn"};
			}
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Teleports you to spawn.",
				"Syntax: /spawn",
				"/spawn [spawn name]"
		};
	}
}