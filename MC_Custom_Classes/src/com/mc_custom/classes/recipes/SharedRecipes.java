package com.mc_custom.classes.recipes;

import com.mc_custom.classes.MC_Custom_Classes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mc_custom.core.utils.ChatColor.AQUA;
import static com.mc_custom.core.utils.ChatColor.ITALIC;
import static com.mc_custom.core.utils.ChatColor.YELLOW;

/**
 * Handles recipes for All classes.
 */
public class SharedRecipes {

	private static final ShapedRecipe NAME_TAG_TOP =
			new ShapedRecipe(new ItemStack(Material.NAME_TAG))
					.shape("SPP", "   ", "   ")
					.setIngredient('S', Material.STRING)
					.setIngredient('P', Material.PAPER);

	private static final ShapedRecipe NAME_TAG_MIDDLE =
			new ShapedRecipe(new ItemStack(Material.NAME_TAG))
					.shape("   ", "SPP", "   ")
					.setIngredient('S', Material.STRING)
					.setIngredient('P', Material.PAPER);

	private static final ShapedRecipe NAME_TAG_BOTTOM =
			new ShapedRecipe(new ItemStack(Material.NAME_TAG))
					.shape("   ", "   ", "SPP")
					.setIngredient('S', Material.STRING)
					.setIngredient('P', Material.PAPER);

	public static void addRecipes() {
		MC_Custom_Classes.addRecipe(NAME_TAG_TOP);
		MC_Custom_Classes.addRecipe(NAME_TAG_MIDDLE);
		MC_Custom_Classes.addRecipe(NAME_TAG_BOTTOM);
	}


	/**
	 * Checks if a recipe is SharedRecipe.
	 */
	public static boolean isRecipe(Recipe recipe) {
		return recipe.getResult().getType() == Material.NAME_TAG;
	}
}
