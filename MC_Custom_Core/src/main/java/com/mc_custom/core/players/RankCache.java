package com.mc_custom.core.players;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.timers.CoreTimer;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RankCache {

	private static RankCache rank_cache = new RankCache();
	private HashMap<Integer, Rank> ranks = new HashMap<>();

	private RankCache() {
		MC_Custom_Core.getTimerHandler().submitTask(new RankTimer());
	}

	public static RankCache getInstance() {
		return rank_cache;
	}

	public synchronized int getRank(int player_id) {
		Rank rank = ranks.get(player_id);

		//If greater than 15 minutes, get new rank.
		if (rank == null || rank.isAfterMinutes(15)) {
			rank = new Rank(player_id);
			ranks.put(player_id, rank);
		}
		return rank.rank;
	}

	private void checkCache() {
		// Use iterator here to avoid concurrency issues
		Iterator<Map.Entry<Integer, Rank>> it = ranks.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Rank> entry = it.next();
			if (entry.getValue().isAfterMinutes(1)) {
				it.remove();
			}
		}
	}

	private class Rank {
		protected int rank;
		protected long timestamp;

		public Rank(int player_id) {
			this.rank = getPlayerRank(player_id);
			this.timestamp = System.currentTimeMillis();
		}

		protected boolean isAfterMinutes(int minutes) {
			return (System.currentTimeMillis() / 1000 / 60) - (timestamp / 1000 / 60) > minutes;
		}

		private int getPlayerRank(final int player_id) {
			try {
				long duration = System.currentTimeMillis();
				int rank = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT COUNT(*) FROM `statistics` WHERE `points` > (SELECT `points` FROM `statistics` WHERE `player_id` = ?)")
						.setInt(player_id)
						.executeQuery()
						.fetchOne(Integer.class);
				// Algorithm gets # of players with higher score.
				// So we must increment by 1, or number 1 player will be ranked 0.
				PluginLogger.database().info(Utils.debugRunDuration(duration));
				return ++rank;
			}
			catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}
	}

	private class RankTimer extends CoreTimer {

		public RankTimer() {
			super(900000); //Repeat every 15 minutes.
		}

		@Override
		public void run() {
			checkCache();
		}
	}
}