package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom8 class.
 */
public class Custom8 extends MCCustomClass {
	private static final float WALK_SPEED = 0.18f;
	private static final boolean CAN_FLY = false;
	private static final float FLY_SPEED = 0.08f;

	public Custom8() {
		addAbility(AbilityType.CRAFT_ICE);
		addAbility(AbilityType.SWIM);
		addAbility(AbilityType.DROWN_IMMUNITY);
		addAbility(AbilityType.AQUA_AFFINITY);
		addAbility(AbilityType.SNOWBALL_DAMAGE);
		addAbility(AbilityType.INCREASE_DAMAGE_FIRE);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom8";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.DARK_AQUA;
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
