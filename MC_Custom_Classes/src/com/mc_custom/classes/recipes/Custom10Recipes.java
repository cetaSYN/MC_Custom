package com.mc_custom.classes.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.Recipe;

public class Custom10Recipes {

	/*
	 * TODO: All potion recipes
	 */
	/*
    private static final ShapelessRecipe AWKWARD_POTION = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.AWKWARD)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.WATER_BOTTLE)))
                .addIngredient(new MaterialData(Material.NETHER_STALK));
    
    private static final ShapelessRecipe POTION_OF_STRENGTH = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.STRENGTH)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.AWKWARD)))
                .addIngredient(new MaterialData(Material.BLAZE_POWDER));
    
    private static final ShapelessRecipe POTION_OF_STRENGTH_II_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.STRENGTH_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.STRENGTH)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_STRENGTH_II_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.STRENGTH_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.STRENGTH_EXTENDED)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_STRENGTH_EXTENDED_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.STRENGTH_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.STRENGTH)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_STRENGTH_EXTENDED_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.STRENGTH_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.STRENGTH_II)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.AWKWARD)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.STRENGTH)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_C = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.REGENERATION)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_D = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.STRENGTH_II)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_E = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.REGENERATION_II)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_F = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.THICK)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_G = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.MUNDANE)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_H = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.WEAKNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_EXTENDED_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.WEAKNESS)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_EXTENDED_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.REGENERATION_EXTENDED)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_WEAKNESS_EXTENDED_C = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.WEAKNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.STRENGTH_EXTENDED)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_REGENERATION = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.REGENERATION)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.AWKWARD)))
                .addIngredient(new MaterialData(Material.GHAST_TEAR));
    
    private static final ShapelessRecipe POTION_OF_REGENERATION_EXTENDED_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.REGENERATION_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.REGENERATION)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_REGENERATION_EXTENDED_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.REGENERATION_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.REGENERATION_II)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_REGENERATION_II_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.REGENERATION_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.REGENERATION)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_REGENERATION_II_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.REGENERATION_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.REGENERATION_EXTENDED)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_POISON = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.POISON)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.AWKWARD)))
                .addIngredient(new MaterialData(Material.SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_POISON_II_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.POISON_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.POISON)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_POISON_II_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.POISON_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.POISON_EXTENDED)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_POISON_EXTENDED_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.POISON_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.POISON)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_POISON_EXTENDED_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.POISON_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.POISON_II)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_HARMING_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HARMING)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.POISON)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_HARMING_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HARMING)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.POISON_EXTENDED)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_HARMING_C = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HARMING)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.HEALING)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_HARMING_D = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HARMING)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.HARMING_II)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_HARMING_II_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HARMING_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.POISON_II)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_HARMING_II_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HARMING_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.HEALING_II)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_HARMING_II_C = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HARMING)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.POISON_II)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_HEALING_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HEALING)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.AWKWARD)))
                .addIngredient(new MaterialData(Material.SPECKLED_MELON));
    
    private static final ShapelessRecipe POTION_OF_HEALING_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HEALING)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.HEALING_II)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_HEALING_II = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.HEALING_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.HEALING)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_SWIFTNESS = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SWIFTNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.AWKWARD)))
                .addIngredient(new MaterialData(Material.SUGAR));
    
    private static final ShapelessRecipe POTION_OF_SWIFTNESS_II_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SWIFTNESS_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.SWIFTNESS)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_SWIFTNESS_II_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SWIFTNESS_II)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.SWIFTNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_SWIFTNESS_EXTENDED_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SWIFTNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.SWIFTNESS)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_SWIFTNESS_EXTENDED_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SWIFTNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.SWIFTNESS_II)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_SLOWNESS_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SLOWNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.SWIFTNESS)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_SLOWNESS_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SLOWNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.FIRE_RESISTANCE)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_SLOWNESS_C = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SLOWNESS)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.SLOWNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_SLOWNESS_EXTENDED_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SLOWNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.FIRE_RESISTANCE_EXTENDED)))
                .addIngredient(new MaterialData(Material.FERMENTED_SPIDER_EYE));
    
    private static final ShapelessRecipe POTION_OF_SLOWNESS_EXTENDED_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.SLOWNESS_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.SLOWNESS)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    
    private static final ShapelessRecipe POTION_OF_FIRE_RESISTANCE_A = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.FIRE_RESISTANCE)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.AWKWARD)))
                .addIngredient(new MaterialData(Material.MAGMA_CREAM));
    
    private static final ShapelessRecipe POTION_OF_FIRE_RESISTANCE_B = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.FIRE_RESISTANCE)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.FIRE_RESISTANCE_EXTENDED)))
                .addIngredient(new MaterialData(Material.GLOWSTONE_DUST));
    
    private static final ShapelessRecipe POTION_OF_FIRE_RESISTANCE_EXTENDED = 
            new ShapelessRecipe(
                new ItemStack(Material.POTION, 1, PotionData.getVal(PotionData.FIRE_RESISTANCE_EXTENDED)))
                .addIngredient(new MaterialData(Material.POTION, PotionData.getVal(PotionData.FIRE_RESISTANCE)))
                .addIngredient(new MaterialData(Material.REDSTONE));
    */
	public static void addRecipes() {
        /*
        MC_Custom_Classes.addRecipe(AWKWARD_POTION);
        MC_Custom_Classes.addRecipe(POTION_OF_STRENGTH);
        MC_Custom_Classes.addRecipe(POTION_OF_STRENGTH_II_A);
        MC_Custom_Classes.addRecipe(POTION_OF_STRENGTH_II_B);
        MC_Custom_Classes.addRecipe(POTION_OF_STRENGTH_EXTENDED_A);
        MC_Custom_Classes.addRecipe(POTION_OF_STRENGTH_EXTENDED_B);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_A);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_B);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_C);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_D);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_E);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_F);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_G);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_H);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_EXTENDED_A);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_EXTENDED_B);
        MC_Custom_Classes.addRecipe(POTION_OF_WEAKNESS_EXTENDED_C);
        MC_Custom_Classes.addRecipe(POTION_OF_REGENERATION);
        MC_Custom_Classes.addRecipe(POTION_OF_REGENERATION_II_A);
        MC_Custom_Classes.addRecipe(POTION_OF_REGENERATION_II_B);
        MC_Custom_Classes.addRecipe(POTION_OF_REGENERATION_EXTENDED_A);
        MC_Custom_Classes.addRecipe(POTION_OF_REGENERATION_EXTENDED_B);
        MC_Custom_Classes.addRecipe(POTION_OF_POISON);
        MC_Custom_Classes.addRecipe(POTION_OF_POISON_EXTENDED_A);
        MC_Custom_Classes.addRecipe(POTION_OF_POISON_EXTENDED_B);
        MC_Custom_Classes.addRecipe(POTION_OF_POISON_II_A);
        MC_Custom_Classes.addRecipe(POTION_OF_POISON_II_B);
        MC_Custom_Classes.addRecipe(POTION_OF_HARMING_A);
        MC_Custom_Classes.addRecipe(POTION_OF_HARMING_B);
        MC_Custom_Classes.addRecipe(POTION_OF_HARMING_C);
        MC_Custom_Classes.addRecipe(POTION_OF_HARMING_D);
        MC_Custom_Classes.addRecipe(POTION_OF_HARMING_II_A);
        MC_Custom_Classes.addRecipe(POTION_OF_HARMING_II_B);
        MC_Custom_Classes.addRecipe(POTION_OF_HARMING_II_C);
        MC_Custom_Classes.addRecipe(POTION_OF_HEALING_A);
        MC_Custom_Classes.addRecipe(POTION_OF_HEALING_B);
        MC_Custom_Classes.addRecipe(POTION_OF_HEALING_II);
        MC_Custom_Classes.addRecipe(POTION_OF_SWIFTNESS);
        MC_Custom_Classes.addRecipe(POTION_OF_SWIFTNESS_EXTENDED_A);
        MC_Custom_Classes.addRecipe(POTION_OF_SWIFTNESS_EXTENDED_B);
        MC_Custom_Classes.addRecipe(POTION_OF_SWIFTNESS_II_A);
        MC_Custom_Classes.addRecipe(POTION_OF_SWIFTNESS_II_B);
        MC_Custom_Classes.addRecipe(POTION_OF_SLOWNESS_A);
        MC_Custom_Classes.addRecipe(POTION_OF_SLOWNESS_B);
        MC_Custom_Classes.addRecipe(POTION_OF_SLOWNESS_C);
        MC_Custom_Classes.addRecipe(POTION_OF_SLOWNESS_EXTENDED_A);
        MC_Custom_Classes.addRecipe(POTION_OF_SLOWNESS_EXTENDED_B);
        MC_Custom_Classes.addRecipe(POTION_OF_FIRE_RESISTANCE_A);
        MC_Custom_Classes.addRecipe(POTION_OF_FIRE_RESISTANCE_B);
        MC_Custom_Classes.addRecipe(POTION_OF_FIRE_RESISTANCE_EXTENDED);
        */
	}

	/**
	 * Checks if a recipe is Custom10-only.
	 */
	public static boolean isRecipe(Recipe recipe) {
		return (recipe.getResult().getType() == Material.POTION && recipe.getResult().getDurability() != (short) 16);
	}
}
