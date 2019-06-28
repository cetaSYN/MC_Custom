package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom7 class.
 */
public class Custom7 extends MCCustomClass {
	private static final float WALK_SPEED = 0.2f;
	private static final boolean CAN_FLY = true;
	private static final float FLY_SPEED = 0.1f;

	public Custom7() {
		addAbility(AbilityType.FLIGHT);
		addAbility(AbilityType.NEGATE_FALL);
		addAbility(AbilityType.WEATHER_CHANGE);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom7";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.AQUA;
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