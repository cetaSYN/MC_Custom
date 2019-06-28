package com.mc_custom.core.players;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.utils.ChatColor;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionGroup {
	private int id;
	private String group_name;
	private String group_prefix;
	private int parent_of;
	private Map<String, Boolean> permission_nodes = new HashMap<>();

	public PermissionGroup(int id, String group_name, String group_prefix, int parent_of) {
		this.id = id;
		this.group_name = group_name;
		this.group_prefix = ChatColor.convertColor(group_prefix);
		// prevents issues with infinite loops when referring to self
		if (parent_of == id) {
			parent_of = 0;
		}
		this.parent_of = parent_of;
		getGroupPermissions();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return group_name;
	}

	public String getPrefix() {
		return group_prefix;
	}

	public int getParentOf() {
		return parent_of;
	}

	public Map<String, Boolean> getPermissionNodes() {
		return permission_nodes;
	}

	public void setPermissionNodes(Map<String, Boolean> permission_nodes) {
		this.permission_nodes = permission_nodes;
	}

	public void addPermissionNodes(Map<String, Boolean> permission_nodes) {
		// Maps will replace the value on a put(key)
		// In some cases the parent groups have inverse values from their child groups
		// This corrects that
		Map<String, Boolean> old_perms = new HashMap<>();
		old_perms.putAll(this.permission_nodes);
		this.permission_nodes.putAll(permission_nodes);
		this.permission_nodes.putAll(old_perms);
		old_perms.clear();
	}

	public int valueOf() {
		return parent_of + 1;
	}

	private Map<String, Boolean> getGroupPermissions() {
		try {
			List<String> nodes = new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `perm_node` FROM `group_perms` WHERE `group_id` = ?")
					.setInt(id)
					.executeQuery()
					.fetchAll(String.class);
			for (String node : nodes) {
				if (node != null && !node.isEmpty()) {
					if (node.startsWith("-") || node.startsWith("^")) {
						permission_nodes.put(node.substring(1), false);
					}
					else {
						permission_nodes.put(node, true);
					}
				}
			}
			return permission_nodes;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
