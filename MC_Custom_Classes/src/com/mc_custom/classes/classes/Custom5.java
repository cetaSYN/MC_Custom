package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom5 class.
 */
public class Custom5 extends MCCustomClass {
	private static final float WALK_SPEED = 0.2f;//0.25f;
	private static final boolean CAN_FLY = false;
	private static final float FLY_SPEED = 0f;

	public Custom5() {
		addAbility(AbilityType.CRAFT_EGG);
		addAbility(AbilityType.REDUCE_DAMAGE_SELF);
		addAbility(AbilityType.WORKBENCH);
		addAbility(AbilityType.FAST_BONEMEAL);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom5";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.GREEN;
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