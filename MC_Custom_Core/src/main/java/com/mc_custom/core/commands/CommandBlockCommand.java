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
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.chatbuilders.CommandBlockBuilder;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;

import java.util.ArrayList;
import java.util.List;

public class CommandBlockCommand extends BaseCommand {

	public List<String> command_whitelist = new ArrayList<>();

	public CommandBlockCommand() {
		command_whitelist.add("achievement");
		command_whitelist.add("clear");
		command_whitelist.add("clone");
		command_whitelist.add("effect");
		command_whitelist.add("enchant");
		command_whitelist.add("entitydata");
		command_whitelist.add("gamemode");
		command_whitelist.add("give");
		command_whitelist.add("kill");
		command_whitelist.add("particle");
		command_whitelist.add("playsound");
		command_whitelist.add("replaceitem");
		command_whitelist.add("scoreboard");
		command_whitelist.add("setblock");
		command_whitelist.add("spawnpoint");
		command_whitelist.add("spreadplayers");
		command_whitelist.add("summon");
		command_whitelist.add("tell");
		command_whitelist.add("testfor");
		command_whitelist.add("title");
		command_whitelist.add("tp");
		command_whitelist.add("xp");
		command_whitelist.add("strike");
	}

	@Override
	public String[] getCommandNames() {
		return new String[]{"setcommand", "setcmd", "sc", "commandbuilder", "cmdbuilder", "cb"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.commandblock";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			InvalidArgumentException, PlayerOnlyException, ConsoleOnlyException, NotOnlineException {
		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		Block block = player.getTargetBlock(Utils.getTransparentBlocks(), 5);
		if (block != null && block.getType().equals(Material.COMMAND)) {
			String name = command.getCommand();
			final CommandBlock command_block = (CommandBlock) block.getState();
			if (name.equalsIgnoreCase("setcommand") || name.equalsIgnoreCase("setcmd") || name.equalsIgnoreCase("sc")) {
				String command_string = "";
				for (String arg : command.getArgs()) {
					command_string += arg + " ";
				}
				command_string = command_string.trim();
				boolean whitelist = false;
				for (String whitelist_string : command_whitelist) {
					if (command_string.toLowerCase().startsWith(whitelist_string)) {
						if (command_string.contains("setdisplay")) {
							break;
						}
						if (command_string.startsWith("spreadplayers") && command_string.contains("@a")) {
							break;
						}
						whitelist = true;
						break;
					}
				}
				if (whitelist) {
					command_block.setCommand(command_string);
					command_block.update(true);
					return new String[]{"Command set: " + command_string};
				}
				else {
					player.sendMessage(ChatColor.RED + "String contains a blacklisted parameter. Contact a tech.");
					PluginLogger.core().warning(ChatColor.RED + player.getName() + " attempted to use blacklisted command in a commandblock.");
					PluginLogger.core().warning(player.getName() + ": " + command);
					command_block.setCommand("");
					command_block.update();
					if (!command_block.getCommand().isEmpty()) {
						MC_Custom_Core.runTaskAsynchronously(new Runnable() {
							public void run() {
								command_block.getBlock().setType(Material.AIR); //If at first you don't succeed, thermonuclear war is justified.
							}
						});
					}
				}
			}
			else {
				CommandBlockBuilder builder = new CommandBlockBuilder(player, command_block);
				player.setChatBuilder(builder);
				return new String[]{};
			}
		}
		return new String[]{ChatColor.RED + "You are not looking at a command block"};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Set the command in a command block",
				"Syntax: /setcommand <command>"
		};
	}
}
