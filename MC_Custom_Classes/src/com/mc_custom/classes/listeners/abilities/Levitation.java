package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.core.listeners.BaseListener;


//TODO Make this work in a way that doesn't look like an 8 year old made it.
// TODO Tell short is is an idiot for making this class
public class Levitation implements BaseListener {
/*
	private MC_Custom_Classes plugin;
	private PlayerHandler<MCCustomPlayer> player_handler;
	private WorldGuardPlugin world_guard;
	private HashSet<Byte> transparent_blocks = new HashSet<>();
	private Set<Material> disallowed_blocks = new HashSet<>();
	private Random random;

	public Levitation(MC_Custom_Classes plugin) {
		this.plugin = plugin;
		// Should not be null as we checked before loading this listener.
		world_guard = plugin.getWorldGuard();
		player_handler = plugin.getPlayerHandler();
		transparent_blocks.add((byte)0);
		transparent_blocks.add((byte)8);
		transparent_blocks.add((byte)9);
		transparent_blocks.add((byte)10);
		transparent_blocks.add((byte)11);
		// Blocks with physics can be allowed once their physics events can be cancelled
		disallowed_blocks.add(Material.SAND);
		disallowed_blocks.add(Material.ANVIL);
		disallowed_blocks.add(Material.GRAVEL);
		disallowed_blocks.add(Material.BEDROCK);
		disallowed_blocks.add(Material.LAVA);
		disallowed_blocks.add(Material.STATIONARY_LAVA);
		disallowed_blocks.add(Material.WATER);
		disallowed_blocks.add(Material.STATIONARY_WATER);
		random = new Random(System.currentTimeMillis());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void toggleLevitateEnabled(final PlayerInteractEvent event) throws NotOnlineException {
		Player player = event.getPlayer();
		if(player.getItemInHand() != null && player.getItemInHand().getType().equals(Material.BLAZE_ROD)) {
			MCCustomPlayer mc_custom_player = player_handler.getPlayer(player);
			if(mc_custom_player.isLevitationEnabled() && mc_custom_player.isLevitatingBlock()) {
				// Player is releasing a block
				BlockContainer block_data = mc_custom_player.getLevitatedBlock();
				// Get the farthest non-solid block
				Block block = player.getTargetBlock(transparent_blocks, block_data.getDistance());
				// Check if the player can build here
				if(checkRegions(player, block)) {
					dropBlock(player, block, block_data);
					// Remove the player's levitated block
					mc_custom_player.setLevitatedBlock(null);
				}
				else {
					return;
				}
			}
			mc_custom_player.setLevitationEnabled(!mc_custom_player.isLevitationEnabled());
			player.sendMessage(ChatColor.AQUA + "Levitation mode "
					+ (mc_custom_player.isLevitationEnabled() ? "enabled" : "disabled"));
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void updateDistance(final PlayerInteractEvent event) throws NotOnlineException {
		MCCustomPlayer player = player_handler.getPlayer(event.getPlayer());
		if(player.isLevitatingBlock()) {
			BlockContainer block_data = player.getLevitatedBlock();
			block_data.setDistance(getFarthestAirDistance(event.getPlayer()));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void toggleLevitate(final PlayerInteractEvent event) throws NotOnlineException {
		Player player = event.getPlayer();
		MCCustomPlayer mc_custom_player = player_handler.getPlayer(player);
		if(mc_custom_player.hasAbilityInRegion(AbilityType.LEVITATE) && mc_custom_player.isLevitationEnabled()) {
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if(mc_custom_player.isLevitatingBlock()) {
					// Player is releasing a block
					BlockContainer block_data = mc_custom_player.getLevitatedBlock();
					// Get the farthest non-solid block
					Block block = player.getTargetBlock(transparent_blocks, block_data.getDistance());
					// Check if the player can build here
					if(checkRegions(player, block)) {
						dropBlock(player, block, block_data);
						// Remove the player's levitated block
						mc_custom_player.setLevitatedBlock(null);
					}
				}
				else if(player.getItemInHand() != null && player.getItemInHand().getType().isBlock()
						&& !player.getItemInHand().getType().equals(Material.AIR)) {
					// Player is placing a block from their inventory
					int farthest_distance = getFarthestAirDistance(player);
					if(farthest_distance != -1) {
						Block block = player.getTargetBlock(transparent_blocks, farthest_distance);
						if(checkRegions(player, block)) {
							ItemStack item = player.getItemInHand();
							block.setType(item.getType());
							block.setData((byte)player.getItemInHand().getDurability());
							plugin.getServer().getPluginManager().callEvent(
									new BlockPlaceEvent(
											block,
											block.getState(),
											block,
											new ItemStack(block.getType()),
											player,
											true));
							BlockContainer block_data = new BlockContainer(block.getState(), farthest_distance);
							mc_custom_player.setLevitatedBlock(block_data);
							if(!player.getGameMode().equals(GameMode.CREATIVE)) {
								if(item.getAmount() > 1) {
									item.setAmount(item.getAmount() - 1);
								}
								else {
									player.setItemInHand(null);
								}
							}
						}
					}
				}
			}
			else if((event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR))
					&& (player.getFoodLevel() > 10 || player.getGameMode().equals(GameMode.CREATIVE))
					&& !mc_custom_player.isLevitatingBlock()) {
				// Player is grabbing a block
				grabBlock(mc_custom_player, player);
			}
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void moveBlocks(final PlayerMoveEvent event) throws NotOnlineException {
		Player player = event.getPlayer();
		MCCustomPlayer mc_custom_player = player_handler.getPlayer(player);
		if(mc_custom_player.isLevitatingBlock()) {
			if(player.getFoodLevel() > 10 || player.getGameMode().equals(GameMode.CREATIVE)) {
				BlockContainer block_data = mc_custom_player.getLevitatedBlock();
				if(!player.getTargetBlock(transparent_blocks, block_data.getDistance()).getLocation()
						.equals(block_data.getLocation())) {
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, random.nextFloat());
					if(!player.getGameMode().equals(GameMode.CREATIVE)) {
						player.setExhaustion(event.getPlayer().getExhaustion() + 0.1f);
					}
					// Check if the player can build here
					if(checkRegions(player, player.getWorld().getBlockAt(block_data.getLocation()))) {
						player.getWorld().getBlockAt(block_data.getLocation()).setType(Material.AIR);
						// Get the farthest non-solid block
						int farthest_distance = getFarthestAirDistance(player);
						if(farthest_distance != -1) {
							Block block = event.getPlayer().getTargetBlock(transparent_blocks, farthest_distance);
							block.setTypeIdAndData(block_data.getType().getId(), block_data.getOrCreate(), false);
							block_data.setLocation(block.getLocation());
							block_data.setDistance(farthest_distance);
						}
					}
				}
			}
			else {
				BlockContainer block_data = mc_custom_player.getLevitatedBlock();
				Block block = player.getTargetBlock(transparent_blocks, block_data.getDistance());
				dropBlock(player, block, block_data);
				mc_custom_player.setLevitatedBlock(null);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void cancelPhysics(final BlockPhysicsEvent event) {
		//TODO: Cancel physics on levitated block
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void cancelRedstone(final BlockRedstoneEvent event) {
		//TODO: Cancel redstone on levitated blocks
	}

	private void dropBlock(Player player, Block block, BlockContainer block_data) {
		// If the block is a container, just leave it where it is to preserve the inventory
		if(!block_data.isContainer()) {
			block.setType(Material.AIR);
			player.getWorld().getBlockAt(block_data.getLocation()).setType(Material.AIR);
			FallingBlock falling_block = player.getWorld().spawnFallingBlock(block_data.getLocation(),
					block_data.getType(), block_data.getOrCreate());
			falling_block.setDropItem(true);
		}
		// Otherwise, just leave it in place to preserve the block's inventory
		else {
			block.setType(block_data.getType());
			block.setData(block_data.getOrCreate());
			// Spoof a BlockPlaceEvent to enable logging
			plugin.getServer().getPluginManager().callEvent(
					new BlockPlaceEvent(
							block,
							block.getState(),
							block,
							new ItemStack(block_data.getType()),
							player,
							true));
			// Chests, furnaces, droppers, etc.
			if(block_data.isContainer()) {
				ItemStack[] inventory = block_data.getContents();
				ContainerBlock container = (ContainerBlock)block.getState();
				int offset = 0;
				// Properly deal with double chests
				if(container.getInventory().getSize() == 54) {
					DoubleChest double_chest = (DoubleChest)container.getInventory().getHolder();
					Chest right_side = (Chest)double_chest.getRightSide();
					if(right_side.getLocation().distance(block.getLocation()) == 0) {
						offset = 27;
					}
				}
				// Inventory.setContents() is rather unreliable
				for(int i = 0; i < inventory.length; i++) {
					if(inventory[i] != null) {
						container.getInventory().setItem(i + offset, inventory[i]);
					}
				}
			}
		}
	}

	private void grabBlock(MCCustomPlayer mc_custom_player, Player player) {
		Block block = player.getTargetBlock(transparent_blocks, 5);
		// Make sure the block isn't protected
		if(block != null && checkRegions(player, block) && !disallowed_blocks.contains(block.getType())) {
			// Create the block info container
			BlockContainer block_data = new BlockContainer(block.getState(), 5);
			// Spoof a BlockBreakEvent to enable logging
			plugin.getServer().getPluginManager().callEvent(new BlockBreakEvent(block, player));
			// Chests, furnaces, droppers, etc.
			if(block.getState() instanceof ContainerBlock) {
				ContainerBlock container = (ContainerBlock)block.getState();
				Inventory inventory = container.getInventory();
				// Properly deal with double chests
				if(inventory.getSize() == 54) {
					DoubleChestInventory double_inventory = (DoubleChestInventory)inventory;
					Chest left_side = (Chest)double_inventory.getLeftSide().getHolder();
					// Empty the moved chest's inventory so the items don't drop
					if(left_side.getLocation().distanceSquared(block.getLocation()) == 0) {
						double_inventory.getLeftSide().clear();
					}
					else {
						double_inventory.getRightSide().clear();
					}
				}
				else {
					inventory.clear();
				}
			}
			// Set the player's levitated block
			mc_custom_player.setLevitatedBlock(block_data);
		}
	}

	private int getFarthestAirDistance(Player player) {
		for(int i = 5; i > 0; i--) {
			Block block = player.getTargetBlock(transparent_blocks, i);
			if(block.isEmpty() || block.isLiquid()) {
				return i;
			}
		}
		return -1;
	}

	private boolean checkRegions(Player player, Block block) {
		// Checking for region protection goes here
		return world_guard.canBuild(player, block)
				&& (GriefPrevention.instance.allowBreak(player, block, block.getLocation()) != null)
				&& (GriefPrevention.instance.allowBuild(player, block.getLocation()) != null);
	}*/
}
