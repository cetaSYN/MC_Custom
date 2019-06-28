package com.mc_custom.classes.recipes;

import com.mc_custom.classes.MC_Custom_Classes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Handles recipes for Custom9 class.
 */
public class Custom9Recipes {

	private static final ShapedRecipe IRON_HORSE_ARMOR =
			new ShapedRecipe(
					new ItemStack(Material.IRON_BARDING))
					.shape("I  ", "IWI", "III")
					.setIngredient('I', Material.IRON_INGOT)
					.setIngredient('W', Material.WOOL);

	private static final ShapedRecipe GOLD_HORSE_ARMOR =
			new ShapedRecipe(
					new ItemStack(Material.GOLD_BARDING))
					.shape("G  ", "GWG", "GGG")
					.setIngredient('G', Material.GOLD_INGOT)
					.setIngredient('W', Material.WOOL);

	private static final ShapedRecipe DIAMOND_HORSE_ARMOR =
			new ShapedRecipe(
					new ItemStack(Material.DIAMOND_BARDING))
					.shape("D  ", "DWD", "DDD")
					.setIngredient('D', Material.DIAMOND)
					.setIngredient('W', Material.WOOL);

	/**
	 * Adds recipes for Custom9 class.
	 */
	public static void addRecipes() {
		MC_Custom_Classes.addRecipe(IRON_HORSE_ARMOR);
		MC_Custom_Classes.addRecipe(GOLD_HORSE_ARMOR);
		MC_Custom_Classes.addRecipe(DIAMOND_HORSE_ARMOR);
	}

	/**
	 * Checks if a recipe is Custom9-only.
	 */
	public static boolean isRecipe(Recipe recipe) {
		return recipe.getResult().equals(new ItemStack(Material.IRON_BARDING)) ||
				recipe.getResult().equals(new ItemStack(Material.GOLD_BARDING)) ||
				recipe.getResult().equals(new ItemStack(Material.DIAMOND_BARDING));
	}
}