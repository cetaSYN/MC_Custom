package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

public class Custom4 extends MCCustomClass {

	private static final float WALK_SPEED = 0.17f;
	private static final boolean CAN_FLY = true;
	private static final float FLY_SPEED = 0.048f;

	public Custom4() {
		addAbility(AbilityType.FLIGHT);
		addAbility(AbilityType.LAVA_IMMUNITY);
		addAbility(AbilityType.FIRE_BREATH);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom4";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.BLACK;
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
