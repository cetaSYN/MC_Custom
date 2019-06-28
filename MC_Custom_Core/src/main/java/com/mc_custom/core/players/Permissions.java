package com.mc_custom.core.players;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.handlers.PermissionsHandler;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import org.bukkit.permissions.PermissionAttachment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Permissions {
	private CorePlayer player;
	private List<Integer> group_ids = new ArrayList<>();
	private List<PermissionGroup> permission_groups = new ArrayList<>();
	private Map<String, Boolean> special_perm_nodes = new HashMap<>();
	private Map<Integer, PermissionAttachment> group_attachments = new HashMap<>();
	private PermissionAttachment special_attachment;

	/**
	 * Called in CorePlayer (async)
	 */
	public Permissions(CorePlayer player) {
		this.player = player;

		//Get group ids from player
		getGroupIds();

		//Get Special Nodes
		getPlayerPermissions();

		//Get only the permission_groups we need for the player.
		for (int id : group_ids) {
			permission_groups.add(PermissionsHandler.getInstance().getPermissionGroups().get(id));
		}

		findPrimaryGroup();

		// Append the prefixes together. May cause issues depending on how we set up the groups.
		for (PermissionGroup group : permission_groups) {
			player.appendPrefix(group.getPrefix());
		}
	}

	/**
	 * Attaches permissions to the Player object
	 * This will be called upon joining the server.
	 */
	public void attachPermissions() {
		for (PermissionGroup group : permission_groups) {
			group_attachments.put(group.getId(), player.addAttachment());
			setGroupPermissions(group.getId(), group.getPermissionNodes());
		}
		special_attachment = player.addAttachment();
		setSpecialPermissions(special_perm_nodes);
	}

	/**
	 * @param group_name The group name
	 * @return PermissionGroup group if found, else null
	 */
	public PermissionGroup getGroup(String group_name) {
		for (PermissionGroup group : permission_groups) {
			if (group.getName().equalsIgnoreCase(group_name)) {
				return group;
			}
		}
		return null;
	}

	public Collection<PermissionGroup> getGroups() {
		return permission_groups;
	}

	/**
	 * Adds a group to the player.
	 *
	 * @param group The PermissionGroup to add to the player
	 */
	public void addGroup(PermissionGroup group) {
		int group_id = group.getId();
		group_ids.add(group_id);
		permission_groups.add(group_id, group);
		group_attachments.put(group_id, player.addAttachment());
		setGroupPermissions(group_id, group.getPermissionNodes());
		//Update prefix
		player.appendPrefix(group.getPrefix());
		//Adjust display name
		String name = player.getName();
		player.setDisplayName(name);
		if (name.length() >= 16) { //Only applicable for nicks & prefix (player name will be under 16 chars)
			name = name.substring(0, 15);
		}
		player.setPlayerListName(name);
	}

	/**
	 * Removes a group from the player.
	 *
	 * @param group The PermissionGroup to add to the player
	 */
	public void removeGroup(PermissionGroup group) {
		if (group_ids.size() > 1) {
			int group_id = group.getId();
			group_ids.remove(group_id);
			permission_groups.remove(group_id);
			//Remove permissions from player
			for (Entry<String, Boolean> node : group.getPermissionNodes().entrySet()) {
				group_attachments.get(group_id).unsetPermission(node.getKey());
			}
			group_attachments.remove(group_id);
			//Update prefix
			player.setPrefix("");
			for (PermissionGroup p_group : permission_groups) {
				player.appendPrefix(p_group.getPrefix());
			}
			//Adjust display name
			String name = player.getName();
			player.setDisplayName(name);
			if (name.length() >= 16) { //Only applicable for nicks & prefix (player name will be under 16 chars)
				name = name.substring(0, 15);
			}
			player.setPlayerListName(name);
		}
	}

	public void addSpecialNode(String node, boolean value) {
		if (node == null) {
			return;
		}
		special_attachment.setPermission(node, value);
	}

	/**
	 * Finds the first parent group out of the current groups assigned to
	 */
	private void findPrimaryGroup() {
		if (permission_groups.size() == 1) {
			player.setPrimaryGroup(permission_groups.get(0));
		}
		else {
			// they were put in the list in descending order
			player.setPrimaryGroup(permission_groups.get(permission_groups.size() - 1));
			PermissionGroup primary_group = player.getPrimaryGroup();
			for (PermissionGroup group : permission_groups) {
				if (group.getParentOf() > primary_group.getParentOf()) {
					player.setPrimaryGroup(group);
				}
			}
		}
	}

	/**
	 * Adds a Map of permission nodes to this specific player.
	 * This method is only used during construction, and should not be used to change a player's group perms.
	 *
	 * @param nodes Map of permission nodes to set
	 */
	private void setSpecialPermissions(Map<String, Boolean> nodes) {
		if (nodes == null) {
			return;
		}
		for (Entry<String, Boolean> node : nodes.entrySet()) {
			special_attachment.setPermission(node.getKey(), node.getValue());
		}
	}

	/**
	 * Adds a Map of permission nodes to this specific player.
	 * This method is only used during construction, and should not be used to change a player's group perms.
	 *
	 * @param id    The Id of the group
	 * @param nodes Map of permission nodes to set
	 */
	private void setGroupPermissions(int id, Map<String, Boolean> nodes) {
		if (nodes == null) {
			return;
		}
		for (Entry<String, Boolean> node : nodes.entrySet()) {
			group_attachments.get(id).setPermission(node.getKey(), node.getValue());
		}
	}

	/**
	 * Gets the groups that this player belongs to.
	 */
	private void getGroupIds() {
		try {
			long duration = System.currentTimeMillis();
			group_ids = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `group_id` FROM `player_groups` WHERE `player_id` = ? AND (`server_id` = ? OR `server_id` = 0) ORDER BY `group_id` DESC")
					.setInt(player.getId())
					.setInt(MC_Custom_Core.getServerId())
					.executeQuery()
					.fetchAll(Integer.class);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		if (group_ids.isEmpty()) {
			MC_Custom_Core.runTaskAsynchronously(new Runnable() {
				@Override
				public void run() {
					try {
						long duration = System.currentTimeMillis();
						new QueryBuilder(MC_Custom_Core.getDBConnection(), "INSERT IGNORE INTO `player_groups` (`group_id`, `player_id`, `server_id`) VALUES (1, ?, ?)")
								.setInt(player.getId())
								.setInt(MC_Custom_Core.getServerId())
								.update();
						PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			group_ids.add(1);
		}
	}

	/**
	 * Gets the special nodes for the given player.
	 */
	private void getPlayerPermissions() {
		try {
			long duration = System.currentTimeMillis();
			List<String> nodes = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `perm_node` FROM `player_perms` WHERE `player_id` = ?")
					.setInt(player.getId())
					.executeQuery()
					.fetchAll(String.class);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
			for (String node : nodes) {
				if (node != null && !node.isEmpty()) {
					if (node.startsWith("-") || node.startsWith("^")) {
						special_perm_nodes.put(node.substring(1), false);
					}
					else {
						special_perm_nodes.put(node, true);
					}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}