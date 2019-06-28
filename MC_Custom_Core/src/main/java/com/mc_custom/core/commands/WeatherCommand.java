package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.*;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PlayerWeatherType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WeatherCommand extends BaseCommand {
	@Override
	public String[] getCommandNames() {
		return new String[]{"weather", "pweather"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.weather";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException, NotImplementedException, InvalidArgumentException, PlayerOnlyException, ConsoleOnlyException, NotOnlineException {
		PlayerWeatherType type;
		int arg_pos = 0;
		switch (command.getCommand().toLowerCase()) {
			case "weather":
				World world;
				Integer ticks = null;
				if (command.fromConsole()) {
					if (command.getArgs().length < arg_pos + 1) {
						throw new TooFewArgumentsException();
					}
					world = Bukkit.getServer().getWorld(command.getArg(arg_pos++));
					if (world == null) {
						return new String[]{"No such world: " + command.getArg(arg_pos - 1)};
					}
				}
				else {
					Player player = command.getSenderPlayer();
					if (command.getArgs().length < arg_pos + 1) {
						world = player.getWorld();
					}
					else {
						type = getTypeByName(command.getArg(arg_pos++));
						if (type != null) {
							world = player.getWorld();
							arg_pos--;
						}
						else {
							world = Bukkit.getServer().getWorld(command.getArg(arg_pos - 1));
							if (world == null) {
								return new String[]{"No such world: " + command.getArg(arg_pos - 1)};
							}
						}
					}
				}
				if (command.getArgs().length < arg_pos + 1) {
					return new String[]{"The weather for world \"" + world.getName() + "\" is " + getWorldWeather(world).name()};
				}
				else {
					type = getTypeByName(command.getArg(arg_pos++));
					if (type == null) {
						return new String[]{"Unknown weather type: " + command.getArg(arg_pos - 1)};
					}
				}
				if (command.getArgs().length > arg_pos) {
					try {
						ticks = Integer.parseInt(command.getArg(arg_pos++));
					}
					catch (NumberFormatException e) {
						return new String[]{"Invalid number: " + command.getArg(arg_pos - 1)};
					}
				}
				switch (type) {
					case SERVER:
						return new String[]{"Invalid weather type"};
					case CLEAR:
						world.setThundering(false);
						world.setStorm(false);
						if (ticks != null) {
							world.setWeatherDuration(ticks);
						}
						break;
					case RAIN:
						world.setThundering(false);
						world.setStorm(true);
						if (ticks != null) {
							world.setWeatherDuration(ticks);
						}
						break;
					case STORM:
						world.setThundering(true);
						world.setStorm(true);
						if (ticks != null) {
							world.setWeatherDuration(ticks);
							world.setThunderDuration(ticks);
						}
						break;
				}
				return new String[]{"Weather for world \"" + world.getName() + "\" has been set to " + type.name()
				                    + (ticks != null ? " for " + ticks + " ticks" : "")};
			case "pweather":
				CorePlayer player;
				try {
					if (command.fromConsole()) {
						if (command.getArgs().length < arg_pos + 1) {
							throw new TooFewArgumentsException();
						}
						player = MC_Custom_Core.getPlayerHandler().getPlayerByAnyNameFuzzy(command.getArg(arg_pos++));
						if (command.getArgs().length < arg_pos + 1) {
							type = player.getPlayerWeather();
							return new String[]{player.getName() + ChatColor.AQUA + "'s weather is " + type.name()};
						}
						type = getTypeByName(command.getArg(arg_pos++));
						if (type == null) {
							return new String[]{"Unknown weather type: " + command.getArg(arg_pos - 1)};
						}
					}
					else {
						if (command.getArgs().length < arg_pos + 1) {
							player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
							type = player.getPlayerWeather();
							return new String[]{"Your weather is " + type.name()};
						}
						type = getTypeByName(command.getArg(arg_pos++));
						if (type != null) {
							player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
							player.setPlayerWeather(type);
							return new String[]{"Set your weather to " + type.name()};
						}
						player = MC_Custom_Core.getPlayerHandler().getPlayerByAnyNameFuzzy(command.getArg(arg_pos - 1));
						if (command.getArgs().length < arg_pos + 1) {
							type = player.getPlayerWeather();
							return new String[]{player.getName() + ChatColor.AQUA + "'s weather is " + type.name()};
						}
						type = getTypeByName(command.getArg(arg_pos++));
						if (type == null) {
							return new String[]{"Unknown weather type: " + command.getArg(arg_pos - 1)};
						}
					}
				}
				catch (NotOnlineException e) {
					return new String[]{"Player is not online"};
				}
				player.setPlayerWeather(type);
				return new String[]{"Set " + player.getName() + ChatColor.AQUA + "'s weather to " + type.name()};
		}
		return new String[]{};
	}

	@Override
	public String[] getHelp() {
		return new String[0];
	}

	private PlayerWeatherType getWorldWeather(World world) {
		if (world.isThundering()) {
			return PlayerWeatherType.STORM;
		}
		if (world.hasStorm()) {
			return PlayerWeatherType.RAIN;
		}
		return PlayerWeatherType.CLEAR;
	}

	private PlayerWeatherType getTypeByName(String name) {
		switch (name.toLowerCase()) {
			case "clear":
			case "sun":
			case "sunny":
				return PlayerWeatherType.CLEAR;
			case "rain":
			case "rainy":
				return PlayerWeatherType.RAIN;
			case "storm":
			case "stormy":
			case "lightning":
			case "thunder":
			case "thundering":
				return PlayerWeatherType.STORM;
			case "none":
			case "reset":
			case "server":
				return PlayerWeatherType.SERVER;
		}
		return null;
	}
}
