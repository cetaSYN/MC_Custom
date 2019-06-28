package com.mc_custom.punishments.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffHelpCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"staffhelp", "grief", "modhelp"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.reports.send";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException {
		if (command.getArgs().length < 2) {
			throw new TooFewArgumentsException();
		}

		CommandSender command_sender = command.getSender();
		//Feel free to modify layout
		Player player = (Player) command.getSender();
		Location location = player.getLocation();
		String report = "Player: " + player.getName() + " Location: " + location.toString() + "Comment: ";
		for (int i = 1; i < command.getArgs().length; i++) {
			report += " ";
			report += command.getArg(i);
		}
		//Add to reports list with name and location

		return new String[]{"Your report has been sent to staff."};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /staffhelp [comment]",
				"Sends a help report to staff with your name, location and comment",
				"Alternative: /grief, /modhelp"
		};
	}
}