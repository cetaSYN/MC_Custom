package com.mc_custom.pds.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.WorldGuardHook;
import com.mc_custom.pds.MC_Custom_PDS;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class SchematicRegionCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"psave", "pschematic"};
	}

	@Override
	public String getRequiredPermissions() {
		return "pds.region.save";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException, InvalidArgumentException,
			NotOnlineException {
		if ((!command.fromPlayer() && command.getArgs().length < 2) || command.getArgs().length < 1) {
			throw new TooFewArgumentsException();
		}
		World world = null;
		String region_name = command.getArg(0);
		String world_name = command.getArgs().length >= 2
				? command.getArg(1)
				: command.fromPlayer()
				? command.getSenderPlayer().getWorld().getName()
				: null;

		if (command.getArgs().length >= 2 && world_name != null) {
			world = Bukkit.getWorld(world_name);
		}
		else if (command.fromPlayer()) {
			world = command.getSenderPlayer().getWorld();
		}
		if (world == null) {
			return new String[]{ChatColor.RED + "No such world \"" + world_name + "\""};
		}

		RegionManager region_manager = WorldGuardHook.WORLD_GUARD.getRegionManager(world);

		if (!region_manager.hasRegion(region_name)) {
			return new String[]{ChatColor.RED + "No such region \"" + region_name + "\""};
		}

		ProtectedRegion region = region_manager.getRegion(region_name);

		if (region == null) {
			return new String[]{ChatColor.RED + "No such region \"" + region_name + "\""};
		}
		return MC_Custom_PDS.regenerateRegion(region, world);
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Saves the content of a PDS region for regeneration",
				"Syntax: /psave <region_id> [world]"
		};
	}
}
