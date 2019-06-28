package com.mc_custom.core.utils;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.handlers.CommandHandler;

import org.bukkit.command.Command;

import java.util.Collection;
import java.util.List;

/**
 * CommandUtils Class to help with CommandListener
 */
public class CommandUtils {

	public static boolean checkBaseCommands(String command_phrase) {
		List<BaseCommand> command_list = CommandHandler.getInstance().getCommandList();
		for (BaseCommand command : command_list) {
			for (String command_name : command.getCommandNames()) {
				if (command_name.equalsIgnoreCase(command_phrase)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean checkPluginCommands(String command_phrase, Collection<Command> commands) {
		for (Command command : commands) {
			switch (command_phrase.toLowerCase()) {
				//Kill off potentially malicious or derpalicious commands.
				case "plugins":
				case "help":
				case "?":
				case "op":
				case "deop":
				case "defaultgamemode":
				case "seed":
				case "toggledownfall":
				case "clear":
				case "difficulty":
				case "spawnpoint":
				case "version":
				case "reload":
				case "rl":
				case "restart":
				case "reboot":
				case "stop":
				case "pl":
				case "ver":
				case "about":
				case "scoreboard":
				case "debug":
				case "testfor":
				case "title":
				case "playsound":
					break;
				default: //if it is a good command
					if (command.getName().equalsIgnoreCase(command_phrase)) {
						return true;
					} //or an alias of a good command
					for (String alias : command.getAliases()) {
						if (alias.equalsIgnoreCase(command_phrase)) {
							return true;
						}
					}
			}
		}
		return false;
	}
}
