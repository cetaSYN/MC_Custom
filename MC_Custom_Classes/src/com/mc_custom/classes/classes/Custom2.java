package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom2 class.
 */
public class Custom2 extends MCCustomClass {
	private static final float WALK_SPEED = 0.17f;
	private static final boolean CAN_FLY = true;
	private static final float FLY_SPEED = 0.048f;

	public Custom2() {
		addAbility(AbilityType.NIGHT_STALKER);
		addAbility(AbilityType.NIGHT_VISION);
		addAbility(AbilityType.CRAFT_BAT);
		addAbility(AbilityType.CRITICAL_HIT);
		addAbility(AbilityType.FLIGHT);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom2";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.DARK_BLUE;
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
