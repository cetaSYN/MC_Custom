package com.mc_custom.core.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Class to preserve a BlockState that does not change once it is created.
 */
public class ImmutableBlockState implements BlockState {
	private final Block block;
	private final World world;

	private final MaterialData data;
	private final byte light_level;
	private final Location location;
	private final int x;
	private final int y;
	private final int z;
	private final Chunk chunk;
	private final byte raw_data;
	private final boolean is_placed;

	public ImmutableBlockState(BlockState state) {
		block = state.getBlock();
		world = state.getWorld();
		data = state.getData().clone();
		light_level = state.getLightLevel();
		location = state.getLocation().clone();
		x = state.getX();
		y = state.getY();
		z = state.getZ();
		chunk = state.getChunk();
		raw_data = state.getRawData();
		is_placed = state.isPlaced();
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public MaterialData getData() {
		return data;
	}

	@Override
	public Material getType() {
		return data.getItemType();
	}

	@Override
	public int getTypeId() {
		return data.getItemTypeId();
	}

	@Override
	public byte getLightLevel() {
		return light_level;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public Location getLocation(Location location) {
		return location.add(location);
	}

	@Override
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public void setData(MaterialData materialData) {

	}

	@Override
	public void setType(Material material) {

	}

	@Override
	public boolean setTypeId(int i) {
		return false;
	}

	@Override
	public boolean update() {
		return false;
	}

	@Override
	public boolean update(boolean b) {
		return false;
	}

	@Override
	public boolean update(boolean b, boolean b1) {
		return false;
	}

	@Override
	public byte getRawData() {
		return raw_data;
	}

	@Override
	public void setRawData(byte b) {

	}

	@Override
	public boolean isPlaced() {
		return is_placed;
	}

	@Override
	public void setMetadata(String s, MetadataValue metadataValue) {

	}

	@Override
	public List<MetadataValue> getMetadata(String s) {
		return null;
	}

	@Override
	public boolean hasMetadata(String s) {
		return false;
	}

	@Override
	public void removeMetadata(String s, Plugin plugin) {

	}
}
