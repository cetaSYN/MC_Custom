package com.mc_custom.core.handlers;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.players.BasePlayer;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.PluginLogger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Handles and provides methods for retrieval and modification of the list of online players.
 * Methods are provided for when a player joins and leaves the server.
 */
public class PlayerHandler<P extends BasePlayer> {
	private final HashMap<String, P> player_list = new HashMap<>();

	/**
	 * PlayerManager constructor.
	 */
	public PlayerHandler() {

	}

	/**
	 * Called when a player joins the server.
	 * Adds player to player_list.
	 */
	public void playerJoin(P player) {
		player_list.put(player.getPlayerName(), player);
	}

	/**
	 * Called when a player quits the server.
	 * Removes the player from player_list.
	 */
	public P playerQuit(Player player) {
		return player_list.remove(player.getName());
	}

	/**
	 * IMPORTANT: Do not use as a list of online-players.
	 * Offline-cached players may be stored here.
	 *
	 * @return List of players currently stored in PlayerHandler.
	 */
	public HashMap<String, P> getPlayerList() {
		return player_list;
	}

	/**
	 * @param name The name of the player to retrieve.
	 * @return The CorePlayer object for player_name.
	 * @throws NotOnlineException
	 */
	public P getPlayer(String name) throws NotOnlineException {
		return getPlayer(Bukkit.getPlayer(name));
	}

	/**
	 * Gets the player, but instead of throwing NotOnlineException it returns null.
	 *
	 * @param player The player to retrieve.
	 * @return The CorePlayer object for player_name or null if not found.
	 */
	public P getPlayerSilently(Player player) {
		try {
			return getPlayer(player);
		}
		catch (NotOnlineException e) {
			return null;
		}
	}

	/**
	 * Gets the player, but instead of throwing NotOnlineException it returns null.
	 *
	 * @param name The name of the player to retrieve.
	 * @return The CorePlayer object for player_name or null if not found.
	 */
	public P getPlayerSilently(String name) {
		Player player = Bukkit.getPlayer(name);
		try {
			return getPlayer(player);
		}
		catch (NotOnlineException e) {
			return null;
		}
	}

	/**
	 * @param player The player to retrieve.
	 * @return The CorePlayer object for player_name.
	 * @throws NotOnlineException
	 */
	public P getPlayer(Player player) throws NotOnlineException {
		if (player == null) {
			throw new NotOnlineException();
		}
		String player_name = player.getName();
		P list_player = player_list.get(player_name);
		if (list_player != null) {
			return list_player;
		}
		PluginLogger.core().warning("Could not find player " + player_name + ". Kicking.");
		final Player sketchy = Bukkit.getPlayer(player_name);
		if (sketchy != null && sketchy.isOnline()) {
			MC_Custom_Core.runTaskSynchronously(
					new Runnable() {
						@Override
						public void run() {
							sketchy.kickPlayer("An error has occurred. Try logging back in.");
						}
					});
		}
		throw new NotOnlineException();
	}


	/**
	 * Get CorePlayer by their nickname. Case-insensitive spelling-specific.
	 *
	 * @param nickname
	 * @return
	 * @throws NotOnlineException
	 */
	public CorePlayer getPlayerByNickname(String nickname) throws NotOnlineException {
		if (nickname == null) {
			throw new NotOnlineException();
		}
		for (Object o : player_list.values()) {
			CorePlayer player = (CorePlayer) o;
			if (player.getNickname().equalsIgnoreCase(nickname)) {
				return player;
			}
		}

		//Could not be found.
		throw new NotOnlineException();
	}

	/**
	 * Get CorePlayer by part of their IGN. Case-insensitive spelling-inspecific.
	 *
	 * @param part
	 * @return
	 * @throws NotOnlineException
	 */
	public CorePlayer getPlayerByNameFuzzy(String part) throws NotOnlineException {
		if (part == null || part.length() < 3) {
			throw new NotOnlineException();
		}

		for (Object o : player_list.values()) {
			CorePlayer player = (CorePlayer) o;
			if (player.getPlayerName().toLowerCase().contains(part.toLowerCase())) {
				return player;
			}
		}
		//Could not be found.
		throw new NotOnlineException();
	}

	/**
	 * Get CorePlayer by part of their Nickname. Case-insensitive spelling-inspecific.
	 *
	 * @param part
	 * @return
	 * @throws NotOnlineException
	 */
	public CorePlayer getPlayerByNicknameFuzzy(String part) throws NotOnlineException {
		if (part == null || part.length() < 3) {
			throw new NotOnlineException();
		}
		for (Object o : player_list.values()) {
			CorePlayer player = (CorePlayer) o;
			String nickname = player.getNickname().get();
			if (nickname != null && nickname.toLowerCase().contains(part.toLowerCase())) {
				return player;
			}
		}

		//Could not be found.
		throw new NotOnlineException();
	}

	public CorePlayer getPlayerByAnyNameFuzzy(String part) throws NotOnlineException {
		try {
			return getPlayerByNicknameFuzzy(part);
		}
		catch (NotOnlineException e) {
			return getPlayerByNameFuzzy(part);
		}
	}
}
