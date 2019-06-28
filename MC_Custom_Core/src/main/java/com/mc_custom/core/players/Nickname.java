package com.mc_custom.core.players;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import java.sql.SQLException;
import java.util.Date;

public class Nickname implements Comparable<Nickname> {
	private String nickname;
	private Date set;
	private BasePlayer player;

	public Nickname(BasePlayer player) {
		this.player = player;
		fetchNickname();
	}

	public Nickname(BasePlayer player, String nickname, Date set) {
		this.player = player;
		this.nickname = nickname;
		this.set = set;
	}

	public String get() {
		return nickname;
	}

	public void set(String nickname) {
		this.nickname = nickname;
	}

	public Date getSetDate() {
		return set;
	}

	public int compareTo(Nickname nickname) {
		return getSetDate().compareTo(nickname.getSetDate());
	}

	public boolean equalsIgnoreCase(String nickname) {
		return this.nickname.equalsIgnoreCase(nickname);
	}

	public void saveNickname(final String new_nick) {
		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				if (new_nick == null || !checkNicknameUsed(new_nick)) {
					try {
						long duration = System.currentTimeMillis();
						new QueryBuilder(MC_Custom_Core.getDBConnection())
								.setQuery("INSERT INTO `nicknames` (`player_id`, `nickname`, `last_change`) VALUES (?, ?, NOW())"
										+ " ON DUPLICATE KEY UPDATE `nickname` = ?, `last_change` = NOW()")
								.setInt(player.getId())
								.setString(new_nick)
								.setString(new_nick)
								.update();
						PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
					}
					catch (SQLException e) {
						e.printStackTrace();
					}

					player.setPlayerName(new_nick);
					nickname = new_nick;
				}
				else {
					player.sendMessage(ChatColor.RED + "This nickname is already in use!");
				}
			}
		});
	}

	private void fetchNickname() {
		try {
			long duration = System.currentTimeMillis();
			nickname = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `nickname` FROM `nicknames` WHERE `player_id` = ? LIMIT 1")
					.setInt(player.getId())
					.executeQuery()
					.fetchOne(String.class);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean checkNicknameUsed(final String nickname) {
		try {
			long duration = System.currentTimeMillis();
			boolean is_used = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `player_id` FROM `nicknames` WHERE `nickname` = ? LIMIT 1")
					.setString(nickname)
					.executeQuery()
					.rowExists(1);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
			return is_used;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
