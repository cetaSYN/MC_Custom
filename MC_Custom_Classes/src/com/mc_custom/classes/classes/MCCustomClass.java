package com.mc_custom.classes.classes;

import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.core.utils.ChatColor;

import java.util.LinkedList;

/*
 * Black           Custom4
 * Dark Purple     Custom9
 * Gold            Custom6
 * Gray            Custom10
 * Dark Gray       Custom3
 * Green           Custom5
 * Aqua            Custom7
 * Light Purple    Custom1
 * White           None
 */

/**
 * Abstract class that handles all data relating to individual players.
 */
public abstract class MCCustomClass {
	public static final float CREATIVE_WALK_SPEED = 0.2f;
	public static final boolean CREATIVE_CAN_FLY = true;
	public static final float CREATIVE_FLY_SPEED = 0.2f;
	protected LinkedList<AbilityType> abilities = new LinkedList<>();

	/**
	 * Returns the name of the player's class.
	 */
	public abstract String getClassName();

	/**
	 * Returns the color associated with the class.
	 */
	public abstract ChatColor getClassColor();

	/**
	 * Returns the walking speed of the class.
	 */
	public abstract float getWalkSpeed();

	/**
	 * Returns the walking speed of the class.
	 */
	public abstract float getFlySpeed();

	/**
	 * Returns the walking speed of the class.
	 */
	public abstract boolean canFly();

	/**
	 * Checks to see if the player has an specified ability.
	 */
	public boolean hasAbility(AbilityType check_ability) {
		for (AbilityType ability : abilities) {
			if (ability == check_ability) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds an ability to a player.
	 */
	public void addAbility(AbilityType ability) {
		abilities.add(ability);
	}

	public float getWeatherFlySpeed() {
		return getFlySpeed() - 0.01f;
	}
}
