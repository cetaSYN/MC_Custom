package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom9 class.
 */
public class Custom9 extends MCCustomClass {
	private static final float WALK_SPEED = 0.2f;
	private static final boolean CAN_FLY = false;
	private static final float FLY_SPEED = 0f;

	public Custom9() {
		addAbility(AbilityType.ARROW_SPLASH);
		addAbility(AbilityType.INCREASE_DAMAGE_SELF);
		addAbility(AbilityType.INCREASE_XP);
		addAbility(AbilityType.TELEPORT);
		addAbility(AbilityType.CRAFT_HORSE_ARMOR);
		addAbility(AbilityType.LEVITATE);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom9";
	}

	@Override
	public ChatColor getClassColor() {
		return ChatColor.DARK_PURPLE;
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
