package com.mc_custom.core.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.timers.CoreTimer;
import com.mc_custom.core.utils.PluginLogger;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class StatisticsListener implements BaseListener {

	private final PlayerHandler<CorePlayer> player_handler;

	public StatisticsListener() {
		player_handler = MC_Custom_Core.getPlayerHandler();
		MC_Custom_Core.getTimerHandler().submitTask(new MinuteStatIncrementTimer(60 * 1000));//Seconds
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		try {
			player_handler.getPlayer(event.getPlayer()).getPlayerStatistics().incrementBlocksBroken();
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		try {
			player_handler.getPlayer(event.getPlayer()).getPlayerStatistics().incrementBlocksPlaced();
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void playerMove(PlayerMoveEvent event) {
		try {
			player_handler.getPlayer(event.getPlayer()).getPlayerStatistics().incrementMovementDistance(event.getFrom().distance(event.getTo()));
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	//TODO Player variant for PvP
	@EventHandler
	public void entityKill(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		//Hostile Mobs
		switch (entity.getType()) {
			case ZOMBIE:
			case PIG_ZOMBIE:
			case ENDERMAN:
			case CREEPER:
			case SKELETON:
			case BLAZE:
			case GHAST:
			case MAGMA_CUBE:
			case CAVE_SPIDER:
			case SPIDER:
				break;
			default:
				return;
		}
		//Should fix NPEs when there is no last damage
		if (entity.getLastDamageCause() == null) {
			return;
		}
		//Filter Mob Griders
		switch (entity.getLastDamageCause().getCause()) {
			case CONTACT:
			case CUSTOM:
			case DROWNING:
			case FALL:
			case FIRE:
			case FIRE_TICK:
			case FALLING_BLOCK:
			case SUFFOCATION:
			case MAGIC:
			case THORNS:
				return;
			default:
				break;
		}
		//Filter Mob Grinders
		if (entity.getNearbyEntities(2, 2, 2).size() > 2) {
			return;
		}
		Player killer = entity.getKiller();
		if (killer != null) {
			try {
				player_handler.getPlayer(killer).getPlayerStatistics().incrementPoints(1);
				PluginLogger.core().info(killer.getName() + " killed " + entity.getType().name());
				player_handler.getPlayer(killer).getPlayerStatistics().incrementMobsKilled(1);
			}
			catch (NotOnlineException e) {
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void playerDeath(PlayerDeathEvent event) {
		try {
			player_handler.getPlayer(event.getEntity()).getPlayerStatistics().incrementPlayerDeaths();
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}

	private class MinuteStatIncrementTimer extends CoreTimer {

		/**
		 * @param repeat_delay Seconds between event executions.
		 */
		public MinuteStatIncrementTimer(long repeat_delay) {
			super(repeat_delay);
		}

		@Override
		public void run() {
			for (CorePlayer core_player : player_handler.getPlayerList().values()) {
				core_player.getPlayerStatistics().incrementMinutesOnline();
				core_player.getPlayerStatistics().incrementPoints(1);
			}
		}
	}
}