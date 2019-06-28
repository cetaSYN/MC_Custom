package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.util.LinkedList;

public class RealNameCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"rn", "realname", "rname"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.realname";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 1) {
			throw new TooManyArgumentsException();
		}
		if (command.getArgs().length == 0) {
			throw new TooFewArgumentsException();
		}

		PlayerHandler<CorePlayer> player_handler = MC_Custom_Core.getPlayerHandler();
		String nickname = command.getArg(0);
		if (nickname.startsWith("~")) {
			nickname = nickname.substring(1);
		}

		LinkedList<String> matches = new LinkedList<>();
		// Checks online players
		for (CorePlayer core_player : player_handler.getPlayerList().values()) {
			String player_nick = core_player.getNickname().get();
			player_nick = ChatColor.removeColor(player_nick);
			if (player_nick != null && !nickname.equals("") && player_nick.toLowerCase().contains(nickname.toLowerCase())) {
				matches.add(ChatColor.convertColor("~" + player_nick + " is " + core_player.getPlayerName()));
			}
		}
		// If no matches for online players, check the DB
		if (matches.size() == 0) {
			final CommandSender command_sender = command.getSender();
			final String check_nick = nickname;
			command_sender.sendMessage(ChatColor.AQUA + "Checking offline for player...");
			MC_Custom_Core.runTaskAsynchronously(new Runnable() {
				@Override
				public void run() {
					String player_name = getRealname(check_nick);
					if (player_name != null) {
						command_sender.sendMessage(ChatColor.AQUA + "~" + check_nick + " is " + player_name);
					}
					else {
						command_sender.sendMessage(ChatColor.AQUA + "Could not find a player with that nickname.");
					}
				}
			});
		}
		else {
			return matches.toArray(new String[matches.size()]);
		}
		return new String[]{""};
	}

	@Override
	public String[] getHelp() {
		//TODO Staff help
		return new String[]{
				"Syntax: /realname <nick>",
				"Gets the IGN of another player."
		};
	}

	// Unless this needs to be used elsewhere, I'm leaving it here.
	public String getRealname(final String nickname) {
		try {
			long duration = System.currentTimeMillis();
			String realname = new QueryBuilder(MC_Custom_Core.getDBConnection())
					.setQuery("SELECT `name` FROM `players` INNER JOIN `nicknames` ON `players`.`id` = `nicknames`.`player_id` WHERE `nickname` = ?")
					.setString(nickname)
					.executeQuery()
					.fetchOne(String.class);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
			return realname;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}