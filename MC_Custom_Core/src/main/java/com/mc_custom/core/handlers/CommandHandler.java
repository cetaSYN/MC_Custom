package com.mc_custom.core.handlers;

import com.mc_custom.core.MC_CustomPlugin;
import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.InvalidCommandException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotImplementedException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.core.utils.PluginLogger;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Set;

public class CommandHandler {
	private static CommandHandler commands = new CommandHandler();
	private final ArrayList<BaseCommand> command_list = new ArrayList<>();

	public static CommandHandler getInstance() {
		return commands;
	}

	public void execute(final MC_CustomCommand command) {
		try {
			// Match command
			for (BaseCommand i_command : command_list) {
				for (String i : i_command.getCommandNames()) {
					if (command.getCommand().equalsIgnoreCase(i)) {
						// Perform command
						if (i_command.getRequiredPermissions() == null
								|| PermissionUtils.hasWildcardPermission(command.getSender(),
								i_command.getRequiredPermissions())) {
							if (command.getArgs().length > 0 && command.getArg(0).equalsIgnoreCase("help")) {
								for (String help : i_command.getHelp()) {
									command.getSender().sendMessage(ChatColor.AQUA + help);
								}
								return;
							}
							else {
								for (String message : i_command.exec(command)) {
									if (message != null && !message.isEmpty()) {
										command.getSender().sendMessage(ChatColor.AQUA + message);
									}
								}
								return;
							}
						}
						else {
							throw new NoPermissionException();
						}
					}
				}
			}
			throw new InvalidCommandException();
		}
		catch (InvalidCommandException
				| NoPermissionException
				| NotOnlineException
				| ConsoleOnlyException
				| NotImplementedException
				| PlayerOnlyException ex) {
			command.getSender().sendMessage(ex.getMessage());
		}
		catch (InvalidArgumentException
				| TooManyArgumentsException
				| TooFewArgumentsException ex) {
			command.getSender().sendMessage(ex.getMessage(command.getCommand()));
			if (ex.getAdditionalDetails() != null) {
				command.getSender().sendMessage(ChatColor.RED + ex.getAdditionalDetails());
			}
		}
	}

	/**
	 * Adds a command to the list of commands to check for processing.
	 */
	public void addCommand(BaseCommand command) {
		command_list.add(command);
		PluginLogger.core().info(command.getClass().getSimpleName() + " added.");
	}

	/**
	 * Returns the list of commands registered to MC_Custom_Core.
	 * Includes commands from other plugins.
	 */
	public ArrayList<BaseCommand> getCommandList() {
		return command_list;
	}

	public void addCommands(MC_CustomPlugin plugin, String package_name) {
		PluginLogger.__(plugin.getName()).info("Scanning for commands in " + plugin.getName());
		Reflections reflections = new Reflections(package_name, plugin.getClass().getClassLoader());
		Set<Class<? extends BaseCommand>> command_classes = reflections.getSubTypesOf(BaseCommand.class);
		for (Class<? extends BaseCommand> command_class : command_classes) {
			try {
				BaseCommand command;
				try {
					Constructor<? extends BaseCommand> constructor = command_class.getDeclaredConstructor(plugin.getClass());
					command = constructor.newInstance(plugin);
				}
				catch(NoSuchMethodException e){
					Constructor<? extends BaseCommand> constructor = command_class.getDeclaredConstructor();
					command = constructor.newInstance();
				}
				command_list.add(command);
				PluginLogger.__(plugin.getName()).info(command_class.getSimpleName() + " added.");
			}
			catch (ReflectiveOperationException e) {
				PluginLogger.__(plugin.getName()).warning("Could not add command \"" + command_class.getSimpleName() + "\": " + e.getMessage());
			}
		}
	}
}