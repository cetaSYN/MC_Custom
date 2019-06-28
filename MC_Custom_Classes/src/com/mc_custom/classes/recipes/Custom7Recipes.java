package com.mc_custom.classes.recipes;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.core.utils.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles recipes for Custom7 class.
 */
public class Custom7Recipes {
	public static final short ATMOSPHERIC_BALANCE_DAMAGE = 7;
	public static final short RAINWATER_DAMAGE = 23;
	public static final short CHARGED_RAINWATER_DAMAGE = 15;
	public static final short CLEAR_SKIES_DAMAGE = 31;
	public static final ItemStack ATMOSPHERIC_BALANCE_ITEM = new ItemStack(Material.POTION, 1, ATMOSPHERIC_BALANCE_DAMAGE);
	public static final ItemStack RAINWATER_ITEM = new ItemStack(Material.POTION, 1, RAINWATER_DAMAGE);
	public static final ItemStack CHARGED_RAINWATER_ITEM = new ItemStack(Material.POTION, 1, CHARGED_RAINWATER_DAMAGE);
	public static final ItemStack CLEAR_SKIES_ITEM = new ItemStack(Material.POTION, 1, CLEAR_SKIES_DAMAGE);
	private static final ShapelessRecipe CLEAR_SKIES;
	private static final ShapelessRecipe ATMOSPHERIC_BALANCE_A;
	private static final ShapelessRecipe ATMOSPHERIC_BALANCE_B;
	private static final ShapelessRecipe ATMOSPHERIC_BALANCE_C;
	private static final ShapelessRecipe RAINWATER;
	private static final ShapelessRecipe CHARGED_RAINWATER;
	public static final String WEATHER_ITEM_NAME = ChatColor.YELLOW + "" + ChatColor.ITALIC + "Bottled Weather";

	static {
		// Set rainwater meta
		PotionMeta meta = (PotionMeta)RAINWATER_ITEM.getItemMeta();
		List<String> lore = new ArrayList<>(4);
		meta.setDisplayName(WEATHER_ITEM_NAME);
		lore.add(ChatColor.BLUE + "Rainwater");
		lore.add("It's rather wet");
		meta.setLore(lore);
		RAINWATER_ITEM.setItemMeta(meta);

		// Set charged rainwater meta
		meta = (PotionMeta)CHARGED_RAINWATER_ITEM.getItemMeta();
		lore = new ArrayList<>(4);
		meta.setDisplayName(WEATHER_ITEM_NAME);
		lore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Charged Rainwater");
		lore.add("Shocking!");
		meta.setLore(lore);
		CHARGED_RAINWATER_ITEM.setItemMeta(meta);
		//CHARGED_RAINWATER_ITEM.setDurability(CHARGED_RAINWATER_DAMAGE);

		// Set atmospheric balance meta
		meta = (PotionMeta)ATMOSPHERIC_BALANCE_ITEM.getItemMeta();
		lore = new ArrayList<>(4);
		meta.setDisplayName(WEATHER_ITEM_NAME);
		lore.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Atmospheric Balance");
		lore.add("Returns the weather to its natural state");
		meta.setLore(lore);
		ATMOSPHERIC_BALANCE_ITEM.setItemMeta(meta);
		//ATMOSPHERIC_BALANCE_ITEM.setDurability(ATMOSPHERIC_BALANCE_DAMAGE);

		// Set clear skies meta
		meta = (PotionMeta)CLEAR_SKIES_ITEM.getItemMeta();
		lore = new ArrayList<>(4);
		meta.setDisplayName(WEATHER_ITEM_NAME);
		lore.add(ChatColor.GOLD + "Clear Skies");
		lore.add("Lovely weather for a picnic");
		meta.setLore(lore);
		CLEAR_SKIES_ITEM.setItemMeta(meta);
		//CLEAR_SKIES_ITEM.setDurability(CLEAR_SKIES_DAMAGE);

		RAINWATER = new ShapelessRecipe(
				RAINWATER_ITEM)
				.addIngredient(3, Material.FEATHER)
				.addIngredient(new MaterialData(Material.POTION, (byte) 0));
		CHARGED_RAINWATER = new ShapelessRecipe(
				CHARGED_RAINWATER_ITEM)
				.addIngredient(3, Material.REDSTONE)
				.addIngredient(RAINWATER_ITEM.getData());
		CLEAR_SKIES = new ShapelessRecipe(CLEAR_SKIES_ITEM)
				.addIngredient(3, Material.BLAZE_POWDER)
				.addIngredient(RAINWATER_ITEM.getData());
		ATMOSPHERIC_BALANCE_A = new ShapelessRecipe(ATMOSPHERIC_BALANCE_ITEM)
				.addIngredient(3, Material.FEATHER)
				.addIngredient(RAINWATER_ITEM.getData());
		ATMOSPHERIC_BALANCE_B = new ShapelessRecipe(ATMOSPHERIC_BALANCE_ITEM)
				.addIngredient(3, Material.FEATHER)
				.addIngredient(CHARGED_RAINWATER_ITEM.getData());
		ATMOSPHERIC_BALANCE_C = new ShapelessRecipe(ATMOSPHERIC_BALANCE_ITEM)
				.addIngredient(3, Material.FEATHER)
				.addIngredient(CLEAR_SKIES_ITEM.getData());
	}


	/**
	 * Adds recipes for Custom7 class.
	 */
	public static void addRecipes() {
		MC_Custom_Classes.addRecipe(RAINWATER);
		//MC_Custom_Classes.addRecipe(CHARGED_RAINWATER);
		MC_Custom_Classes.addRecipe(CLEAR_SKIES);
		MC_Custom_Classes.addRecipe(ATMOSPHERIC_BALANCE_A);
		//MC_Custom_Classes.addRecipe(ATMOSPHERIC_BALANCE_B);
		MC_Custom_Classes.addRecipe(ATMOSPHERIC_BALANCE_C);
	}

	/**
	 * Checks if a recipe is Custom7-only.
	 */
	public static boolean isRecipe(Recipe recipe) {
		ItemStack result = recipe.getResult();
		return result.isSimilar(RAINWATER_ITEM)
				//|| result.isSimilar(CHARGED_RAINWATER_ITEM)
				|| result.isSimilar(CLEAR_SKIES_ITEM)
				|| result.isSimilar(ATMOSPHERIC_BALANCE_ITEM);
	}
}