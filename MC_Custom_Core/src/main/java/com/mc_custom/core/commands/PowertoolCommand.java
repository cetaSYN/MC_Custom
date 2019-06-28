package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PowertoolCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"powertool", "pt"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.powertool";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			InvalidArgumentException, PlayerOnlyException, ConsoleOnlyException, NotOnlineException {
		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}

		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		ItemStack hand = player.getItemInHand();
		if (hand == null || hand.getType().equals(Material.AIR)) {
			return new String[]{"Cannot bind a powertool to your hand"};
		}
		Material tool = hand.getType();
		if (tool.isBlock()) {
			return new String[]{"Cannot bind a powertool to a block"};
		}
		// Remove a powertool
		if (command.getArgs().length < 1) {
			player.removePowertool(tool);
			return new String[]{"Successfully cleared all commands from item"};
		}
		String tool_commands = "";
		for (String arg : command.getArgs()) {
			tool_commands += arg + " ";
		}
		player.addPowertool(tool, tool_commands);
		return new String[]{"Successfully bound command to item"};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Creates a powertool using the current item in hand",
				"Syntax: /powertool <command>"
		};
	}
}
