package com.mc_custom.core.utils;

import com.mc_custom.core.MC_Custom_Core;

import me.botsko.prism.Prism;
import me.botsko.prism.actionlibs.ActionFactory;
import me.botsko.prism.actionlibs.ActionType;
import me.botsko.prism.actionlibs.RecordingQueue;
import me.botsko.prism.events.PrismCustomPlayerActionEvent;
import me.botsko.prism.exceptions.InvalidActionException;
import org.bukkit.entity.Player;

public class PrismUtils {

	private static PrismUtils prism_utils;

	private PrismUtils() {
		try {
			Prism.getActionRegistry().registerCustomAction(MC_Custom_Core.getInstance(), new ActionType("pmc-custom4-fireball", null, "used fireball ability"));
		}
		catch (InvalidActionException e) {
			e.printStackTrace();
		}
	}

	public static PrismUtils getInstance() {
		if (prism_utils == null) {
			prism_utils = new PrismUtils();
		}
		return prism_utils;
	}

	public void callCommandEvent(Player player, String full_command) {
		RecordingQueue.addToQueue(ActionFactory.createPlayer("player-command", player, full_command));
	}

	public void callPrismEvent(String flag, Player player, String message) {
		MC_Custom_Core.callEvent(new PrismCustomPlayerActionEvent(MC_Custom_Core.getInstance(), flag, player, message));
	}
}
