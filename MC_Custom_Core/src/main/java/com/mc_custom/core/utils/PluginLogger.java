package com.mc_custom.core.utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;


public class PluginLogger {

	public static final PluginLogger GLOBAL = new PluginLogger();
	public static final PluginLogger CLASSES = new PluginLogger("MC_Custom_CLasses");
	public static final PluginLogger PUNISHMENTS = new PluginLogger("MC_Custom_Punishments");
	public static final PluginLogger DATABASE = new PluginLogger("MC_Custom_Database");
	public static final PluginLogger PDS = new PluginLogger("MC_Custom_PDS");

	private final Logger logger = Bukkit.getLogger();
	private String name;

	protected PluginLogger() {
		this("MC_Custom_Core");
	}

	protected PluginLogger(String name) {
		this.name = name;
		logger.setLevel(Level.ALL);
	}

	public static PluginLogger plugin() {
		return GLOBAL;
	}

	public static PluginLogger core() {
		return GLOBAL;
	}

	public static PluginLogger classes() {
		return CLASSES;
	}

	public static PluginLogger punishments() {
		return PUNISHMENTS;
	}

	public static PluginLogger database() {
		return DATABASE;
	}

	public static PluginLogger pds() {
		return PDS;
	}

	public static PluginLogger __(String name) {
		return new PluginLogger(name);
	}

	public void severe(String message) {
		message = "[" + name + "] " + message;
		logger.severe(message);
	}

	public void warning(String message) {
		message = "[" + name + "] " + message;
		logger.warning(message);
	}

	public void info(String message) {
		message = "[" + name + "] " + message;
		logger.info(message);
	}
}
