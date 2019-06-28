package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.classes.utils.EntityMetadata;
import com.mc_custom.classes.utils.UtilityBlock;
import com.mc_custom.classes.utils.UtilityManager;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.mc_custom.core.utils.ChatColor.RED;

public class MobileBrewing implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;
	private UtilityManager utility_manager;

	public MobileBrewing() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
		this.utility_manager = MC_Custom_Classes.getUtilityManager();
	}

	@EventHandler
	public void mobileBrew(final PlayerInteractEvent event) {
		Player player = event.getPlayer();
		try {
			if (/*Replace this with whatever check for mobile brewing*/true) {
				if (player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.MOBILE_BREWING)) {
					if (utility_manager.hasUtility(player.getName())) {
						// Get the player's utility
						UtilityBlock utility = utility_manager.getUtility(player.getName());
						if (utility.getUtilityType().equals(InventoryType.BREWING)) {
							// Open the inventory
							Block block = utility.getBlock();
							block.setMetadata("isUtility", new EntityMetadata(MC_Custom_Classes.getInstance(), true));
							BrewingStand brew = (BrewingStand) block.getState();
							player.openInventory(brew.getInventory());
						}
						else {
							player.sendMessage(RED + "You already have a " + utility.getUtilityType()
									+ " utility open");
						}
					}
					else {
						// Get the block at y=1 below the player
						Location location = new Location(player.getWorld(), player.getLocation().getBlockX(), 1,
								player.getLocation().getBlockZ());
						Block block = player.getWorld().getBlockAt(location);
						// Make sure that the block isn't someone else's utility
						while (block.hasMetadata("isUtility")) {
							location = location.add(0, 0, 1);
							block = player.getWorld().getBlockAt(location);
						}
						// Add the utility
						block.setMetadata("isUtility", new EntityMetadata(MC_Custom_Classes.getInstance(), true));
						utility_manager.addUtility(player.getName(), block, InventoryType.BREWING);
						block.setType(Material.BREWING_STAND);
						// Open the inventory
						BrewingStand brew = (BrewingStand) block.getState();
						player.openInventory(brew.getInventory());
					}
				}
			}
		}
		catch (NotOnlineException e) {
			e.printStackTrace();
		}
	}
}