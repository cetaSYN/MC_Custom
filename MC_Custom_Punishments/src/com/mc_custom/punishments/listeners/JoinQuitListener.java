package com.mc_custom.punishments.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.mc_custom.core.events.CorePreLoginEvent;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.punishments.Action;
import com.mc_custom.punishments.ActionHandler;
import com.mc_custom.punishments.ActionType;
import com.mc_custom.punishments.MC_Custom_Punishments;
import com.mc_custom.punishments.players.ActionPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class JoinQuitListener implements BaseListener {

	private ActionHandler action_handler;
	private PlayerHandler<ActionPlayer> player_handler;

	private final String json_message = "[{\"text\":\"" + ChatColor.RED + "%1$s\"," +
			"\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://url_removed/punishments/player/%1$s\"}," +
			"\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + ChatColor.RED + "%1$s's punishments\"}" +
			"}, \"" + ChatColor.RED + " has been %2$s within the last week\"]";
	private final String message = ChatColor.RED + "%1$s has been %2$s within the last week";

	public JoinQuitListener() {
		this.action_handler = MC_Custom_Punishments.getActionHandler();
		this.player_handler = MC_Custom_Punishments.getPlayerHandler();
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onCorePreLogin(final CorePreLoginEvent event) {
		try {
			CorePlayer player = event.getCorePlayer();
			String player_name = player.getPlayerName();
			int player_id = player.getId();
			player_handler.playerJoin(new ActionPlayer(player_name, player.getNickname(), player.getUUID(), player_id, action_handler.getActions(player_id)));
			ActionPlayer action_player = player_handler.getPlayerList().get(player_name); //using this to avoid the NotOnlineException nonsense
			if(action_player != null && action_player.isBanned()) {
				event.setJoinAllowed(false);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			event.setMessage("An error has occurred. EX_PUNCPL");
			event.setJoinAllowed(false);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player join_player = event.getPlayer();

		ActionPlayer action_player = null;
		try {
			action_player = player_handler.getPlayer(join_player);
		}
		catch (NotOnlineException e) {
			//don't do anything if we can't get the player
			return;
		}
		//Set the internal player for use in other parts of the plugin.
		action_player.setPlayer(join_player);

		List<Action> actions = action_player.getPlayerActions();

		long week_prior = System.currentTimeMillis() - 604800000;//7 Days in millis
		if (actions.size() > 0 && actions.get(0).getActionTime() > week_prior) {
			String punish_type = "punished";
			if (actions.get(0).getActionType() == ActionType.UNBAN) {
				punish_type = "unbanned";
			}

			Bukkit.getConsoleSender().sendMessage(String.format(message, join_player.getName(), punish_type));
			//String message = String.format(json_message, join_player.getName(), punish_type);

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("punish.receive_warnings")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + player.getName() +
					" " + message);
				}
			}
		}
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		player_handler.playerQuit(event.getPlayer());
	}
}