package com.mc_custom.pds.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.WorldGuardHook;
import com.mc_custom.pds.MC_Custom_PDS;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ListRegionsCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"plist"};
	}

	@Override
	public String getRequiredPermissions() {
		return "pds.region.list";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			InvalidArgumentException, PlayerOnlyException, ConsoleOnlyException, NotOnlineException {
		List<World> worlds = new ArrayList<>();
		if (command.getArgs().length >= 1) {
			World world = MC_Custom_PDS.getWorld(command.getArg(0));
			if (world == null) {
				return new String[]{ChatColor.RED + "No such world \"" + command.getArg(0) + "\""};
			}
			worlds.add(world);
		}
		else {
			worlds = MC_Custom_PDS.getWorlds();
		}
		List<String> region_names = new ArrayList<>();
		for (World world : worlds) {
			RegionManager region_manager = WorldGuardHook.WORLD_GUARD.getRegionManager(world);
			for (ProtectedRegion region : region_manager.getRegions().values()) {
				if (MC_Custom_PDS.isPDSRegion(region)) {
					region_names.add((region_names.size() + 1) + ": " + region.getId() + " " + region.getMinimumPoint() + " -> " + region.getMaximumPoint());
				}
			}
		}
		return region_names.size() > 0 ? region_names.toArray(new String[region_names.size()]) : new String[]{"There are no PDS regions defined"};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Lists all regions protected by PDS",
				"Syntax: /plist [world]"
		};
	}
}
