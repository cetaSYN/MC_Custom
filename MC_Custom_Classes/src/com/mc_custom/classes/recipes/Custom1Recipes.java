package com.mc_custom.classes.recipes;

import com.mc_custom.classes.MC_Custom_Classes;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.SpawnEgg;

/**
 * Handles recipes for Custom1 class.
 */
public class Custom1Recipes {

	private static final ShapedRecipe VILLAGER_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.VILLAGER).toItemStack(1))
					.shape("BEB", " P ", "B B")
					.setIngredient('B', Material.BONE)
					.setIngredient('E', Material.EMERALD)
					.setIngredient('P', Material.PORK);

	/**
	 * Adds recipes for Custom1 class.
	 */
	public static void addRecipes() {
		MC_Custom_Classes.addRecipe(VILLAGER_EGG);
	}

	/**
	 * Checks if a recipe is Custom1-only.
	 */
	public static boolean isRecipe(Recipe recipe) {
		if (recipe.getResult().getType() == Material.MONSTER_EGG) {
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.VILLAGER).toItemStack())) {
				return true;
			}
		}
		return false;
	}
}