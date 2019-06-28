package com.mc_custom.classes.classes;

import com.mc_custom.core.utils.ChatColor;

/**
 * None class.
 */
public class None extends MCCustomClass {
	private static final float WALK_SPEED = 0.18f;
	private static final boolean CAN_FLY = false;
	private static final float FLY_SPEED = 0f;

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "None";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.WHITE;
	}

	@Override
	public float getWalkSpeed() {
		return WALK_SPEED;
	}

	@Override
	public float getFlySpeed() {
		return FLY_SPEED;
	}

	@Override
	public boolean canFly() {
		return CAN_FLY;
	}
}
