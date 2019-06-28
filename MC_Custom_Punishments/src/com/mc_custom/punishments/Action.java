package com.mc_custom.punishments;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import java.sql.SQLException;

public class Action {

	private ActionType action_type;
	private int player_id;
	private int moderator_id;
	private long action_time;
	private String action_comment;

	public Action(int action_type, int player_id, int moderator_id, long action_date, String action_comment) {
		this(ActionType.getTypeByInt(action_type), player_id, moderator_id, action_date, action_comment);
	}

	public Action(ActionType action_type, int player_id, int moderator_id, long action_time, String action_comment) {
		this.action_type = action_type;
		this.player_id = player_id;
		this.moderator_id = moderator_id;
		this.action_time = action_time;
		this.action_comment = action_comment;
	}

	/**
	 * Gets the player's name.
	 *
	 * @return Returns the name of the player banned.
	 */
	public int getPlayerId() {
		return player_id;
	}

	/**
	 * Gets the Moderator's name.
	 *
	 * @return Returns the name of moderator.
	 */
	public int getModeratorId() {
		return moderator_id;
	}

	/**
	 * Gets the Actiontype.
	 *
	 * @return Returns banned or unbanned.
	 */
	public ActionType getActionType() {
		return action_type;
	}

	/**
	 * Get the action date.
	 *
	 * @return Returns a Calendar action date.
	 */
	public long getActionTime() {
		return action_time;
	}

	/**
	 * Gets the comment.
	 *
	 * @return Returns the comment of the action.
	 */
	public String getActionComment() {
		return action_comment;
	}

	/**
	 * Adds log of action to database.
	 */
	public void insert() {
		MC_Custom_Punishments.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				try {
					long duration = System.currentTimeMillis();
					new QueryBuilder(MC_Custom_Core.getDBConnection(), "INSERT INTO `punishments` (`action`, `player_id`, `moderator_id`, `ban_comment`,`date`) VALUES (?, ?, ?, ?, NOW())")
							.setInt(action_type.getInt())
							.setInt(player_id)
							.setInt(moderator_id)
							.setString(action_comment)
							.update();
					PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
