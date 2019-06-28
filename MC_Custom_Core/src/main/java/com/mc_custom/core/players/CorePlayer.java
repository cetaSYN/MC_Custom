package com.mc_custom.core.players;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.Column;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.timers.CoreTimer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents a player.
 * Holds all attributes of the player.
 */
public class CorePlayer extends BasePlayer {

	private InetAddress ip_address;
	private PlayerStatistics statistics;
	private Homes homes;
	private final Flight flight;

	private Permissions permissions;
	private PermissionGroup primary_group;
	private CorePlayer last_communication;
	private CorePlayer last_tp_request;
	private Location back_location;
	private boolean last_tp_request_direction = true;
	private boolean allow_tp_request = true;
	private boolean afk_status;
	private boolean god_mode;
	private List<String> powertools = new ArrayList<>();
	private AFKTimer afk_timer;

	/**
	 * @param name Player's name that new CorePlayer corresponds to.
	 * @param uuid Player's uuid that new CorePlayer corresponds to.
	 */
	public CorePlayer(String name, UUID uuid, InetAddress ip_address) {
		super(name, uuid);
		this.ip_address = ip_address;
		//Get ID
		getPlayerID();
		//Update last ip address
		setLastAddress();
		//Fetch player info from DB and initializes homes,nickname,stats
		initPlayer();
		//Get Perms
		permissions = new Permissions(this);
		flight = new Flight();
	}

	/**
	 * Returns the player's ranking of classes points.
	 *
	 * @return int the current ranking of the player
	 */
	public int getRank() {
		return RankCache.getInstance().getRank(getId());
	}

	/**
	 * Returns PermissionsHandler object.
	 *
	 * @return permissions
	 */
	public Permissions getPermissions() {
		return permissions;
	}

	/**
	 * Returns HomeHandler object.
	 *
	 * @return homes
	 */
	public Homes getHomes() {
		return homes;
	}

	/**
	 * Returns FlightHandler object.
	 *
	 * @return flight
	 */
	public Flight getFlightHandler() {
		return flight;
	}

	/**
	 * Returns PlayerStatistics object.
	 *
	 * @return statistics
	 */
	public PlayerStatistics getPlayerStatistics() {
		return statistics;
	}

	public boolean inGroup(String name) {
		return permissions.getGroup(name) != null;
	}

	public PermissionGroup getPrimaryGroup() {
		return primary_group;
	}

	public void setPrimaryGroup(PermissionGroup primary_group) {
		this.primary_group = primary_group;
	}

	public Collection<PermissionGroup> getGroups() {
		return permissions.getGroups();
	}

	public PermissionAttachment addAttachment() {
		return addAttachment(MC_Custom_Core.getInstance());
	}

	public CorePlayer getLastCommunicationFrom() {
		return last_communication;
	}

	public void setLastCommunicationFrom(CorePlayer player) {
		this.last_communication = player;
	}

	public boolean allowTeleportRequest() {
		return allow_tp_request;
	}

	public void setAllowTeleportRequest(boolean allow_tp_request) {
		this.allow_tp_request = allow_tp_request;
	}

	public CorePlayer getLastTeleportRequest() {
		return last_tp_request;
	}

	public void setLastTeleportRequest(CorePlayer player) {
		this.last_tp_request = player;
	}

	public boolean getLastTeleportRequestDirection() {
		return last_tp_request_direction;
	}

	public void setLastTeleportRequestDirection(boolean direction) {
		this.last_tp_request_direction = direction;
	}

	public void sendMessageTo(CorePlayer player, String message) {
		this.last_communication = player;
		player.setLastCommunicationFrom(this);

		player.sendMessage(ChatColor.BLUE + "[" + getName() + ChatColor.BLUE + "] -> [Me]:" + ChatColor.RESET + message);
		PluginLogger.core().info("msg " + getPlayerName() + " > " + player.getPlayerName() + ":" + message);
	}

	public Location getBackLocation() {
		return this.back_location;
	}

	public void setBackLocation(Location back_location) {
		this.back_location = back_location;
	}

	public void teleport(CorePlayer cp) {
		teleport(cp.getLocation());
	}

	public boolean teleport(Location loc) {
		if (isInsideVehicle()) {
			leaveVehicle();
		}
		if (getLocation() != null) {
			this.back_location = Utils.getSafeDestination(getLocation());
		}
		else {
			PluginLogger.core().warning("CorePlayer Line 197 returned null.");
		}
		if (loc != null) {
			loc = Utils.getSafeDestination(loc);
		}
		else {
			PluginLogger.core().warning("CorePlayer Line 203 returned null.");
		}
		return super.teleport(loc);
	}

	public boolean isAFK() {
		return afk_status;
	}

	public void setAFK(boolean afk_status) {
		this.afk_status = afk_status;
		String name = getName();
		if (afk_status) {
			name = "AFK " + name;
			if (name.length() >= 16) {
				setPlayerListName(name.substring(0, 15));
			}
			else {
				setPlayerListName(name);
			}
		}
		else {
			afk_timer.resetTimer();
			if (name.length() >= 16) {
				setPlayerListName(name.substring(0, 15));
			}
			else {
				setPlayerListName(name);
			}
		}
	}

	public void give(ItemStack item_stack) {
		getInventory().addItem(item_stack);
	}

	public boolean getGodMode() {
		return god_mode;
	}

	public void toggleGodMode() {
		this.god_mode = !god_mode;
	}

	/**
	 * @return True if player should ignore the max-player cap.
	 */
	public boolean ignoresPlayerCap() {
		return hasPermission("core.ignore_cap");
	}

	public void addPowertool(Material tool, String command) {
		String powertool = tool.name() + ";" + command;
		// Ensure that that there are no duplicate tools
		removePowertool(tool);
		powertools.add(powertool);
	}

	public void removePowertool(Material tool) {
		String removed_tool = null;
		for (String powertool : powertools) {
			String material_name = powertool.split(";", 2)[0];
			if (tool.name().equals(material_name)) {
				removed_tool = powertool;
				break;
			}
		}
		powertools.remove(removed_tool);
	}

	public String[] getPowertool(Material tool) {
		for (String powertool : powertools) {
			String[] tool_info = powertool.split(";", 2);
			if (tool.name().equals(tool_info[0])) {
				return tool_info[1].split(",");
			}
		}
		return null;
	}

	private void initPlayer() {
		try {
			long duration = System.currentTimeMillis();
			Column<String> home_data = new Column<>(String.class);
			Column<String> nickname = new Column<>(String.class);
			Column<Timestamp> last_change = new Column<>(Timestamp.class);
			Column<Integer> points = new Column<>(Integer.class);

			new QueryBuilder(MC_Custom_Core.getDBConnection())
					.setQuery("SELECT `homes`.`data`, `nicknames`.`nickname`, `nicknames`.`last_change`, `statistics`.`points`"
							+ " FROM `players`"
							+ " INNER JOIN `nicknames` ON `players`.`id` = `nicknames`.`player_id`"
							+ " INNER JOIN `statistics` ON `players`.`id` = `statistics`.`player_id`"
							+ " INNER JOIN `homes` ON `players`.`id` = `homes`.`player_id`"
							+ " WHERE `players`.`id` = ? AND `homes`.`server_id` = ?")
					.setInt(getId())
					.setInt(MC_Custom_Core.getServerId())
					.executeQuery()
					.fetchOne(home_data, nickname, last_change, points);
			PluginLogger.database().info(Utils.debugRunDuration(duration));

			JsonParser parser = new JsonParser();
			JsonObject json_homes;

			//Get Homes
			if (home_data.getValue() != null) {
				json_homes = parser.parse(home_data.getValue()).getAsJsonObject();
				homes = new Homes(this, json_homes);
			}
			else {
				homes = new Homes(this);
			}

			//Get Nick
			if (nickname.getValue() != null && last_change.getValue() != null) {
				this.nickname = new Nickname(this, nickname.getValue(), new Date(last_change.getValue().getTime()));
			}
			else {
				this.nickname = new Nickname(this);
			}

			//Get Stats
			if (points.getValue() != null) {
				statistics = new PlayerStatistics(getId(), points.getValue());
			}
			else {
				statistics = new PlayerStatistics(getId());
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changes a player's group.
	 * NOTE: Only supports changing global-groups at this time.
	 *
	 * @param group_id The ID of the group to change to.
	 */
	public void changePlayerGroup(final int group_id) {
		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				try {
					long duration = System.currentTimeMillis();
					new QueryBuilder(MC_Custom_Core.getDBConnection(), "UPDATE `player_groups` SET `group_id` = ? WHERE `player_id` = ?")
							.setInt(group_id)
							.setInt(getId())
							.update();
					PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Add player to a group.
	 *
	 * @param group_id The ID of the group to change to.
	 */
	public void addPlayerGroup(final int group_id) {
		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				try {
					long duration = System.currentTimeMillis();
					new QueryBuilder(MC_Custom_Core.getDBConnection(), "INSERT INTO `player_groups` (`group_id`, `player_id`, `server_id`) VALUES (?, ?, ?)")
							.setInt(group_id)
							.setInt(getId())
							.setInt(MC_Custom_Core.getServerId())
							.update();
					PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void getPlayerID() {
		try {
			long duration = System.currentTimeMillis();
			Integer player_id;
			String clean_uuid = this.uuid.toString().replace("-", "");
			new QueryBuilder(MC_Custom_Core.getDBConnection())
					.setQuery("INSERT INTO `players` (`name`, `uuid`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `name` = ?")
					.setString(this.player_name)
					.setString(clean_uuid)
					.setString(this.player_name)
					.update();
			player_id = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `id` FROM `players` WHERE `uuid` = ? LIMIT 1")
					.setString(clean_uuid)
					.executeQuery()
					.fetchOne(Integer.class);
			if (player_id != null && player_id != 0) {
				setId(player_id);
			}
			PluginLogger.database().info(Utils.debugRunDuration(duration));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getLastAddress() {
		try {
			long duration = System.currentTimeMillis();
			String last_address = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `last_address` FROM `players` WHERE `id` = ?")
					.setInt(getId())
					.executeQuery()
					.fetchOne(String.class);
			if (last_address != null) {
				ip_address = InetAddress.getByName(last_address);
			}
			PluginLogger.database().info(Utils.debugRunDuration(duration));
		}
		catch (SQLException | UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void setLastAddress() {
		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				try {
					long duration = System.currentTimeMillis();
					new QueryBuilder(MC_Custom_Core.getDBConnection(), "UPDATE `players` SET `last_address` = ?  WHERE `id` = ?")
							.setString(ip_address.getHostAddress())
							.setInt(getId())
							.update();
					PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AFKTimer getAFKTimer() {
		return afk_timer;
	}

	public void createAFKTimer() {
		if (afk_timer != null) {
			return;
		}
		afk_timer = new AFKTimer(300000);
		if (!PermissionUtils.hasWildcardPermission(getPlayer(), "core.ignoreafk")) {
			MC_Custom_Core.getTimerHandler().submitTask(afk_timer);
		}
	}

	public class AFKTimer extends CoreTimer {
		boolean do_kick = false;

		public AFKTimer(long repeat_delay) {
			super(repeat_delay);
			PluginLogger.core().info("AFK Timer created");
		}

		public void resetTimer() {
			super.resetTime();
			do_kick = false;
		}

		public void beginTimer() {
			do_kick = true;
		}

		public void cancelTimer() {
			MC_Custom_Core.getTimerHandler().cancelTask(this);
			PluginLogger.core().info("AFK Timer was cancelled");
		}

		@Override
		public void run() {
			if (do_kick) {
				cancelTimer();
				MC_Custom_Core.getPlayerHandler().playerQuit(getPlayer());
				getPlayer().kickPlayer(ChatColor.AQUA + "You have been AFK for too long");
			}
			else {
				getPlayer().sendMessage(ChatColor.AQUA + "You are now AFK");
				do_kick = true;
				setAFK(true);
			}
		}
	}
}