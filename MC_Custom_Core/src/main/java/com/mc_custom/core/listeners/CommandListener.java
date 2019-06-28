package com.mc_custom.core.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.handlers.CommandHandler;
import com.mc_custom.core.utils.CommandUtils;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.PrismUtils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;
import java.util.List;

public class CommandListener implements BaseListener {

	public CommandListener() {
	}

	/**
	 * Commands that are registered by other plugins are passed to Bukkit.
	 * Commands that are not used by other plugins are passed to MC_Custom_Core.
	 * Holy efficiency Batman!
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		try {
			if (event.isCancelled()) {
				return;
			}

			PluginLogger.core().info(event.getPlayer().getName() + " " + event.getMessage());

			String[] command_phrase = event.getMessage().split(" ");
			String command = command_phrase[0].replaceFirst("/", "").toLowerCase();

			if (CommandUtils.checkBaseCommands(command)) {
				event.setCancelled(true);
				if (MC_Custom_Core.pluginExists("Prism")) {
					PrismUtils.getInstance().callCommandEvent(event.getPlayer(), event.getMessage());
				}
				CommandHandler.getInstance().execute(new MC_CustomCommand(event.getPlayer(), command, Arrays.copyOfRange(command_phrase, 1, command_phrase.length)));
				return;
			}

			if (CommandUtils.checkPluginCommands(command, MC_Custom_Core.getKnownCommands().getCommands())
					&& !command.toLowerCase().equals("gamerule")) {
				return;
			}

			//Passes unused commands to Core
			event.setCancelled(true);
			if (MC_Custom_Core.pluginExists("Prism")) {
				PrismUtils.getInstance().callCommandEvent(event.getPlayer(), event.getMessage());
			}
			CommandHandler.getInstance().execute(new MC_CustomCommand(event.getPlayer(), command, Arrays.copyOfRange(command_phrase, 1, command_phrase.length)));
		}
		catch(NullPointerException ex){
			ex.printStackTrace(); //Hoping this will give us some more insight as to the spawn command issue.
		}
	}

	/**
	 * Commands that are registered by other plugins are passed to Bukkit.
	 * Commands that are not used by other plugins are passed to MC_Custom_Core.
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCommand(ServerCommandEvent event) {
		String[] command_phrase = event.getCommand().split(" ");

		if (CommandUtils.checkBaseCommands(command_phrase[0])) {
			event.setCommand("tps");
			CommandHandler.getInstance().execute(new MC_CustomCommand(event.getSender(), command_phrase[0], Arrays.copyOfRange(command_phrase, 1, command_phrase.length)));
			return;
		}

		if (command_phrase[0].equalsIgnoreCase("stop") || command_phrase[0].equalsIgnoreCase("version")
				|| CommandUtils.checkPluginCommands(command_phrase[0], MC_Custom_Core.getKnownCommands().getCommands())) {
			return;
		}

		//Passes unused commands to Core
		event.setCommand("tps");
		CommandHandler.getInstance().execute(new MC_CustomCommand(event.getSender(), command_phrase[0], Arrays.copyOfRange(command_phrase, 1, command_phrase.length)));
	}

	/**
	 * Proper command block handling that actually works and stuff
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommandBlock(final BlockRedstoneEvent event) {
		if (event.getNewCurrent() == event.getOldCurrent()) {
			return;
		}
		Block block = event.getBlock();
		if (block.getType().equals(Material.COMMAND)) {
			CommandBlock command_block = (CommandBlock) block.getState();
			String command_title = command_block.getCommand().split(" ")[0];
			List<BaseCommand> commands = CommandHandler.getInstance().getCommandList();
			for (BaseCommand command : commands) {
				for (String name : command.getCommandNames()) {
					if (name.equalsIgnoreCase(command_title)) {
						command_block.setCommand("cmd-wrap " + command_block.getCommand());
						command_block.update(true);
						return;
					}
				}
			}
		}
	}
}