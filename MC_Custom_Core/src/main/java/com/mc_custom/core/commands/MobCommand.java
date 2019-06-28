package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.Utils;

import org.bukkit.Location;

public class MobCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"mob", "spawnmob", "mobspawn"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.spawnmob";
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
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		Location loc = player.getTargetBlock(Utils.getTransparentBlocks(), 100).getLocation();
		String mobname = command.getArg(0);
		int amount = 1;
		if (command.getArgs().length > 1) {
			String amount_s = command.getArg(1);
			try {
				amount = Integer.parseInt(amount_s);
			}
			catch (NumberFormatException e) {
				amount = 1;
			}
		}
		for (int i = 0; i < amount; i++) {
			player.getWorld().spawnEntity(loc, Utils.getMob(mobname));
		}

		return new String[]{amount + " " + mobname + " spawned."};
	}

	@Override
	public String[] getHelp() {
		return new String[]{"Syntax: /spawnmob [mob] [amount]",
				"spawns a mob of the type and amount specified"
		};
	}
}