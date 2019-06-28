package com.mc_custom.core.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.players.CorePlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class FlightListener implements BaseListener {

	private final PlayerHandler<CorePlayer> player_handler;

	public FlightListener() {
		this.player_handler = MC_Custom_Core.getPlayerHandler();
	}

	@EventHandler
	public void noDrift(PlayerMoveEvent event) throws NotOnlineException {
		CorePlayer player = player_handler.getPlayerSilently(event.getPlayer());
		if (!player.getFlightHandler().hasDrift()
				&& player.isFlying()) {
			// http://i.imgur.com/GzpdHut.png
			// Can't seem to figure this shit out
		}
	}

	@EventHandler
	public void verticalSpeed(PlayerMoveEvent event) throws NotOnlineException {
		CorePlayer player = player_handler.getPlayerSilently(event.getPlayer());
		if (!player.getFlightHandler().hasVertSpeedSynced()
				&& player.isFlying()) {
			// http://i.imgur.com/GzpdHut.png
			// Can't seem to figure this shit out
		}
	}

	@EventHandler
	public void flightCinematic(PlayerMoveEvent event) throws NotOnlineException {
		CorePlayer player = player_handler.getPlayerSilently(event.getPlayer());
		if (player.getFlightHandler().hasCinematic()
				&& player.isFlying()) {
			// Taken from our old flight code. Seems to work, probably needs tweaking
			// We could just make this a PlayerInteractEvent and have it old-school flight
			Vector vector = player.getVelocity();

			vector.setY(-player.getLocation().getPitch() / 100);
			//Not sure if needed, Velocity of X and Z seem to always be 0 when flying.
			vector.setX(player.getLocation().getDirection().getX() / 1.85);
			vector.setZ(player.getLocation().getDirection().getZ() / 1.85);

			player.setVelocity(vector);
		}
	}
}
