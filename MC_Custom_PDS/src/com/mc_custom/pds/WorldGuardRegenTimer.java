package com.mc_custom.pds;

import com.mc_custom.core.timers.CoreTimer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.WorldGuardHook;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import org.bukkit.World;

public class WorldGuardRegenTimer extends CoreTimer {

	public WorldGuardRegenTimer() {
		super(1000 * 60 * 5);
	}

	@Override
	public void run() {
		for (World world : MC_Custom_PDS.getWorlds()) {
			RegionManager region_manager = WorldGuardHook.WORLD_GUARD.getRegionContainer().get(world);
			if (region_manager != null) {
				for (ProtectedRegion region: region_manager.getRegions().values()) {
					if (MC_Custom_PDS.isPDSRegion(region)) {
						for(String message : MC_Custom_PDS.regenerateRegion(region, world)){
							PluginLogger.pds().info(ChatColor.removeColor(message));
						}
					}
				}
			}
		}
	}
}
