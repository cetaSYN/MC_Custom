package com.mc_custom.classes.recipes;

import com.mc_custom.classes.MC_Custom_Classes;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.material.SpawnEgg;

/**
 * Handles recipes for Custom5 class.
 */
public class Custom5Recipes {

	private static final ShapedRecipe PIG_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.PIG).toItemStack(1))
					.shape("A  ", "PPP", "P P")
					.setIngredient('A', Material.APPLE)
					.setIngredient('P', Material.PORK);

	private static final ShapedRecipe SHEEP_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.SHEEP).toItemStack(1))
					.shape("   ", "WWW", "L L")
					.setIngredient('W', Material.WOOL)
					.setIngredient('L', Material.LEATHER);

	private static final ShapedRecipe COW_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.COW).toItemStack(1))
					.shape("W  ", "LLL", "L L")
					.setIngredient('W', Material.WHEAT)
					.setIngredient('L', Material.LEATHER);

	private static final ShapedRecipe CHICKEN_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.CHICKEN).toItemStack(1))
					.shape(" F ", "FCF", "   ")
					.setIngredient('F', Material.FEATHER)
					.setIngredient('C', Material.RAW_CHICKEN);

	private static final ShapedRecipe HORSE_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.HORSE).toItemStack(1))
					.shape("W  ", "LSL", "L L")
					.setIngredient('W', Material.WHEAT)
					.setIngredient('S', Material.SADDLE)
					.setIngredient('L', Material.LEATHER);

	private static final ShapedRecipe WOLF_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.WOLF).toItemStack(1))
					.shape("B  ", "PPP", "B B")
					.setIngredient('P', Material.PORK)
					.setIngredient('B', Material.BONE);

	private static final ShapedRecipe OCELOT_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.OCELOT).toItemStack(1))
					.shape("M  ", "FFF", "F F")
					.setIngredient('M', Material.MILK_BUCKET)
					.setIngredient('F', Material.RAW_FISH);

	private static final ShapedRecipe CREEPER_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.CREEPER).toItemStack(1))
					.shape(" G ", " T ", "STS")
					.setIngredient('G', Material.GOLD_INGOT)
					.setIngredient('T', Material.TNT)
					.setIngredient('S', Material.SULPHUR);

	@SuppressWarnings("deprecation")
	private static final ShapedRecipe SKELETON_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.SKELETON).toItemStack(1))
					.shape(" G ", "AWA", "B B")
					.setIngredient('W', new MaterialData(Material.BOW, (byte) -1))
					.setIngredient('B', Material.BONE)
					.setIngredient('A', Material.ARROW)
					.setIngredient('G', Material.GOLD_INGOT);

	private static final ShapedRecipe SPIDER_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.SPIDER).toItemStack(1))
					.shape("   ", "SGS", "S S")
					.setIngredient('G', Material.GOLD_INGOT)
					.setIngredient('S', Material.STRING);

	private static final ShapedRecipe ZOMBIE_EGG =
			new ShapedRecipe(
					new SpawnEgg(EntityType.ZOMBIE).toItemStack(1))
					.shape(" G ", "RRR", "R R")
					.setIngredient('G', Material.GOLD_INGOT)
					.setIngredient('R', Material.ROTTEN_FLESH);

	private static final ShapedRecipe SADDLE =
			new ShapedRecipe(
					new ItemStack(Material.SADDLE, 1, (short) 0))
					.shape("   ", " L ", "L L")
					.setIngredient('L', Material.LEATHER);

	/**
	 * Adds recipes for Custom5 class.
	 */
	public static void addRecipes() {
		MC_Custom_Classes.addRecipe(COW_EGG);
		MC_Custom_Classes.addRecipe(PIG_EGG);
		MC_Custom_Classes.addRecipe(SHEEP_EGG);
		MC_Custom_Classes.addRecipe(CHICKEN_EGG);
		MC_Custom_Classes.addRecipe(HORSE_EGG);
		MC_Custom_Classes.addRecipe(WOLF_EGG);
		MC_Custom_Classes.addRecipe(OCELOT_EGG);
		MC_Custom_Classes.addRecipe(SKELETON_EGG);
		MC_Custom_Classes.addRecipe(ZOMBIE_EGG);
		MC_Custom_Classes.addRecipe(CREEPER_EGG);
		MC_Custom_Classes.addRecipe(SPIDER_EGG);
		MC_Custom_Classes.addRecipe(SADDLE);
	}

	/**
	 * Checks if a recipe is Custom5-only.
	 */
	public static boolean isRecipe(Recipe recipe) {

		if (recipe.getResult().getType() == Material.SADDLE) {
			return true;
		}
		if (recipe.getResult().getType() == Material.MONSTER_EGG) {
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.PIG).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.SHEEP).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.COW).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.CHICKEN).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.HORSE).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.WOLF).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.OCELOT).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.CREEPER).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.SKELETON).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.SPIDER).toItemStack())) {
				return true;
			}
			if (recipe.getResult().isSimilar(new SpawnEgg(EntityType.ZOMBIE).toItemStack())) {
				return true;
			}
		}
		return false;
	}
}