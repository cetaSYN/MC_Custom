package com.mc_custom.core.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import io.netty.buffer.ByteBuf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PacketListener extends PacketAdapter {

	public List<String> command_whitelist = new ArrayList<>();

	public PacketListener() {
		super(MC_Custom_Core.getInstance(), PacketType.Play.Client.TAB_COMPLETE, PacketType.Play.Client.CUSTOM_PAYLOAD);
		command_whitelist.add("achievement");
		command_whitelist.add("clear");
		command_whitelist.add("clone");
		command_whitelist.add("cmd-wrap");
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
	public void onPacketReceiving(PacketEvent event) {
		PacketContainer packet = event.getPacket();

		// Tab-Exploit Handling
		if (packet.getType() == PacketType.Play.Client.TAB_COMPLETE) {
			String message = packet.getSpecificModifier(String.class).read(0).toLowerCase();
			String message_parts[] = message.split(" ");
			if (message_parts.length < 2 && message_parts.length > 0 && message_parts[0].contains("/")) {
				event.setCancelled(true);
			}
			else {
				Pattern pattern = Pattern.compile("^/(ver|about|help).*");
				if (pattern.matcher(message_parts[0]).matches()) {
					event.setCancelled(true);
				}
			}
		}
		// End Tab-Exploit Handling


		// Command Block Handling
		Player player = event.getPlayer();
		if (packet.getType() == PacketType.Play.Client.CUSTOM_PAYLOAD
				&& packet.getStrings().read(0).equals("MC|AdvCdm")) {
			if (player.hasPermission("core.commandblock")) {
				ByteBuf buf = (ByteBuf) packet.getModifier().withType(ByteBuf.class).read(0);
				byte[] content = buf.array();

				StringBuilder sb = new StringBuilder();
				// http://wiki.vg/Plugin_channel#MC.7CAdvCdm
				// Zeroth byte = type
				// If type = 0 next 12 bytes are ints x, y, z
				// If type = 1 next 4 bytes are int EntityId
				// Strings are prefixed with a VarInt of 1 byte denoting string length
				int i = 14;
				if (content[0] == 0x01) {
					i = 6;
				}
				for (; i < content.length; i++) {
					if (content[i] >= 32) {
						sb.append((char) content[i]);
					}
				}
				String command = sb.toString();
				if (command.startsWith("/")) {
					command = command.replaceFirst("/", "");
				}

				Block target_block = player.getTargetBlock(Utils.getTransparentBlocks(), 20);

				if (target_block.getType() == Material.COMMAND
						&& target_block.getState() instanceof CommandBlock) {
					final CommandBlock command_block = (CommandBlock) target_block.getState();
					player.sendMessage(ChatColor.AQUA + "You accessed a command block");

					boolean whitelisted = false;

					for (String whitelisted_string : command_whitelist) {
						if (command.toLowerCase().startsWith(whitelisted_string)) {
							if (command.contains("setdisplay")) {
								break;
							}
							if (command.startsWith("spreadplayers") && command.contains("@a")) {
								break;
							}
							whitelisted = true;
							break;
						}
					}
					if (whitelisted) {
						command_block.setCommand(command);
						command_block.update(true);
					}
					else {
						player.sendMessage(ChatColor.RED + "String contains a blacklisted parameter. Contact a tech.");
						PluginLogger.core().warning(ChatColor.RED + player.getName() + " attempted to use blacklisted command in a commandblock.");
						PluginLogger.core().warning(player.getName() + ": " + command);
						command_block.setCommand("");
						command_block.update();
						Bukkit.getScheduler().scheduleSyncDelayedTask(MC_Custom_Core.getInstance(), new Runnable() {
							public void run() {
								command_block.getBlock().setType(Material.AIR); //If at first you don't succeed, thermonuclear war is justified.
							}
						});
					}
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "You do not have permission");
			}
		}
		//End Command Block Handling
	}
}