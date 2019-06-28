package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.classes.recipes.*;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public CraftingListener(){
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}

	@SuppressWarnings("deprecation")
	//NO alternatives exist
	@EventHandler
	public void crafting(PrepareItemCraftEvent event){
		Player player = (Player)event.getView().getPlayer();
		try{
			if(Custom2Recipes.isRecipe(event.getRecipe())
					&& (!(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.CRAFT_BAT)))){
				event.getInventory().setResult(new ItemStack(Material.AIR));
			}
			else if(Custom5Recipes.isRecipe(event.getRecipe())
					&& (!(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.CRAFT_EGG)))){
				event.getInventory().setResult(new ItemStack(Material.AIR));
			}
			else if(Custom1Recipes.isRecipe(event.getRecipe())
					&& (!(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.CRAFT_VILLAGER)))){
				event.getInventory().setResult(new ItemStack(Material.AIR));
			}
			else if(Custom9Recipes.isRecipe(event.getRecipe())
					&& (!(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.CRAFT_HORSE_ARMOR)))){
				event.getInventory().setResult(new ItemStack(Material.AIR));
			}
			else if(Custom8Recipes.isRecipe(event.getRecipe())
					&& (!(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.CRAFT_ICE)))){
				event.getInventory().setResult(new ItemStack(Material.AIR));
			}
			else if(Custom7Recipes.isRecipe(event.getRecipe())
					&& (!(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.WEATHER_CHANGE)))){
				event.getInventory().setResult(new ItemStack(Material.AIR));
			}
			else if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.AQUA_AFFINITY)){
				ItemStack item = event.getInventory().getResult();
				if(item.getType() == Material.LEATHER_HELMET
						|| item.getType() == Material.GOLD_HELMET
						|| item.getType() == Material.IRON_HELMET
						|| item.getType() == Material.DIAMOND_HELMET){

					item.addEnchantment(Enchantment.WATER_WORKER, 1);
					event.getInventory().setResult(item);
				}
			}
		}
		catch(NotOnlineException ex){
			ex.printStackTrace();
		}
	}
}