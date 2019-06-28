package com.mc_custom.punishments.listeners;

import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.punishments.MC_Custom_Punishments;
import com.mc_custom.punishments.players.ActionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static com.mc_custom.core.utils.ChatColor.RED;

/**
 * Handles Events for freezing a player.
 * Player cannot move, speak, or use any actions.
 * Replaces Jail.
 */
public class FreezeListener implements BaseListener {

	private PlayerHandler<ActionPlayer> player_handler;

	public FreezeListener() {
		this.player_handler = MC_Custom_Punishments.getPlayerHandler();
	}

	@EventHandler
	public void onMove(final PlayerMoveEvent event) {
		Player player = event.getPlayer();
		try {
			if (player_handler.getPlayer(player).isFrozen()) {
				if (event.getFrom().getX() != event.getTo().getX()
						|| event.getFrom().getZ() != event.getTo().getZ()) {
					player.teleport(event.getFrom());
					player.sendMessage(RED + "You are frozen!");
				}
			}
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void cancelChat(final AsyncPlayerChatEvent event) {
		for (ActionPlayer action_player : MC_Custom_Punishments.getOnlinePlayers()) {
			if (action_player.isFrozen()) {
				event.getRecipients().remove(action_player.getPlayer());
			}
		}
	}

	@EventHandler
	public void interact(final PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.LEFT_CLICK_BLOCK
				|| event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			try {
				if (player_handler.getPlayer(event.getPlayer()).isFrozen()) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(RED + "You are frozen!");
				}
			}
			catch (NotOnlineException e) {
				e.printStackTrace();
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void cancelCommands(final PlayerCommandPreprocessEvent event) throws NotOnlineException {
		if (player_handler.getPlayer(event.getPlayer()).isFrozen()) {
			String msg = event.getMessage().split(" ")[0];
			switch (msg.toLowerCase()) {
				// Allowed Commands
				case "/message":
					break;
				case "/msg":
					break;
				case "/m":
					break;
				case "/me":
					break;
				case "/reply":
					break;
				case "/r":
					break;
				case "/whisper":
					break;
				case "/tell":
					break;
				case "/t":
					break;
				case "/unfreeze":
					break;
				case "/thaw":
					break;
				default: //Bad commands
					event.getPlayer().sendMessage(RED + "You cannot use this command while muted.");
					event.setCancelled(true);
			}
		}
	}
}
