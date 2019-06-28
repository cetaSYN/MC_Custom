package com.mc_custom.punishments.listeners;

import com.mc_custom.core.listeners.BaseListener;

/**
 * This class is a good idea, however will not work with Bungee.
 * (Saves time having to write something to cache IPs)
 */
public class PingListener implements BaseListener {
/*
	@EventHandler
	public void onPing(final ServerListPingEvent event) {
		try {
			int player_id = ActionQuery.getIdByAddress(event.getAddress());
			List<Action> actions = ActionQuery.getActions(player_id);
			if (actions == null || actions.size() == 0) {
				return;
			}
			Action action = actions.get(0);
			// Order from oldest to newest
			Collections.reverse(actions);
			for (Action last_action : actions) {
				if (last_action.getActionType().equals(ActionType.BAN) || last_action.getActionType().equals(ActionType.UNBAN)) {
					action = last_action;
				}
			}

			File image_file = new File("server-icon.png");
			if (action.getActionType().equals(ActionType.BAN) && image_file.exists() && Files.probeContentType(image_file.toPath()).contains("image")) {
				BufferedImage image = ImageIO.read(image_file);
				Graphics g = image.getGraphics();
				g.setPaintMode();
				g.setFont(new Font("tahoma", Font.BOLD, 11));
				g.setColor(Color.RED);
				g.drawString("BANNED", 0, 64);
				event.setMotd(ChatColor.AQUA + "Comment: " + action.getActionComment());
				event.setServerIcon(Bukkit.loadServerIcon(image));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
