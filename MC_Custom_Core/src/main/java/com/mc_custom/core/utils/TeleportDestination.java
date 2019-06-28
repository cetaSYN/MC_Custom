package com.mc_custom.core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Location;


public class TeleportDestination implements Comparable<TeleportDestination> {
	private final LocationWrapper location;
	private final String name;

	public TeleportDestination(Location location, String name) {
		this(new LocationWrapper(location), name);
	}

	public TeleportDestination(LocationWrapper location, String name) {
		this.location = location;
		this.name = name.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location.getLocation();
	}

	public int compareTo(TeleportDestination destination) {
		return this.name.compareTo(destination.name);
	}

	public JsonObject toJson() {
		Gson gson = new Gson();
		return new JsonParser().parse(gson.toJson(location)).getAsJsonObject();
	}
}
