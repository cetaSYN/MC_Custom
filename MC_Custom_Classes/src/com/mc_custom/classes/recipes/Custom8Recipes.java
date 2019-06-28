package com.mc_custom.classes.recipes;

import com.mc_custom.classes.MC_Custom_Classes;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.material.SpawnEgg;

/**
 * Handles recipes for Custom8 class.
 */
public class Custom8Recipes {

	private static final ShapedRecipe SQUID = new ShapedRecipe(
			new SpawnEgg(EntityType.SQUID).toItemStack(1))
			.shape(" I ", " S ", "III")
			.setIngredient('I', Material.INK_SACK)
			.setIngredient('S', Material.SLIME_BALL);

	private static final ShapedRecipe ICE = new ShapedRecipe(
			new MaterialData(Material.ICE).toItemStack(3))
			.shape("SSS", "SWS", "SSS")
			.setIngredient('S', Material.SNOW_BALL)
			.setIngredient('W', Material.WATER_BUCKET);

	private static final ShapedRecipe PACKED_ICE = new ShapedRecipe(
			new MaterialData(Material.PACKED_ICE).toItemStack(3))
			.shape("SSS", "SSS", "SSS")
			.setIngredient('S', Material.ICE);

	/**
	 * Adds recipes for Custom8 class.
	 */
	public static void addRecipes() {
		MC_Custom_Classes.addRecipe(ICE);
		MC_Custom_Classes.addRecipe(PACKED_ICE);
		MC_Custom_Classes.addRecipe(SQUID);
	}

	/**
	 * Checks if a recipe is Custom8-only.
	 */
	public static boolean isRecipe(Recipe recipe) {
		return recipe.getResult().getType() == Material.ICE
				|| recipe.getResult().getType() == Material.PACKED_ICE
				|| recipe.getResult().isSimilar(new SpawnEgg(EntityType.SQUID).toItemStack(1));
	}
}
