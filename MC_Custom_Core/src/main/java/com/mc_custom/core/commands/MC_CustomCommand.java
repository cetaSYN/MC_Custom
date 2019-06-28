package com.mc_custom.core.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MC_CustomCommand {

	private final CommandSender sender;
	private final String command;
	private final String[] args;

	public MC_CustomCommand(CommandSender sender, String command, String[] args) {
		this.sender = sender;
		this.command = command;
		this.args = args;
	}

	public CommandSender getSender() {
		return sender;
	}

	public Player getSenderPlayer() {
		return fromPlayer() ? (Player) sender : null;
	}

	public String getCommand() {
		return command;
	}

	public String[] getArgs() {
		return args;
	}

	public String getArg(int i) {
		return args[i];
	}

	public boolean fromPlayer() {
		return sender instanceof Player;
	}

	public boolean fromConsole() {
		return sender instanceof ConsoleCommandSender;
	}

	public boolean fromCommandBlock() { return sender instanceof BlockCommandSender; }
}
