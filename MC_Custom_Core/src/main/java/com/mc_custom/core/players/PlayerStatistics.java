package com.mc_custom.core.players;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import java.sql.SQLException;

public class PlayerStatistics {

	private int player_id;

	private int points = 0;
	private int total_points = 0;
	private int minutes_online = 0;
	private int blocks_broken = 0;
	private int blocks_placed = 0;
	private double distance_travelled = 0;
	private int mobs_killed = 0;
	private int deaths = 0;
	//TODO Minigames won/lost
	//TODO Minigames played

	public PlayerStatistics(int player_id) {
		this.player_id = player_id;
		checkJoin();
	}

	public PlayerStatistics(int player_id, int total_points) {
		this.player_id = player_id;
		this.total_points = total_points;
	}

	//TODO Utilization of points
	public int getPoints() {
		return points;
	}

	public int getTotalPoints() {
		return total_points;
	}

	public int getMinutesOnline() {
		return minutes_online;
	}

	public int getBlocksBroken() {
		return blocks_broken;
	}

	public int getBlocksPlaced() {
		return blocks_placed;
	}

	public double getDistanceTravelled() {
		return distance_travelled;
	}

	public int getMobsKilled() {
		return mobs_killed;
	}

	public int getPlayerDeaths() {
		return deaths;
	}

	public void incrementBlocksBroken() {
		blocks_broken++;
	}

	public void incrementBlocksPlaced() {
		blocks_placed++;
	}

	public void incrementMovementDistance(double distance) {
		distance_travelled += distance;
	}

	public void incrementMinutesOnline() {
		minutes_online++;
	}

	public void incrementPoints(int points) {
		this.points += points;
		incrementTotalPoints(points);
	}

	public void incrementTotalPoints(int points) {
		this.total_points += points;
	}

	public void incrementMobsKilled(int mobs_killed) {
		this.mobs_killed += mobs_killed;
	}

	public void incrementPlayerDeaths() {
		deaths++;
	}

	public void saveStatsAsync() {
		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				long duration = System.currentTimeMillis();
				saveStats();
				PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
			}
		});
	}

	public void saveStats() {
		try {
			new QueryBuilder(MC_Custom_Core.getDBConnection(), "UPDATE `statistics` SET"
					+ " `last_join` = NOW(),"
					+ " `time_online` = `time_online` + ?,"
					+ " `blocks_broken` = `blocks_broken` + ?,"
					+ " `blocks_placed` = `blocks_placed` + ?,"
					+ " `distance_travelled` = `distance_travelled` + ?,"
					+ " `points` = `points` + ?,"
					+ " `mobs_killed` = `mobs_killed` + ?,"
					+ " `deaths` = `deaths` + ?"
					+ " WHERE `player_id` = ?")
					.setInt(minutes_online)
					.setInt(blocks_broken)
					.setInt(blocks_placed)
					.setDouble(distance_travelled)
					.setInt(points)
					.setInt(mobs_killed)
					.setInt(deaths)
					.setInt(player_id)
					.update();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void checkJoin() {
		try {
			long duration = System.currentTimeMillis();
			new QueryBuilder(MC_Custom_Core.getDBConnection(), "INSERT IGNORE INTO `statistics` (`player_id`, `first_join`) VALUES (?, NOW())")
					.setInt(player_id)
					.update();
			total_points = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `points` FROM `statistics` WHERE `player_id` = ?")
					.setInt(player_id)
					.executeQuery()
					.fetchOne(Integer.class);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}