package com.mc_custom.core.utils;

import com.mc_custom.core.players.CorePlayer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Utils Class to help with logging and other various tasks
 */
public class Utils {

	/**
	 * Returns true if player has a negative potion effect applied.
	 * Otherwise, returns false.
	 */
	public static boolean hasNegativePotionEffect(Player player) {
		return player.hasPotionEffect(PotionEffectType.BLINDNESS) ||
				player.hasPotionEffect(PotionEffectType.CONFUSION) ||
				player.hasPotionEffect(PotionEffectType.HARM) ||
				player.hasPotionEffect(PotionEffectType.HUNGER) ||
				player.hasPotionEffect(PotionEffectType.POISON) ||
				player.hasPotionEffect(PotionEffectType.SLOW) ||
				player.hasPotionEffect(PotionEffectType.SLOW_DIGGING) ||
				player.hasPotionEffect(PotionEffectType.WEAKNESS);
	}

	public static String debugRunDuration(long millis) {
		return debugRunDuration(millis, 1);
	}

	/**
	 * Converts system milli-time to a nice eye-catching format for performance debugging.
	 * Do not perform offset.
	 * Output resembles: 4.5 seconds - !!!!
	 * exclaim marks = num seconds
	 */
	public static String debugRunDuration(long millis, int z) {
		double seconds = System.currentTimeMillis() - millis;
		seconds = seconds / 1000; //Convert to seconds
		String output = new DecimalFormat("0.000").format(seconds) + " seconds - ";
		int rounded_seconds = (int) Math.round(seconds);
		for (int i = 0; i < rounded_seconds; i++) {
			output += "!";
		}
		StackTraceElement caller_method = Thread.currentThread().getStackTrace()[2 + z];
		return caller_method.getMethodName() + "() - " + output;
	}

	/**
	 * Determines if a location is within a specified range at a specified location.
	 */
	public static boolean inCubicalRange(Location origin_location, Location test_location, int range) {
		if (origin_location.getX() + range >= test_location.getX()
				&& origin_location.getX() - range <= test_location.getX()) {
			if (origin_location.getY() + range >= test_location.getY()
					&& origin_location.getY() - range <= test_location.getY()) {
				if (origin_location.getZ() + range >= test_location.getZ()
						&& origin_location.getZ() - range <= test_location.getZ()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determines if a player can build at a given location
	 * TODO: Check with protection plugins (WorldGuard, GriefPrevention)
	 */
	public static boolean canBuild(Location location, CorePlayer player) {
		return true;
	}

	/**
	 * Determines if a location is within a specified range at a specified location.
	 */
	public static boolean in2DRange(Location origin_location, Location test_location, int range) {
		if (origin_location.getX() + range >= test_location.getX()
				&& origin_location.getX() - range <= test_location.getX()) {
			if (origin_location.getZ() + range >= test_location.getZ()
					&& origin_location.getZ() - range <= test_location.getZ()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a certain item is considered a crop
	 */
	public static boolean isCrop(Material item) {
		return item == Material.CROPS
				|| item == Material.POTATO
				|| item == Material.CARROT
				|| item == Material.NETHER_WARTS
				|| item == Material.PUMPKIN_STEM
				|| item == Material.MELON_STEM
				|| item == Material.COCOA
				|| item == Material.SUGAR_CANE_BLOCK
				|| item == Material.CACTUS;
	}

	/**
	 * Checks if a certain item is considered a tool
	 */
	public static boolean isTool(Material item) {
		return item == Material.WOOD_AXE ||
				item == Material.WOOD_HOE ||
				item == Material.WOOD_PICKAXE ||
				item == Material.WOOD_SPADE ||
				item == Material.STONE_AXE ||
				item == Material.STONE_HOE ||
				item == Material.STONE_PICKAXE ||
				item == Material.STONE_SPADE ||
				item == Material.IRON_AXE ||
				item == Material.IRON_HOE ||
				item == Material.IRON_PICKAXE ||
				item == Material.IRON_SPADE ||
				item == Material.GOLD_AXE ||
				item == Material.GOLD_HOE ||
				item == Material.GOLD_PICKAXE ||
				item == Material.GOLD_SPADE ||
				item == Material.DIAMOND_AXE ||
				item == Material.DIAMOND_HOE ||
				item == Material.DIAMOND_PICKAXE ||
				item == Material.DIAMOND_SPADE;
	}


	public static boolean isUnsafe(Material block) {
		return block == Material.LAVA ||
				block == Material.STATIONARY_LAVA ||
				block == Material.FIRE ||
				block == Material.BED_BLOCK;
	}

	public static Set<Material> getTransparentBlocks() {
		Set<Material> blocks = new HashSet<>();
		for (Material material : Material.values()) {
			if (material.isTransparent()) {
				blocks.add(material);
			}
		}
		return blocks;
	}

	/**
	 * @param chance Percent chance that the method will return true.
	 * @return true randomly.
	 */
	public static boolean randSuccess(double chance) {
		Random rand = new Random();
		return rand.nextDouble() < (chance / 100);
	}

	/**
	 * @param calendar Calendar to be formatted.
	 * @return The Calendar in the format: 2013-01-1 1:11:11
	 */
	public static String formatCalendar(Calendar calendar) {
		if (calendar == null) {
			return "Forever";
		}

		return calendar.get(Calendar.YEAR) + "-" +
				calendar.get(Calendar.MONTH) + "-" +
				calendar.get(Calendar.DAY_OF_MONTH) + " " +
				calendar.get(Calendar.HOUR_OF_DAY) + ":" +
				calendar.get(Calendar.MINUTE) + ":" +
				calendar.get(Calendar.SECOND);
	}

	public static Date addDays(int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, amount);
		return c.getTime();
	}

	/**
	 * @param milliseconds Number of milliseconds to be converted to DHMS format.
	 * @return A string with time in Days, Hours, Minutes and Seconds.
	 */
	public static String millisToDHMS(long milliseconds) {
		String message = "";

		int days = (int) TimeUnit.MILLISECONDS.toDays(milliseconds);
		int hours = (int) (TimeUnit.MILLISECONDS.toHours(milliseconds) - TimeUnit.DAYS.toHours(days));
		int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(milliseconds) - (TimeUnit.HOURS.toMinutes(hours) + TimeUnit.DAYS.toMinutes(days)));
		int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(milliseconds) - (TimeUnit.HOURS.toSeconds(hours) + TimeUnit.DAYS.toSeconds(days) + TimeUnit.MINUTES.toSeconds(minutes)));

		if (days > 0) {
			message += " " + days + "D ";
		}
		if (hours > 0) {
			message += " " + hours + "H ";
		}
		if (minutes > 0) {
			message += " " + minutes + "M ";
		}
		if (seconds > 0) {
			message += " " + seconds + "S ";
		}
		return message;
	}

	public static int getSafeY(Location tele_loc) { //TODO: Bedrock nether check. Unlikely players will run across this often.
		final int MAX_HEIGHT = tele_loc.getWorld().getMaxHeight();
		while (tele_loc.add(0, 1, 0).getBlock().getType() != Material.AIR
				&& tele_loc.add(0, 2, 0).getBlock().getType() != Material.AIR
				&& tele_loc.getBlockY() < MAX_HEIGHT) {
			tele_loc.setY(tele_loc.getY() + 1);
		}
		return tele_loc.getBlockY();
	}

	public static Location getSafeDestination(final Location loc) {
		if (loc == null || loc.getBlock() == null) {
			return null;
		}
		if (!isUnsafe(loc.getBlock().getType())) {
			return loc;
		}

		List<Location> area = new ArrayList<>();
		for (int x = -10; x <= 10; x++) {
			for (int z = -10; z <= 10; z++) {
				Location temp = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z);
				area.add(temp);
			}
		}

		// Sorts by smallest area (blocks closest to starting point)
		Collections.sort(area, new Comparator<Location>() {
			@Override
			public int compare(Location a, Location b) {
				return (a.getBlockX() * a.getBlockX() + a.getBlockZ() * a.getBlockZ())
						- (b.getBlockX() * b.getBlockX() + b.getBlockZ() * b.getBlockZ());
			}
		});

		for (Location safe_loc : area) {
			if (!isUnsafe(safe_loc.getBlock().getType())
					&& safe_loc.add(0, 1, 0).getBlock().getType() == Material.AIR
					&& safe_loc.add(0, 2, 0).getBlock().getType() == Material.AIR) {
				return safe_loc;
			}
		}

		//Sorry buddy, you're falling into lava
		return loc;
	}

	public static StackTraceElement getCallerTrace() {
		return Thread.currentThread().getStackTrace()[3];
	}

	public static StackTraceElement getCallerTrace(int level) {
		return Thread.currentThread().getStackTrace()[level];
	}

	/**
	 * Casts an Object to a generic type.
	 *
	 * @param o     The Object to cast
	 * @param clazz The Class to cast to
	 * @return A generic type
	 */
	public static <T> T cast(Object o, Class<T> clazz) {
		if (clazz == null || o == null) {
			return null;
		}
		try {
			// We want to use Integers, SQL tries to give us Longs
			if (o instanceof Long && !clazz.equals(Long.class)) {
				if (clazz.equals(Integer.class)) {
					return clazz.cast(((Long) o).intValue());
				}
				if (clazz.equals(Short.class)) {
					return clazz.cast(((Long) o).shortValue());
				}
			}
			return clazz.cast(o);
		}
		catch (ClassCastException e) {
			if (clazz.equals(String.class)) {
				return (T) o.toString();
			}
			throw e;
		}
	}

	public static <T> Type getGenericType(Class<T> clazz) {
		Type generic_super = clazz.getGenericSuperclass();
		if (generic_super instanceof ParameterizedType) {
			return ((ParameterizedType) generic_super).getActualTypeArguments()[0];
		}
		return Void.class;
	}

	public static boolean isSubclass(Class<?> subclass, Class<?> superclass) {
		Class check = subclass;
		while (check != null) {
			if (check.equals(superclass)) {
				return true;
			}
			check = check.getSuperclass();
		}
		return false;
	}

	public static <T extends Collection<?>, E, C extends Collection<E>> C castCollection(T collection, Class<E> type_class) {
		return castCollection(collection, type_class, (Class<C>) collection.getClass());
	}

	public static <T extends Collection<?>, E, C extends Collection<E>> C castCollection(T collection, Class<E> type_class, Class<C> set_class) {
		C cast;
		try {
			try {
				cast = set_class.getDeclaredConstructor(int.class).newInstance(collection.size());
			}
			catch (NoSuchMethodException e) {
				cast = set_class.getDeclaredConstructor().newInstance();
			}
		}
		catch (ReflectiveOperationException e) {
			return null;
		}
		for (Object element : collection) {
			cast.add(cast(element, type_class));
		}
		return cast;
	}

	public static EntityType getMob(String name) {
		switch (name.toLowerCase()) {
			case "xporb":
				return EntityType.EXPERIENCE_ORB;

			case "snowball":
				return EntityType.SNOWBALL;

			case "fireball":
				return EntityType.FIREBALL;

			case "smallfireball":
				return EntityType.SMALL_FIREBALL;

			case "witherskull":
				return EntityType.WITHER_SKULL;

			case "primedtnt":
				return EntityType.PRIMED_TNT;

			case "boat":
				return EntityType.BOAT;

			case "minecartrideable":
				return EntityType.MINECART;

			case "minecartchest":
				return EntityType.MINECART_CHEST;

			case "minecartfurnace":
				return EntityType.MINECART_FURNACE;

			case "minecarttnt":
				return EntityType.MINECART_TNT;

			case "minecarthopper":
				return EntityType.MINECART_HOPPER;

			case "minecartmobspawner":
				return EntityType.MINECART_MOB_SPAWNER;

			case "creeper":
				return EntityType.CREEPER;

			case "skeleton":
				return EntityType.SKELETON;

			case "spider":
				return EntityType.SPIDER;

			case "giant":
				return EntityType.GIANT;

			case "zombie":
				return EntityType.ZOMBIE;

			case "slime":
				return EntityType.SLIME;

			case "ghast":
				return EntityType.GHAST;

			case "pigzombie":
				return EntityType.PIG_ZOMBIE;

			case "enderman":
				return EntityType.ENDERMAN;

			case "cavespider":
				return EntityType.CAVE_SPIDER;

			case "silverfish":
				return EntityType.SILVERFISH;

			case "blaze":
				return EntityType.BLAZE;

			case "lavaslime":
				return EntityType.MAGMA_CUBE;

			case "endercustom4":
				return EntityType.ENDER_CUSTOM4;

			case "witherboss":
				return EntityType.WITHER;

			case "bat":
				return EntityType.BAT;

			case "witch":
				return EntityType.WITCH;

			case "endermite":
				return EntityType.ENDERMITE;

			case "guardian":
				return EntityType.GUARDIAN;

			case "pig":
				return EntityType.PIG;

			case "sheep":
				return EntityType.SHEEP;

			case "cow":
				return EntityType.COW;

			case "chicken":
				return EntityType.CHICKEN;

			case "squid":
				return EntityType.SQUID;

			case "wolf":
				return EntityType.WOLF;

			case "mushroomcow":
				return EntityType.MUSHROOM_COW;

			case "snowman":
				return EntityType.SNOWMAN;

			case "ozelot":
				return EntityType.OCELOT;

			case "villagergolem":
				return EntityType.IRON_GOLEM;

			case "entityhorse":
				return EntityType.HORSE;

			case "rabbit":
				return EntityType.RABBIT;

			case "villager":
				return EntityType.VILLAGER;

			case "endercrystal":
				return EntityType.ENDER_CRYSTAL;
			default:
				return null;
		}
	}
}
