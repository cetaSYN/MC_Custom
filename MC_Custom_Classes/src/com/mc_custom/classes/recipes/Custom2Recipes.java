package com.mc_custom.classes.recipes;

import com.mc_custom.classes.MC_Custom_Classes;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.SpawnEgg;

/**
 * Handles recipes for Custom2 class.
 */
public class Custom2Recipes {

	private static final ShapedRecipe BAT_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.BAT).toItemStack(1))
					.shape("L L", " P ", "   ")
					.setIngredient('L', Material.LEATHER)
					.setIngredient('P', Material.PORK);


	/**
	 * Adds recipes for Custom2 class.
	 */
	public static void addRecipes() {
		MC_Custom_Classes.addRecipe(BAT_EGG);
	}

	/**
	 * Checks if a recipe is Custom2-only.
	 */
	public static boolean isRecipe(Recipe recipe) {
		if (recipe.getResult().getType() == Material.MONSTER_EGG) {
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.BAT).toItemStack())) {
				return true;
			}
		}
		return false;
	}
}
