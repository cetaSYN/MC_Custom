package com.mc_custom.core;

import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.handlers.CommandHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.PluginLogger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MC_CustomPlugin extends JavaPlugin {

	protected static Server server = Bukkit.getServer();

	public static void checkCore() {
		if (!pluginExists("MC_Custom_Core")) {
			PluginLogger.__("MC_Custom_Plugin").severe("Error linking to MC_Custom_Core! Shutting Down!");
			server.shutdown();
		}
	}

	public static boolean pluginExists(String plugin) {
		return server.getPluginManager().getPlugin(plugin) != null;
	}

	public static void callEvent(Event e) {
		server.getPluginManager().callEvent(e);
	}

	public static void broadcast(String s, String permission) {
		server.broadcast(s, permission);
	}

	public static void broadcastMessage(String m) {
		server.broadcastMessage(m);
	}

	public static Collection<? extends Player> getOnlineBukkitPlayers() {
		return server.getOnlinePlayers();
	}

	public static int getMaxPlayers() {
		return server.getMaxPlayers();
	}

	public static BukkitScheduler getScheduler() {
		return server.getScheduler();
	}

	public static void addRecipe(Recipe recipe) {
		server.addRecipe(recipe);
	}

	public static void shutdown() {
		server.shutdown();
	}

	public static List<World> getWorlds() {
		return server.getWorlds();
	}

	public static World getWorld(String arg) {
		return server.getWorld(arg);
	}

	public static void registerEvents(MC_CustomPlugin plugin, BaseListener listener) {
		server.getPluginManager().registerEvents(listener, plugin);
	}

	public static void registerEvents(MC_CustomPlugin plugin, String package_name) {
		PluginLogger.__(plugin.getName()).info("Scanning for listeners in " + plugin.getName());
		Reflections reflections = new Reflections(package_name, plugin.getClass().getClassLoader());
		Set<Class<? extends BaseListener>> listener_classes = reflections.getSubTypesOf(BaseListener.class);
		for (Class<? extends BaseListener> listener_class : listener_classes) {
			try {
				BaseListener listener;
				try {
					Constructor<? extends BaseListener> constructor = listener_class.getDeclaredConstructor(plugin.getClass());
					listener = constructor.newInstance(plugin);
				}
				catch(NoSuchMethodException e){
					Constructor<? extends BaseListener> constructor = listener_class.getDeclaredConstructor();
					listener = constructor.newInstance();
				}
				Bukkit.getPluginManager().registerEvents(listener, plugin);
				PluginLogger.__(plugin.getName()).info(listener_class.getSimpleName() + " added");
			}
			catch (ReflectiveOperationException e) {
				PluginLogger.__(plugin.getName()).warning("Could not add listener \"" + listener_class.getSimpleName() + "\": " + e.getMessage());
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
		if (name.equalsIgnoreCase("cmd-wrap") && sender instanceof BlockCommandSender) {
			String message = "";
			for (String arg : args) {
				message += " " + arg;
			}
			if (!message.trim().isEmpty()) {
				System.out.println(message);
				CommandHandler.getInstance().execute(new MC_CustomCommand(sender, args[0], Arrays.copyOfRange(args, 1, args.length)));
			}
			return true;
		}
		return false;
	}

}
