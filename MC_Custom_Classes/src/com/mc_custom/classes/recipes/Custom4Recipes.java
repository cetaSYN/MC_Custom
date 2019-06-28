package com.mc_custom.classes.recipes;

import com.mc_custom.classes.MC_Custom_Classes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Custom4Recipes {
	private static final ShapelessRecipe IRON =
			new ShapelessRecipe(
					new ItemStack(Material.IRON_INGOT, 1))
					.addIngredient(Material.IRON_ORE)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe GOLD =
			new ShapelessRecipe(
					new ItemStack(Material.GOLD_INGOT, 1))
					.addIngredient(Material.GOLD_ORE)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe STONE =
			new ShapelessRecipe(
					new ItemStack(Material.STONE, 1))
					.addIngredient(Material.COBBLESTONE)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe CHARCOAL =
			new ShapelessRecipe(
					new ItemStack(Material.IRON_INGOT, 1, (short) 1))
					.addIngredient(Material.LOG)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe GLASS =
			new ShapelessRecipe(
					new ItemStack(Material.GLASS, 1))
					.addIngredient(Material.SAND)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe PORK =
			new ShapelessRecipe(
					new ItemStack(Material.GRILLED_PORK, 1))
					.addIngredient(Material.PORK)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe BEEF =
			new ShapelessRecipe(
					new ItemStack(Material.COOKED_BEEF, 1))
					.addIngredient(Material.RAW_BEEF)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe CHICKEN =
			new ShapelessRecipe(
					new ItemStack(Material.COOKED_CHICKEN, 1))
					.addIngredient(Material.RAW_CHICKEN)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe FISH =
			new ShapelessRecipe(
					new ItemStack(Material.COOKED_FISH, 1))
					.addIngredient(Material.RAW_FISH)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe POTATO =
			new ShapelessRecipe(
					new ItemStack(Material.BAKED_POTATO, 1))
					.addIngredient(Material.POTATO_ITEM)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe BRICK =
			new ShapelessRecipe(
					new ItemStack(Material.CLAY_BRICK, 1))
					.addIngredient(Material.CLAY_BALL)
					.addIngredient(Material.COAL);
	private static final ShapelessRecipe HARDENED_CLAY =
			new ShapelessRecipe(
					new ItemStack(Material.HARD_CLAY, 1))
					.addIngredient(Material.CLAY)
					.addIngredient(Material.COAL);

	public static void addRecipes() {
		MC_Custom_Classes.addRecipe(IRON);
		MC_Custom_Classes.addRecipe(GOLD);
		MC_Custom_Classes.addRecipe(STONE);
		MC_Custom_Classes.addRecipe(IRON);
		MC_Custom_Classes.addRecipe(CHARCOAL);
		MC_Custom_Classes.addRecipe(GLASS);
		MC_Custom_Classes.addRecipe(PORK);
		MC_Custom_Classes.addRecipe(BEEF);
		MC_Custom_Classes.addRecipe(CHICKEN);
		MC_Custom_Classes.addRecipe(FISH);
		MC_Custom_Classes.addRecipe(POTATO);
		MC_Custom_Classes.addRecipe(BRICK);
		MC_Custom_Classes.addRecipe(HARDENED_CLAY);
	}

	public static boolean isRecipe(Recipe recipe) {
		return false;
	}
}
