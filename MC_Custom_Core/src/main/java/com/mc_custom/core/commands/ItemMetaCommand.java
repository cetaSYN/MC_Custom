package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemMetaCommand extends BaseCommand {
	@Override
	public String[] getCommandNames() {
		return new String[]{"itemlore", "itemname", "ilore", "iname"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.meta";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {
		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		CorePlayer sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		ItemMeta meta = sender.getItemInHand().getItemMeta();
		switch (command.getCommand().toLowerCase()) {
			case "itemlore":
			case "ilore":
				if (command.getArgs().length < 1) {
					throw new TooFewArgumentsException();
				}
				switch (command.getArg(0).toLowerCase()) {
					case "clear":
						meta.getLore().clear();
						break;
					case "set":
						meta.getLore().clear();
					case "add":
						String[] parts = new String[command.getArgs().length - 1];
						System.arraycopy(command.getArgs(), 1, parts, 0, parts.length);
						constructLore(meta, parts);
						break;
				}
				break;
			case "itemname":
			case "iname":
				StringBuilder s_builder = new StringBuilder();
				for (String arg : command.getArgs()) {
					s_builder.append(arg).append(' ');
				}
				meta.setDisplayName(ChatColor.convertColor(s_builder.toString().trim()));
				break;
		}
		sender.getItemInHand().setItemMeta(meta);
		return new String[]{};
	}

	private void constructLore(ItemMeta meta, String[] parts) {
		List<String> lore = meta.getLore() == null ? new ArrayList<String>(4) : meta.getLore();
		StringBuilder s_builder = new StringBuilder();
		for (String part : parts) {
			s_builder.append(part).append(' ');
		}
		for (String line : StringEscapeUtils.unescapeJava(s_builder.toString()).split("\n")) {
			lore.add(ChatColor.convertColor(line.trim()));
		}
		meta.setLore(lore);
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /itemname [name]",
				"Change the display name of an item",
				"/itemlore <set|add|clear> [lore]",
				"Modify the lore of an item",
				"When changing the lore, a new line may be denoted with \"\\n\"",
				"All input may include color codes"
		};
	}
}
