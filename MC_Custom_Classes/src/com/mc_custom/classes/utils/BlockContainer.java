package com.mc_custom.classes.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class BlockContainer {

	private final ItemStack[] inventory;
	private final Material type;
	private final byte data;
	private final boolean is_container;
	private Location location;
	private int distance;

	public BlockContainer(final BlockState state, int distance) {
		if (state instanceof InventoryHolder) {
			InventoryHolder container = (InventoryHolder) state;
			if (container.getInventory().getSize() == 54) {
				DoubleChest double_chest = (DoubleChest) container.getInventory().getHolder();
				Chest left_side = (Chest) double_chest.getLeftSide();
				Chest right_side = (Chest) double_chest.getRightSide();
				inventory = new ItemStack[27];
				if (left_side.getLocation().distanceSquared(state.getLocation()) == 0) {
					for (int i = 0; i < inventory.length; i++) {
						if (left_side.getInventory().getItem(i) != null) {
							inventory[i] = left_side.getInventory().getItem(i).clone();
						}
					}
				}
				else {
					for (int i = 0; i < inventory.length; i++) {
						if (right_side.getInventory().getItem(i + 27) != null) {
							inventory[i] = right_side.getInventory().getItem(i + 27).clone();
						}
					}
				}
			}
			else {
				inventory = new ItemStack[container.getInventory().getSize()];
				for (int i = 0; i < inventory.length; i++) {
					if (container.getInventory().getItem(i) != null) {
						inventory[i] = container.getInventory().getItem(i).clone();
					}
				}
			}
			is_container = true;
		}
		else {
			inventory = null;
			is_container = false;
		}
		type = state.getType();
		data = state.getRawData();
		this.distance = distance;
		location = state.getLocation().clone();
	}

	public ItemStack[] getContents() {
		return inventory;
	}

	public Material getType() {
		return type;
	}

	public byte getData() {
		return data;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isContainer() {
		return is_container;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}
}