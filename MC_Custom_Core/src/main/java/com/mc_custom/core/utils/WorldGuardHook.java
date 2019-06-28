package com.mc_custom.core.utils;

import com.mc_custom.core.MC_Custom_Core;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.BooleanFlag;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;

public class WorldGuardHook {

	// This flag takes a comma separated list of abilities. use "all" to block all abilities
	public static final SetFlag<String> ABILITY_FLAG = new SetFlag<>("blocked-abilities", RegionGroup.ALL, new StringFlag(null));
	// boolean flag either enabled or disabled
	public static final BooleanFlag PDS_FLAG = new BooleanFlag("pds-protected", RegionGroup.ALL);
	public static final WorldGuardPlugin WORLD_GUARD = WorldGuardPlugin.inst();

	private static Field flags_list_field;
	private static WorldGuardHook wg_hook = new WorldGuardHook();

	private WorldGuardHook() {
		try {
			flags_list_field = DefaultFlag.class.getDeclaredField("flagsList");
			flags_list_field.setAccessible(true);

			Field modifiers_field = Field.class.getDeclaredField("modifiers");
			modifiers_field.setAccessible(true);
			modifiers_field.set(flags_list_field, flags_list_field.getModifiers() & ~Modifier.FINAL);

			WORLD_GUARD.getRegionContainer().reload();
		}
		catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
		if (MC_Custom_Core.pluginExists("WorldGuard")) {
			addWorldGuardFlag(ABILITY_FLAG);
			addWorldGuardFlag(PDS_FLAG);
		}
	}

	public static WorldGuardHook getInstance() {
		if (wg_hook == null) {
			wg_hook = new WorldGuardHook();
		}
		return wg_hook;
	}

	public static boolean addWorldGuardFlag(Flag flag) {
		try {
			Flag<?>[] flags_list = (Flag<?>[]) flags_list_field.get(null);
			Flag<?>[] new_flag_list = Arrays.copyOf(flags_list, flags_list.length + 1);
			new_flag_list[new_flag_list.length - 1] = flag;
			flags_list_field.set(null, new_flag_list);
			WORLD_GUARD.getLogger().info("Flag \"" + flag.getName() + "\" added");
			return true;
		}
		catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean hasAbilityInRegion(Player player, String ability_name) {
		RegionManager region_manager = WorldGuardHook.WORLD_GUARD.getRegionContainer().get(player.getWorld());
		boolean ability_blocked = false;
		if (region_manager != null) {
			ApplicableRegionSet regions = region_manager.getApplicableRegions(player.getLocation());
			Set<String> blocked_abilities = regions.queryValue(WorldGuardHook.WORLD_GUARD.wrapPlayer(player), WorldGuardHook.ABILITY_FLAG);
			if (blocked_abilities != null) {
				for (String blocked_ability : blocked_abilities) {
					if (blocked_ability.equalsIgnoreCase(ability_name) || blocked_ability.equalsIgnoreCase("all")) {
						ability_blocked = true;
						break;
					}
				}
			}
		}
		return !ability_blocked;
	}
}
