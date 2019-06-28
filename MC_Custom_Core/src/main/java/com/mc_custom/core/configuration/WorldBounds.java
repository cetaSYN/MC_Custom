package com.mc_custom.core.configuration;

import com.mc_custom.core.utils.PluginLogger;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldBounds {
	private Location max_point = null;
	private Location min_point = null;

	public WorldBounds(String world_name, JsonObject boundaries) {

		World world = Bukkit.getWorld(world_name);
		if (boundaries != null) {
			JsonObject json_world = boundaries.get(world_name).getAsJsonObject();
			if (json_world != null) {
				max_point = new Location(world, json_world.get("max_x").getAsDouble(),
						0.0, json_world.get("max_z").getAsDouble());

				min_point = new Location(world, json_world.get("min_x").getAsDouble(),
						0.0, json_world.get("min_z").getAsDouble());
				return;
			}
		}

		//Fallback
		PluginLogger.core().warning("No boundaries for " + world_name);
		PluginLogger.core().warning("Using default boundaries of -60k, 60k.");
		max_point = new Location(world, 60000, 0.0, 60000);
		min_point = new Location(world, -60000, 0.0, -60000);
	}

	public Location getMaxPoint() {
		return max_point;
	}

	public Location getMinPoint() {
		return min_point;
	}
}