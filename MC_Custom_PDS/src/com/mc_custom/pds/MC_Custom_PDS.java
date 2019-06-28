package com.mc_custom.pds;

import com.mc_custom.core.MC_CustomPlugin;
import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.handlers.CommandHandler;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.WorldGuardHook;
import com.mc_custom.pds.players.PDSPlayer;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.world.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class MC_Custom_PDS extends MC_CustomPlugin {

	public static final int MAX_REGEN_VOLUME = 1000;
	public static final int FAST_MODE_THRESHHOLD = 10000;
	public static final SchematicFormat SCHEMATIC_FORMAT = SchematicFormat.getFormat("mcedit");

	private static File region_file;
	private static MC_Custom_PDS plugin;
	private static PlayerHandler<PDSPlayer> player_handler = new PlayerHandler<>();


	@Override
	public void onEnable() {
		checkCore();
		checkPunishments();
		plugin = this;
		// Init datafile
		region_file = new File(getDataFolder() + "/regions");
		region_file.mkdirs(); //makes the directory if it doesn't already exist

		// Register Listeners
		registerEvents(this, "com.mc_custom.pds.listeners");

		// Register Commands
		CommandHandler.getInstance().addCommands(this, "com.mc_custom.pds.commands");

		// Register Timer
		MC_Custom_Core.getTimerHandler().submitTask(new WorldGuardRegenTimer());
	}

	public static PlayerHandler<PDSPlayer> getPlayerHandler() {
		return player_handler;
	}

	public static MC_Custom_PDS getInstance() {
		return plugin;
	}

	public static void runTaskAsynchronously(Runnable runnable) {
		getScheduler().runTaskAsynchronously(plugin, runnable);
	}

	public static File getRegionFile() {
		return region_file;
	}

	private void checkPunishments() {
		if (!pluginExists("MC_Custom_Punishments")) {
			PluginLogger.pds().severe("Error linking to MC_Custom_Punishments! Shutting Down!");
			server.shutdown();
		}
	}

	public static boolean isPDSRegion(ProtectedRegion region) {
		Boolean flag = region.getFlag(WorldGuardHook.PDS_FLAG);
		return flag != null && flag;
	}

	public static int getMaxRegenVolume() {
		return MAX_REGEN_VOLUME;
	}

	public static int getFastModeThreshhold() {
		return FAST_MODE_THRESHHOLD;
	}

	// Moved here to reduce code redundancies
	public static String[] regenerateRegion(ProtectedRegion region, World world) {
		if (!isPDSRegion(region)) {
			return new String[]{ChatColor.RED + "The selected region is not PDS protected"};
		}
		try {
			File file = new File(getRegionFile() + "/" + region.getId() + ".schematic");
			//CuboidClipboard is deprecated in favor of Clipboard, however don't see a way to load a schematic into that class
			CuboidClipboard clipboard = SCHEMATIC_FORMAT.load(file);
			// Another interesting line of code becuase of the way getEditSession was deprecated
			EditSession edit_session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(
					(com.sk89q.worldedit.world.World) BukkitUtil.getLocalWorld(world),
					getMaxRegenVolume());
			if (clipboard.getLength() * clipboard.getWidth() * clipboard.getHeight() > getFastModeThreshhold()) {
				edit_session.setFastMode(true);
			}
			clipboard.place(edit_session, region.getMinimumPoint(), false);
			return new String[]{"Successfully regenerated PDS region \"" + region.getId() + "\""
					+ (edit_session.hasFastMode() ? " using fast mode" : "")};
		}
		catch (DataException e) {
			return new String[]{ChatColor.RED + "Invalid or corrupt schematic file: " + region.getId()
					+ ".schematic\""};
		}
		catch (IOException e) {
			return new String[]{ChatColor.RED + "No regeneration schematic found for PDS region \""
					+ region.getId() + "\""};
		}
		catch (MaxChangedBlocksException e) {
			return new String[]{
					ChatColor.RED + "Regeneration schematic \"" + region.getId() + ".schematic\" exceeds the maximum allowed volume",
					ChatColor.RED + "(Max " + getMaxRegenVolume() + " blocks)"
			};
		}
	}
}
