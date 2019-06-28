package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom10 class.
 */
public class Custom10 extends MCCustomClass {
	private static final float WALK_SPEED = 0.2f;//0.25f;
	private static final boolean CAN_FLY = false;
	private static final float FLY_SPEED = 0f;

	public Custom10() {
		addAbility(AbilityType.POTION_BOOST);
		addAbility(AbilityType.STACKABLE_POTS);
		addAbility(AbilityType.MOBILE_BREWING);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom10";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.GRAY;
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