package com.mc_custom.core.commands;

import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.lang.management.ManagementFactory;
import java.util.List;

public class StatusCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"serverstatus", "status", "stat"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.status";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 0) {
			throw new TooManyArgumentsException();
		}

		List<World> worlds = Bukkit.getWorlds();
		int chunks_loaded = 0;
		int entities = 0;
		int mobs = 0;
		for (World world : worlds) {
			chunks_loaded += world.getLoadedChunks().length;
			entities += world.getEntities().size();
			mobs += world.getLivingEntities().size();
		}

		return new String[]{
				"====================",
				"Uptime: " + Utils.millisToDHMS(System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime()),
				"Mem Max: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB",
				"Mem Alloc: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB",
				"Mem Used: " + ((Runtime.getRuntime().totalMemory() / 1024 / 1024) - (Runtime.getRuntime().freeMemory() / 1024 / 1024)) + "MB",
				"Mem Free: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB",
				"Worlds: " + worlds.size(),
				"Chunks: " + chunks_loaded,
				"Entities: " + entities,
				"Mobs: " + mobs,
				"===================="
		};
	}

	@Override
	public String[] getHelp() {
		return new String[]{};
	}
}