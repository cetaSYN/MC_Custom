package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

/**
 * Custom1 class.
 */
public class Custom1 extends MCCustomClass {
	private static final float WALK_SPEED = 0.2f;
	private static final boolean CAN_FLY = true;
	private static final float FLY_SPEED = 0.1f;

	public Custom1() {
		addAbility(AbilityType.FLIGHT);
		addAbility(AbilityType.NEGATE_FALL);
		addAbility(AbilityType.CRAFT_EGG);
		addAbility(AbilityType.CRAFT_VILLAGER);
		addAbility(AbilityType.CRAFT_HORSE_ARMOR);
		addAbility(AbilityType.CRAFT_ICE);
		addAbility(AbilityType.CRAFT_BAT);
		addAbility(AbilityType.ARROW_SPLASH);
		addAbility(AbilityType.INCREASE_XP);
		addAbility(AbilityType.TELEPORT);
		addAbility(AbilityType.REDUCE_DAMAGE_SELF);
		//addAbility(AbilityType.TIME_CHANGE);
		addAbility(AbilityType.WEATHER_CHANGE);
		addAbility(AbilityType.WORKBENCH);
		addAbility(AbilityType.LEVITATE);
	}

	/**
	 * @see MCCustomClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Custom1";
	}

	/**
	 * @see MCCustomClass#getClassColor()
	 */
	@Override
	public ChatColor getClassColor() {
		return ChatColor.LIGHT_PURPLE;
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