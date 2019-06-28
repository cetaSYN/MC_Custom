package com.mc_custom.core.configuration;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.Column;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.handlers.SpawnHandler;
import com.mc_custom.core.handlers.WarpHandler;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.LocationWrapper;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.TeleportDestination;
import com.mc_custom.core.utils.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

	private static Configuration configuration = new Configuration();

	private HashMap<String, WorldBounds> world_bounds = new HashMap<>();
	private SpawnHandler spawns = new SpawnHandler();
	private WarpHandler warps = new WarpHandler();
	private AnnouncementsHandler announcements = new AnnouncementsHandler();

	// To be used to initialize each configuration node
	// Initialize the map to a value a little higher than the number of nodes so it doesn't need to resize
	private HashMap<String, String> nodes;
	private Gson gson = new Gson();

	/**
	 * Configuration Constructor
	 * Creation of a new Configuration will cause new values to be loaded.
	 */
	private Configuration() {
		//fetches the nodes from the database in a single query
		getNodes();
		initWorldBounds();
		initSpawns();
		initGameRules();
		initWarps();
		initAnnouncements();
		PluginLogger.core().info("Configurations initialized");
		// at this point we don't need this we can clear it
		nodes.clear();
	}

	public static Configuration getInstance() {
		return configuration;
	}

	public static void newInstance() {
		PluginLogger.core().info("Reloading configurations.");
		configuration = new Configuration();
	}

	public static int getServerId() {
		try {
			long duration = System.currentTimeMillis();
			Column<Integer> server_id = new Column<>(Integer.class);
			new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `id` FROM `servers` WHERE `name` = ? LIMIT 1")
					.setString(Bukkit.getServerName())
					.executeQuery()
					.fetchOne(server_id);
			if (server_id.getValue() == 0) {
				System.out.println(ChatColor.RED + "===========No server id found.===========");
				System.out.println(ChatColor.RED + "===========Shit might break.============");
				System.out.println(ChatColor.RED + "SERVER NAME IS: " + Bukkit.getServerName());
			}
			PluginLogger.database().info(Utils.debugRunDuration(duration));
			return server_id.getValue();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(ChatColor.RED + "===========No server id found.===========");
			System.out.println(ChatColor.RED + "===========Shit might break.============");
			System.out.println(ChatColor.RED + "SERVER NAME IS: " + Bukkit.getServerName());
			return 0;
		}
	}

	/**
	 * @return Returns WorldBounds object for specified world.
	 */
	public WorldBounds getWorldBoundaries(String world_name) {
		return world_bounds.get(world_name);
	}

	public SpawnHandler getSpawnHandler() {
		return spawns;
	}

	public WarpHandler getWarpHandler() {
		return warps;
	}

	public void updateNode(final String node, final String key, final JsonObject value) {
		updateNode(node, key, value, false);
	}

	public void updateNode(final String node, final String key, final JsonObject value, boolean remove) {
		JsonObject json_object = fetchNode(node);
		if (json_object == null) {
			json_object = new JsonObject();
		}
		if (remove && json_object.get(key) != null) {
			json_object.remove(key);
		}
		else {
			json_object.add(key, value);
		}
		final JsonObject json_value = json_object;
		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				try {
					long duration = System.currentTimeMillis();
					new QueryBuilder(MC_Custom_Core.getDBConnection(), "INSERT INTO `configuration` (`server`, `node`, `value`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `value` = ?")
							.setString(Bukkit.getServerId())
							.setString(node)
							.setString(json_value.toString())
							.setString(json_value.toString())
							.update();
					PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JsonObject fetchNode(final String node) {
		try {
			long duration = System.currentTimeMillis();
			String value = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `value` FROM `configuration` WHERE `node` = ?")
					.setString(node)
					.executeQuery()
					.fetchOne(String.class);
			PluginLogger.database().info(Utils.debugRunDuration(duration));

			JsonParser parser = new JsonParser();
			JsonObject json_value = new JsonObject();
			if (value != null) {
				json_value = parser.parse(value).getAsJsonObject();
			}
			return json_value;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return new JsonObject();
	}

	private void getNodes() {
		try {
			long duration = System.currentTimeMillis();
			Column<String> value = new Column<>(String.class);
			Column<String> node = new Column<>(String.class);
			new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `value`, `node` FROM `configuration` WHERE `server` = ?")
					.setString(Bukkit.getServerId())
					.executeQuery()
					.fetchAll(value, node);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
			nodes = new HashMap<>(value.size());
			for (int i = 0; i < value.size(); i++) {
				nodes.put(node.getNext(), value.getNext());
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initWorldBounds() {
		JsonObject boundaries = getNode("boundaries");
		for (World world : Bukkit.getWorlds()) {
			WorldBounds bounds = new WorldBounds(world.getName(), boundaries);
			world_bounds.put(world.getName(), bounds);
		}
	}

	private void initSpawns() { // Comments stripped
		JsonObject json_spawns = getNode("spawns");
		if (json_spawns != null) {
			JsonElement first = json_spawns.get("new");
			if (first != null) {
				LocationWrapper location = gson.fromJson(first, LocationWrapper.class);
				spawns.setFirstSpawn(new TeleportDestination(location, "new"));
			}

			for (Map.Entry<String, JsonElement> entry : json_spawns.entrySet()) {
				LocationWrapper location = gson.fromJson(entry.getValue(), LocationWrapper.class);
				spawns.add(new TeleportDestination(location, entry.getKey()));
			}
		}
	}

	// {"key1":"value1","key2":"value2","key3":"value3"}
	private void initGameRules() {
		JsonObject json_gamerule = getNode("gamerule");
		if (json_gamerule != null) {
			for (World world : Bukkit.getWorlds()) {
				for (Map.Entry<String, JsonElement> entry : json_gamerule.entrySet()) {
					world.setGameRuleValue(entry.getKey(), entry.getValue().getAsString());
				}
			}
		}
	}

	private void initWarps() { // Comments stripped
		JsonObject json_warps = getNode("warps");
		if (json_warps != null) {
			for (Map.Entry<String, JsonElement> entry : json_warps.entrySet()) {
				LocationWrapper location = gson.fromJson(entry.getValue(), LocationWrapper.class);
				warps.add(new TeleportDestination(location, entry.getKey()));
			}
		}
	}

	private void initAnnouncements() {
		JsonObject json_announcements = getNode("announcements");
		if (json_announcements != null) {
			for (Map.Entry<String, JsonElement> entry : json_announcements.entrySet()) {
				Announcement announcement = gson.fromJson(entry.getValue(), Announcement.class);
				announcements.addAnnouncement(announcement);
			}
		}
	}

	/**
	 * Gets the value associated with the specified node.
	 * The value is converted to a JsonObject before its returned
	 *
	 * @param node The node to get
	 * @return A JsonObject of the value
	 */
	private JsonObject getNode(final String node) {
		String json_string = nodes.get(node);
		JsonParser parser = new JsonParser();
		if (json_string != null) {
			JsonObject json_result = parser.parse(json_string).getAsJsonObject();
			if (json_result != null) {
				System.out.println(json_result.toString());
			}
			else {
				System.out.println("No configuration found.");
			}
			return json_result;
		}
		return null;
	}
}
