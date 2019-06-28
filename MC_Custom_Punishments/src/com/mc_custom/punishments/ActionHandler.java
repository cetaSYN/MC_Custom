package com.mc_custom.punishments;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.database.Column;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;
import com.mc_custom.punishments.database.ActionQuery;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActionHandler {

	/**
	 * Gets player logs from the database in date descending order.
	 */
	public List<Action> getActions(final int player_id) {
		List<Action> actions = new ArrayList<>();
		try {
			long duration = System.currentTimeMillis();
			Column<Integer> punishment_id = new Column<>(Integer.class);
			Column<Integer> action = new Column<>(Integer.class);
			Column<Integer> moderator = new Column<>(Integer.class);
			Column<Long> action_date = new Column<>(Long.class);
			Column<String> ban_comment = new Column<>(String.class);

			new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `punishments`.`id`,"
					+ " `moderator_table`.`id` AS `moderator`, `action`, `date`, `ban_comment`"
					+ " FROM `punishments`"
					+ " INNER JOIN `players` AS `player_table` ON `player_table`.`id` = `punishments`.`player_id`"
					+ " INNER JOIN `players` AS `moderator_table` ON `moderator_table`.`id` = `punishments`.`moderator_id`"
					+ " WHERE `player_table`.`id` = ? ORDER BY `date` DESC")
					.setInt(player_id)
					.executeQuery()
					.fetchAll(punishment_id, moderator, action, action_date, ban_comment);

			for (int i = 0; i < punishment_id.size(); i++) {
				actions.add(new Action(
						action.getNext(), //Action
						player_id, //Player Id
						moderator.getNext(), //Moderator Id
						action_date.getNext(), //Action Date
						ban_comment.getNext())); //Comment
			}
			PluginLogger.database().info(Utils.debugRunDuration(duration));
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return actions;
	}

	public void logAction(MC_CustomCommand command, ActionType action, String[] broadcast) throws InvalidArgumentException {
		logAction(command, action, broadcast, false);
	}

	public void logOfflineAction(MC_CustomCommand command, ActionType action, String[] broadcast) throws InvalidArgumentException {
		logAction(command, action, broadcast, true);
	}

	private void logAction(MC_CustomCommand command, final ActionType action, final String[] broadcast, final boolean offline) throws InvalidArgumentException {
		String remark = "";
		final String player_name = command.getArg(0);
		int pid = 0;
		int moderator_id = 0;
		if (!offline) {
			try {
				// If they are online, we should be able to get the id from the coreplayer.
				pid = MC_Custom_Core.getPlayerHandler().getPlayer(player_name).getId();
			}
			catch (NotOnlineException e) {
				e.printStackTrace();
			}
		}
		else {
			// TODO: FIX!
			// Used for offline players, will not work with name changes
			pid = ActionQuery.getPlayerIdByName(player_name);
		}

		if (command.fromConsole()) {
			moderator_id = 1;
		}
		else {
			try {
				// If they are online, we should be able to get the id from the coreplayer.
				moderator_id = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer()).getId();
			}
			catch (NotOnlineException e) {
				e.printStackTrace();
			}
		}

		for (int i = 1; i < command.getArgs().length; i++) {
			remark += " ";
			remark += command.getArg(i);
		}

		final int player_id = pid;
		try {
			final Action new_action = new Action(action, player_id, moderator_id, System.currentTimeMillis(), remark);
			if (!offline) {
				Player player = Bukkit.getPlayer(player_name);
				MC_Custom_Punishments.getPlayerHandler().getPlayer(player).getPlayerActions().add(new_action);
			}
			MC_Custom_Punishments.runTaskAsynchronously(new Runnable() {
				@Override
				public void run() {
					boolean action_needed = false;
					//If a player is offline, check if action is needed before adding the action
					if (offline) {
						switch (action) {
							case BAN:
								action_needed = !isBanned(player_id);
								break;
							case UNBAN:
								action_needed = isBanned(player_id);
								break;
							case MUTE:
								action_needed = !isMuted(player_id);
								break;
							case UNMUTE:
								action_needed = isMuted(player_id);
								break;
							case FREEZE:
								action_needed = !isFrozen(player_id);
								break;
							case THAW:
								action_needed = isFrozen(player_id);
								break;
							case WARN:
								action_needed = true;
								break;
						}
						if (action_needed) {
							new_action.insert();
							MC_Custom_Punishments.broadcast(broadcast[0], broadcast[1]);
						}
					}
					else { //else the online player has already had actions checked and needs to be logged.
						new_action.insert();
						MC_Custom_Punishments.broadcast(broadcast[0], broadcast[1]);
					}
				}
			});
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if player is banned. Used for cases when player is not online.
	 *
	 * @param id of player
	 * @return if player is banned
	 */
	private boolean isBanned(int id) {
		Action most_recent = null;
		List<Action> actions = getActions(id);
		for (Action action : actions) {
			if (action.getActionType() == ActionType.BAN || action.getActionType() == ActionType.UNBAN) {
				if (most_recent == null) {
					most_recent = action;
				}
				if (action.getActionTime() > most_recent.getActionTime()) {
					most_recent = action;
				}
			}
		}
		return most_recent != null && most_recent.getActionType() == ActionType.BAN;
	}

	/**
	 * Checks if player is muted. Used for cases when player is not online.
	 *
	 * @param id of player
	 * @return if player is muted
	 */
	private boolean isMuted(int id) {
		Action most_recent = null;
		List<Action> actions = getActions(id);
		for (Action action : actions) {
			if (action.getActionType() == ActionType.MUTE || action.getActionType() == ActionType.UNMUTE) {
				if (most_recent == null) {
					most_recent = action;
				}
				if (action.getActionTime() > most_recent.getActionTime()) {
					most_recent = action;
				}
			}
		}
		return most_recent != null && most_recent.getActionType() == ActionType.MUTE;
	}

	/**
	 * Checks if player is frozen. Used for cases when player is not online.
	 *
	 * @param id of player
	 * @return if player is frozen
	 */
	private boolean isFrozen(int id) {
		Action most_recent = null;
		List<Action> actions = getActions(id);
		for (Action action : actions) {
			if (action.getActionType() == ActionType.FREEZE || action.getActionType() == ActionType.THAW) {
				if (most_recent == null) {
					most_recent = action;
				}
				if (action.getActionTime() > most_recent.getActionTime()) {
					most_recent = action;
				}
			}
		}
		return most_recent != null && most_recent.getActionType() == ActionType.FREEZE;
	}
}