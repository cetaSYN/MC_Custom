package com.mc_custom.core.handlers;


import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.TeleportDestination;

import java.util.Random;

/**
 * Class for handling TeleportDestinations
 */
public class SpawnHandler extends WarpHandler {
	private TeleportDestination first_spawn = null;

	public SpawnHandler() {
		super();
	}

	/**
	 * Set the initial spawn location
	 *
	 * @param spawn
	 */
	public void setFirstSpawn(TeleportDestination spawn) {
		this.first_spawn = spawn;
	}

	/**
	 * Get the initial spawn location
	 *
	 * @return the first spawn
	 */
	public TeleportDestination getFirstSpawn() {
		if (first_spawn == null) {
			return getRandomSpawn(null);
		}
		return first_spawn;
	}

	/**
	 * Get a random spawn from the list
	 *
	 * @return a random spawn
	 */
	public TeleportDestination getRandomSpawn(CorePlayer player) {
		Random random = new Random();
		int i = random.nextInt(destinations.size());
		TeleportDestination spawn = destinations.get(i);
		if (player == null) {
			return spawn;
		}
		if (player.getLocation().getWorld() != spawn.getLocation().getWorld()) {//If it's in a different world, just go for it.
			return spawn;
		}
		if (player.getLocation().distance(spawn.getLocation()) < 100) { //If current loc within 100 of spawn
			spawn = destinations.get(i < destinations.size() - 1 ? i + 1 : 0); //Pick next spawn (if at end of index, move to 0)
		}
		if (spawn.equals(first_spawn)) { //If spawn is first spawn. (removed spawn)
			spawn = destinations.get(i < destinations.size() - 1 ? i + 1 : 0); //Pick next spawn (if at end of index, move to 0)
		}
		return spawn;
	}
}
