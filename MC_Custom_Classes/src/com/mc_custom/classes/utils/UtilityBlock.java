package com.mc_custom.classes.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class UtilityBlock {

	private Location block_location;
	private Material old_block_type;
	private Block utility_block;
	private BlockState old_block_state;
	private InventoryType utility_type;
	private byte old_block_data;
	private ItemStack[] old_block_inventory;

	public UtilityBlock(Block old_block, InventoryType utility_type) {
		this.old_block_type = old_block.getType();
		this.utility_type = utility_type;
		this.old_block_state = old_block.getState();
		this.utility_block = old_block;
		this.old_block_data = old_block.getData();
		this.block_location = old_block.getLocation();
		if (old_block.getState() instanceof InventoryHolder) {
			this.old_block_inventory = ((InventoryHolder) old_block.getState()).getInventory().getContents();
		}
	}

	public UtilityBlock setOldBlock(Block old_block) {
		this.old_block_type = old_block.getType();
		return this;
	}

	public Material getOldBlockType() {
		return old_block_type;
	}

	public UtilityBlock setUtilityType(InventoryType utility_type) {
		this.utility_type = utility_type;
		return this;
	}

	public InventoryType getUtilityType() {
		return utility_type;
	}

	public Block getBlock() {
		return utility_block;

	}

	public Location getLocation() {
		return block_location;
	}

	public ItemStack[] getOldInventory() {
		return old_block_inventory;
	}

	public byte getOldBlockData() {
		return old_block_data;
	}

	public BlockState getOldBlockState() {
		return old_block_state;
	}
}
