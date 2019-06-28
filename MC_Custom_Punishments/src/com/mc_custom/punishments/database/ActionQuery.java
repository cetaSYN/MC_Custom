package com.mc_custom.punishments.database;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import java.sql.SQLException;

/**
 * @deprecated Don't add anything new to this class. Queries should be placed in models
 */
public class ActionQuery {

	/**
	 * @deprecated Since the implementation of UUIDs, try and not use this method wherever possible.
	 * It will not work with player name changes, but is needed in some cases currently.
	 */
	public static int getPlayerIdByName(final String player_name) {
		try {
			long duration = System.currentTimeMillis();
			new QueryBuilder(MC_Custom_Core.getDBConnection(), "INSERT IGNORE INTO `players` (name) VALUES (?)")
					.setString(player_name)
					.update();
			Integer id = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `id` FROM `players` WHERE `name` = ? LIMIT 1")
					.setString(player_name)
					.executeQuery()
					.fetchOne(Integer.class);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
			if (id == null) {
				return 0;
			}
			return id;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

}