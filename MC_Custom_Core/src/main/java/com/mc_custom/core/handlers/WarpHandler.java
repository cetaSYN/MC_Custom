package com.mc_custom.core.handlers;

import com.mc_custom.core.utils.TeleportDestination;

import java.util.LinkedList;

/**
 * Base Class for handling TeleportDestinations
 */
public class WarpHandler {

	protected LinkedList<TeleportDestination> destinations = new LinkedList<>();

	public WarpHandler() {
	}

	/**
	 * Add a TeleportDestination to the list
	 * If the list already contains a TeleportDestination
	 * with the same name, it will be removed and replaced
	 * with the new one
	 *
	 * @param warp
	 */
	public void add(TeleportDestination warp) {
		remove(warp.getName());
		destinations.add(warp);
	}

	/**
	 * Gets the TeleportDestination at the specified index
	 *
	 * @param index
	 * @return warp
	 */
	public TeleportDestination get(int index) {
		return destinations.get(index);
	}

	/**
	 * Get TeleportDestination by warp name
	 *
	 * @param name
	 * @return warp if found. null if not found.
	 */
	public TeleportDestination get(String name) {
		for (TeleportDestination warp : destinations) {
			if (warp.getName().equalsIgnoreCase(name)) {
				return warp;
			}
		}
		return null;
	}

	public LinkedList<TeleportDestination> getAll() {
		return destinations;
	}

	/**
	 * Remove TeleportDestination by name
	 *
	 * @param name The name of the warp to remove
	 * @return {@code true} if the warp was removed
	 */
	public boolean remove(String name) {
		for (TeleportDestination warp : destinations) {
			if (warp.getName().equalsIgnoreCase(name)) {
				return destinations.remove(warp);
			}
		}
		return false;
	}

	public void remove(TeleportDestination warp){
		destinations.remove(warp);
	}
}
