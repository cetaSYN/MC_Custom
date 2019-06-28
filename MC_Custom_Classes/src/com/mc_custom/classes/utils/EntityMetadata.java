package com.mc_custom.classes.utils;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class EntityMetadata implements MetadataValue {
	Plugin plugin;
	Object value;

	public EntityMetadata(Plugin plugin, Object value) {
		this.plugin = plugin;
		this.value = value;
	}

	@Override
	public boolean asBoolean() {
		if (value instanceof Boolean) {
			return (boolean) value;
		}
		throw new NullPointerException();
	}

	@Override
	public byte asByte() {
		if (value instanceof Byte) {
			return (byte) value;
		}
		throw new NullPointerException();
	}

	@Override
	public double asDouble() {
		if (value instanceof Double) {
			return (double) value;
		}
		throw new NullPointerException();
	}

	@Override
	public float asFloat() {
		if (value instanceof Float) {
			return (float) value;
		}
		throw new NullPointerException();
	}

	@Override
	public int asInt() {
		if (value instanceof Integer) {
			return (int) value;
		}
		throw new NullPointerException();
	}

	@Override
	public long asLong() {
		if (value instanceof Long) {
			return (long) value;
		}
		throw new NullPointerException();
	}

	@Override
	public short asShort() {
		if (value instanceof Short) {
			return (short) value;
		}
		throw new NullPointerException();
	}

	@Override
	public String asString() {
		if (value instanceof String) {
			return (String) value;
		}
		throw new NullPointerException();
	}

	@Override
	public Plugin getOwningPlugin() {
		return plugin;
	}

	@Override
	public void invalidate() {
	}

	@Override
	public Object value() {
		return value;
	}
}