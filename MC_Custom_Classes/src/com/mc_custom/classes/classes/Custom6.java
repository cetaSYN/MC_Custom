package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom6 class.
 */
public class Custom6 extends MCCustomClass {
	private static final float WALK_SPEED = 0.2f;
	private static final boolean CAN_FLY = true;
	private static final float FLY_SPEED = .08f;

	public Custom6() {
		addAbility(AbilityType.FLIGHT);
		addAbility(AbilityType.NEGATE_FALL);
		addAbility(AbilityType.RETALIATE);
		addAbility(AbilityType.TALONS);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom6";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.GOLD;
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
