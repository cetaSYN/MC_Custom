package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

import org.bukkit.Location;

public class TopCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"top"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.top";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {
		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		Location location = player.getLocation();
		location.setY(player.getWorld().getHighestBlockYAt(player.getLocation()));
		player.teleport(location);
		return new String[]{"Zoom!"};
	}

	@Override
	public String[] getHelp() {
		return new String[]{"Jump to the highest point at your location", "Syntax: /top"};
	}
}
