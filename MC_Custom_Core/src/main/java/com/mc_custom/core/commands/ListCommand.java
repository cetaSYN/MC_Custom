package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;

import java.util.List;

public class ListCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"list"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.list";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 0) {
			throw new TooManyArgumentsException();
		}
		StringBuilder string_builder = new StringBuilder();
		List<CorePlayer> players = MC_Custom_Core.getOnlinePlayers();
		int reg_players = 0;
		int ignore_cap_players = 0;
		for (CorePlayer core_player : players) {
			if (!core_player.isVanished() || PermissionUtils.hasWildcardPermission(command.getSender(), "vanish.see")) {

				if (string_builder.length() > 0) {
					string_builder.append(", ");
				}

				string_builder.append(ChatColor.RESET);
				if (core_player.isAFK()) {
					string_builder.append("[AFK]");
				}
				string_builder.append(core_player.getName());
				if (!core_player.getName().equals(core_player.getPlayerName())) {
					String name = ChatColor.AQUA + "[" + core_player.getPlayerName() + "]";
					string_builder.append(name);
				}
				string_builder.append(ChatColor.AQUA);
			}
			if (core_player.ignoresPlayerCap()) {
				ignore_cap_players++;
			}
			else {
				reg_players++;
			}
		}
		String count_players = "There are " +
				ChatColor.YELLOW + reg_players +
				ChatColor.AQUA + " out of " +
				ChatColor.YELLOW + MC_Custom_Core.getMaxPlayers() +
				ChatColor.AQUA + " online " +
				ChatColor.YELLOW + "+" + ignore_cap_players +
				ChatColor.AQUA + " ignoring cap. (" +
				ChatColor.YELLOW + MC_Custom_Core.getOnlinePlayers().size() +
				ChatColor.AQUA + ")";

		return new String[]{count_players, string_builder.toString()};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Lists online players.",
				"Syntax: /list",
		};
	}

}
