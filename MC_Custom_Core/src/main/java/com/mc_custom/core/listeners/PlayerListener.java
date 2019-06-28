package com.mc_custom.core.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.players.BasePlayer;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.ImmutableBlockState;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.core.utils.Utils;
import com.mc_custom.core.utils.chatbuilders.ChatBuilder;
import com.mc_custom.core.utils.chatbuilders.ReflectionBuilder;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;


public class PlayerListener implements BaseListener {

	private final PlayerHandler<CorePlayer> player_handler;

	public PlayerListener() {
		player_handler = MC_Custom_Core.getPlayerHandler();
	}

	@EventHandler
	public void playerDeath(PlayerDeathEvent event) {
		CorePlayer player = player_handler.getPlayerSilently(event.getEntity());
		if (player != null && player.hasPermission("core.teleport")) {
			player.setBackLocation(Utils.getSafeDestination(player.getLocation()));
		}
	}

	@EventHandler
	public void playerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			CorePlayer player = player_handler.getPlayerSilently((Player) event.getEntity());
			if (player != null && player.hasPermission("core.godmode") && player.getGodMode()) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void playerMessage(AsyncPlayerChatEvent event) {
		if (PermissionUtils.hasWildcardPermission(event.getPlayer(), "core.chatcolor")) {
			String message = event.getMessage();
			event.setMessage(ChatColor.convertColor(message));
		}
	}

	@EventHandler
	public void cancelAFK(final PlayerMoveEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final PlayerInteractEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final PlayerInteractEntityEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final AsyncPlayerChatEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final PlayerChatTabCompleteEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final PlayerItemHeldEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final PlayerDropItemEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final PlayerCommandPreprocessEvent event) {
		if (!event.getMessage().split(" ", 2)[0].equalsIgnoreCase("/afk")) {
			doCancelAFK(event);
		}
	}

	@EventHandler
	public void cancelAFK(final PlayerRespawnEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final PlayerTeleportEvent event) {
		doCancelAFK(event);
	}

	@EventHandler
	public void cancelAFK(final PlayerToggleSneakEvent event) {
		doCancelAFK(event);
	}

	private void doCancelAFK(PlayerEvent event) {
		try {
			CorePlayer player = player_handler.getPlayer(event.getPlayer());
			boolean show_message = player.isAFK();
			player.setAFK(false);
			if (show_message) {
				player.sendMessage(ChatColor.AQUA + "You are no longer AFK");
			}
		}
		catch (NotOnlineException ex){
			ex.printStackTrace();
		}
	}

	@EventHandler
	public void invseeListener(final InventoryClickEvent event) {
		HumanEntity human = event.getWhoClicked();
		InventoryHolder inven_holder = event.getInventory().getHolder();
		if (event.getInventory().getType() == InventoryType.PLAYER && human instanceof Player && inven_holder instanceof Player) {
			Player player1 = (Player) human;
			Player player2 = (Player) inven_holder;
			if (player1 != player2 && !PermissionUtils.hasWildcardPermission(player1, "core.invsee.edit")) {
				// if they are both players, and they are not the same player, and they are clicking in an inventory
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void colorSigns(final SignChangeEvent event) {
		for (int i = 0; i < event.getLines().length; i++) {
			event.setLine(i, ChatColor.convertColor(event.getLine(i)));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void usePowertool(final PlayerInteractEvent event) {
		if (event.isCancelled() && !event.getAction().equals(Action.LEFT_CLICK_AIR)
				&& !event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			return;
		}
		CorePlayer player = player_handler.getPlayerSilently(event.getPlayer());
		Material tool = event.getMaterial();
		if (tool != null && !tool.isBlock()) {
			String[] commands = player.getPowertool(tool);
			if (commands != null) {
				for (String powertool : commands) {
					player.chat("/" + powertool);
				}
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void updateBuilder(final AsyncPlayerChatEvent event) throws NotOnlineException {
		BasePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(event.getPlayer());
		if (event.isCancelled() || !player.isBuilding()) {
			return;
		}
		ChatBuilder chatBuilder = player.getChatBuilder();
		try {
			chatBuilder.update(event.getMessage());
		}
		catch (Exception e) {
			player.sendMessage(ChatColor.RED + ((e.getLocalizedMessage() != null)
					? (e.getClass().getSimpleName() + ": " + e.getLocalizedMessage()) : e.getClass().getSimpleName()));
		}
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void suggestFieldsAndMethods(final PlayerChatTabCompleteEvent event) throws NotOnlineException {
		BasePlayer player = player_handler.getPlayer(event.getPlayer());
		if (player.isBuilding() && player.getChatBuilder() instanceof ReflectionBuilder) {
			event.getTabCompletions().clear();
			ReflectionBuilder builder = (ReflectionBuilder) player.getChatBuilder();
			try {
				for (String field : builder.listFields()) {
					if (field.toLowerCase().contains(event.getLastToken().toLowerCase())) {
						event.getTabCompletions().add(field);
					}
				}
			}
			catch (NoPermissionException e) {
				// Do nothing
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void placeCommandBlock(final PlayerInteractEvent event) throws NotOnlineException {
		BasePlayer player = player_handler.getPlayer(event.getPlayer());
		if (player.getItemInHand().getType().equals(Material.COMMAND)
				&& player.hasPermission("core.commandblock")
				&& event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				&& event.isBlockInHand()) {
			Material clicked_block = event.getClickedBlock().getType();
			// Clicking on these blocks will open up an inventory, so don't place the block
			if (event.getClickedBlock().getState() instanceof InventoryHolder
					|| clicked_block.equals(Material.COMMAND)
					|| clicked_block.equals(Material.WORKBENCH)
					|| clicked_block.equals(Material.ENDER_CHEST)
					|| clicked_block.equals(Material.ENCHANTMENT_TABLE)) {
				return;
			}
			Block block = event.getClickedBlock().getRelative(event.getBlockFace());
			final ImmutableBlockState old_state = new ImmutableBlockState(block.getState());
			block.setType(Material.COMMAND);
			BlockPlaceEvent place_event = new BlockPlaceEvent(block, old_state, event.getClickedBlock(), player.getItemInHand(), event.getPlayer(), true);
			MC_Custom_Core.callEvent(place_event);
			if (place_event.isCancelled()) {
				block.setType(old_state.getType());
			}
		}
	}

	private boolean isPlacing(InventoryAction action) {
		System.out.println(action);
		switch (action) {
			case PLACE_ALL:
			case PLACE_SOME:
			case PLACE_ONE:
			case SWAP_WITH_CURSOR:
			case DROP_ALL_CURSOR:
			case DROP_ONE_CURSOR:
			case DROP_ALL_SLOT:
			case DROP_ONE_SLOT:
				return true;
			default:
				return false;
		}
	}
}
