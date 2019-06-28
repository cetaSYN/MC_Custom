package com.mc_custom.core.players;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.utils.LocationWrapper;
import com.mc_custom.core.utils.TeleportDestination;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Homes {

	private static final int MAX_HOME_COUNT = 3;
	private static final int JUNIOR_MAX_HOME_COUNT = 10;
	private ArrayList<TeleportDestination> homes = new ArrayList<>();
	private CorePlayer player;

	/**
	 * Creation is handled on Async Player joined.
	 *
	 * @param player The player
	 */
	public Homes(CorePlayer player) {
		this.player = player;
		JsonObject json_homes = fetchHomes();
		init(json_homes);
	}

	/**
	 * Creation is handled on Async Player joined.
	 *
	 * @param player The player
	 */
	public Homes(CorePlayer player, JsonObject json_homes) {
		this.player = player;
		init(json_homes);
	}

	public TeleportDestination get(String home_name) {
		for (TeleportDestination home : homes) {
			if (home.getName().equalsIgnoreCase(home_name)) {
				return home;
			}
		}
		return null;
	}

	public void add(TeleportDestination home) {
		final String home_name = home.getName();
		final JsonObject json_home = home.toJson();
		remove(home_name);
		homes.add(home);
		updateHomes(home_name, json_home, false);
	}

	public boolean remove(TeleportDestination home) {
		final String home_name = home.getName();
		final JsonObject json_home = home.toJson();
		if (remove(home_name)) {
			updateHomes(home_name, json_home, true);
			return true;
		}
		return false;
	}

	public boolean hasHome(String home_name) {
		for (TeleportDestination home : homes) {
			if (home.getName().equalsIgnoreCase(home_name)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<TeleportDestination> getHomes() {
		return homes;
	}

	/**
	 * Checks if the player can set homes based on permission and home count
	 *
	 * @return true if they can, false if they cannot
	 */
	public boolean canSetHome() {
		if (player.hasPermission("core.home.multiple")) {
			return true;
		}
		if (player.hasPermission("core.home.junior")) {
			return homes.size() <= JUNIOR_MAX_HOME_COUNT;
		}
		return homes.size() <= MAX_HOME_COUNT;
	}

	private void init(JsonObject json_homes) {
		if (json_homes == null) {
			createHomes();
		}

		//Init homes
		Gson gson = new Gson();
		if (json_homes != null) {
			for (Map.Entry<String, JsonElement> entry : json_homes.entrySet()) {
				LocationWrapper location = gson.fromJson(entry.getValue(), LocationWrapper.class);
				homes.add(new TeleportDestination(location, entry.getKey()));
			}
		}
	}

	private boolean remove(String home_name) {
		for (TeleportDestination home : homes) {
			if (home.getName().equalsIgnoreCase(home_name)) {
				return homes.remove(home);
			}
		}
		return false;
	}

	private void updateHomes(final String key, final JsonObject data, final boolean remove) {
		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				JsonObject json_object = fetchHomes();
				if (json_object == null) {
					json_object = new JsonObject();
				}
				if (remove && json_object.get(key) != null) {
					json_object.remove(key);
				}
				else {
					json_object.add(key, data);
				}
				setHomes(json_object);
			}
		});
	}

	private JsonObject fetchHomes() {
		try {
			String json_string = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `data` FROM `homes` WHERE `server_id` = ? AND `player_id` = ? LIMIT 1")
					.setInt(MC_Custom_Core.getServerId())
					.setInt(player.getId())
					.executeQuery()
					.fetchOne(String.class);
			JsonParser parser = new JsonParser();
			return (json_string != null) ? parser.parse(json_string).getAsJsonObject() : null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void createHomes() {
		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				try {
					new QueryBuilder(MC_Custom_Core.getDBConnection(), "INSERT INTO `homes` (`data`, `server_id`, `player_id`) VALUES (?, ?, ?)")
							.setString("{}")
							.setInt(MC_Custom_Core.getServerId())
							.setInt(player.getId())
							.update();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setHomes(final JsonObject data) {
		// Called asynchronously through updateHomes
		try {
			new QueryBuilder(MC_Custom_Core.getDBConnection(), "UPDATE `homes` SET `data` = ? WHERE `server_id` = ? AND `player_id` = ?")
					.setString(data.toString())
					.setInt(MC_Custom_Core.getServerId())
					.setInt(player.getId())
					.update();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}