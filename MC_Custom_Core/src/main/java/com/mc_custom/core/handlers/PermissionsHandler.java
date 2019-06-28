package com.mc_custom.core.handlers;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.Column;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.players.PermissionGroup;
import com.mc_custom.core.timers.CoreTimer;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PermissionsHandler {
	private static Map<Integer, PermissionGroup> permission_groups = new HashMap<>();
	private static PermissionsHandler permissions = new PermissionsHandler();

	private PermissionsHandler() {
		updatePermissionGroups();
		// Permissions Timer
		MC_Custom_Core.getTimerHandler().submitTask(new PermissionTimer());
	}

	public static PermissionsHandler getInstance() {
		return permissions;
	}


	public Map<Integer, PermissionGroup> getPermissionGroups() {
		return permission_groups;
	}

	public void updatePermissionGroups() {
		getGroups();
		// Build Hierarchy
		for (PermissionGroup group : permission_groups.values()) {
			getParentPerms(group);
		}
	}

	private Map<String, Boolean> getParentPerms(PermissionGroup group) {
		if (group.getParentOf() > 0) {
			PermissionGroup child = permission_groups.get(group.getParentOf());
			group.addPermissionNodes(getParentPerms(child));
		}
		return group.getPermissionNodes();
	}
	private void getGroups() {
		try {
			long duration = System.currentTimeMillis();
			Column<Integer> id = new Column<>(Integer.class);
			Column<String> group_name = new Column<>(String.class);
			Column<String> group_prefix = new Column<>(String.class);
			Column<Integer> parent_of = new Column<>(Integer.class);
			new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `id`, `group_name`, `group_prefix`, `parent_of` FROM `groups`")
					.executeQuery()
					.fetchAll(id, group_name, group_prefix, parent_of);
			for (int i = 0; i < id.size(); i++) {
				int group_id = id.getNext();
				permission_groups.put(group_id,
						new PermissionGroup(group_id, group_name.getNext(), group_prefix.getNext(), parent_of.getNext()));
			}
			PluginLogger.database().info(Utils.debugRunDuration(duration));
			if (permission_groups.size() == 0) {
				PluginLogger.core().severe("NO DEFAULT PERMISSIONS GROUP FOUND.");
				com.mc_custom.core.MC_Custom_Core.shutdown();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class PermissionTimer extends CoreTimer {
		public PermissionTimer() {
			super(600000); //Repeat every 10 minutes.
		}

		@Override
		public void run() {
			PermissionsHandler.getInstance().updatePermissionGroups();
			PluginLogger.core().info("Permissions reloaded.");
		}
	}
}
