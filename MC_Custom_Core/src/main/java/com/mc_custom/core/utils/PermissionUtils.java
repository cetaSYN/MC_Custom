package com.mc_custom.core.utils;

import org.bukkit.permissions.Permissible;

public class PermissionUtils {

	public static boolean hasWildcardPermission(Permissible user, String permission) {
		if (user.hasPermission(permission)) {
			return true;
		}
		String[] nodes = permission.split("\\.");
		String appended_nodes = "";
		for (String node : nodes) {
			appended_nodes += node + ".";
			if (user.hasPermission(appended_nodes + "*")) {
				return true;
			}
		}
		return false;
	}
}
