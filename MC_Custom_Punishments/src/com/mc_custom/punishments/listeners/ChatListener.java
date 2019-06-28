package com.mc_custom.punishments.listeners;

import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.punishments.MC_Custom_Punishments;
import com.mc_custom.punishments.players.ActionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static com.mc_custom.core.utils.ChatColor.RED;

public class ChatListener implements BaseListener {

	private PlayerHandler<ActionPlayer> player_handler;

	public ChatListener() {
		this.player_handler = MC_Custom_Punishments.getPlayerHandler();
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		try {
			if (player_handler.getPlayer(player).isMuted()) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(RED + "You are muted.");
			}
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void cancelCommands(final PlayerCommandPreprocessEvent event) {
		try {
			if (player_handler.getPlayer(event.getPlayer()).isMuted()) {
				String msg = event.getMessage().split(" ")[0];
				if (msg.equalsIgnoreCase("/me")) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(RED + "You are muted.");
				}
			}
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}
}