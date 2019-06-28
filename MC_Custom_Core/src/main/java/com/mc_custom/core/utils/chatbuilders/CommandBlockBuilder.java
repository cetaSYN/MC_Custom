package com.mc_custom.core.utils.chatbuilders;

import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;

import org.bukkit.block.CommandBlock;

public class CommandBlockBuilder implements ChatBuilder {

	private final CommandBlock command_block;
	private final CorePlayer player;
	private String command;

	public CommandBlockBuilder(CorePlayer player, CommandBlock command_block) {
		this.command_block = command_block;
		this.player = player;
		this.command = command_block.getCommand();
		viewHelp();
		player.sendMessage(ChatColor.GREEN + "Current command: " + command);
	}

	public void update(String message) {
		String header = message.split(" ")[0];
		switch (header.toLowerCase()) {
			case "help":
				viewHelp();
				break;
			case "quit":
			case "exit":
				player.setChatBuilder(null);
				player.sendMessage(ChatColor.GREEN + "Discarded changes and exited the builder");
				break;
			case "commit":
				command_block.setCommand(command.trim());
				command_block.update(true);
				player.setChatBuilder(null);
				player.sendMessage(ChatColor.GREEN + "Successfully set the command and exited the builder");
				break;
			case "view":
				player.sendMessage(ChatColor.GREEN + "Current command:");
				player.sendMessage(ChatColor.GREEN + command);
				break;
			case "reset":
				command = "";
				player.sendMessage(ChatColor.GREEN + "Reset the builder");
				break;
			default:
				command += message + " ";
				break;
		}
	}

	public String[] getHelp() {
		return new String[]{
				"MC_Custom Command Builder",
				"Typing in chat will append the message to the command being built",
				"Type \"view\" to see the current command",
				"Type \"reset\" to reset the command",
				"Type \"commit\" to save changes and exit the builder",
				"Type \"exit\" to quit the builder without saving",
				"Type \"help\" to view this help"
		};
	}

	public void viewHelp() {
		for (String help : getHelp()) {
			player.sendMessage(ChatColor.AQUA + help);
		}
	}
}
