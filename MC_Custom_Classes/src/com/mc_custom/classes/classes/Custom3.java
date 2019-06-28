package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom3 class.
 */
public class Custom3 extends MCCustomClass {
	private static final float WALK_SPEED = 0.2f;
	private static final boolean CAN_FLY = true;
	private static final float FLY_SPEED = 0.064f; //Between a Custom7, and a Custom4

	public Custom3() {
		addAbility(AbilityType.FLIGHT);
		addAbility(AbilityType.NEGATE_FALL);
		addAbility(AbilityType.INCREASE_DAMAGE_SELF);
		addAbility(AbilityType.IMPERSONATE);
		addAbility(AbilityType.LIFE_STEAL);
		addAbility(AbilityType.NO_HEALTH_REGEN);
		addAbility(AbilityType.FOOD_REGEN);
		addAbility(AbilityType.LEVITATE);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom3";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.DARK_GRAY;
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