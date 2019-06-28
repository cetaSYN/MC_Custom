package com.mc_custom.core.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BaseEvent extends Event implements Cancellable {

	protected static final HandlerList handlers = new HandlerList();
	protected boolean cancelled = false;

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	// I think this was changed in later Spigot builds
	// My local keeps failing so keeping it in
	public static HandlerList getHandlerList() {
		return handlers;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
