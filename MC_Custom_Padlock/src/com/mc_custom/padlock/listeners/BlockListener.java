package com.mc_custom.padlock.listeners;

import java.util.List;

import com.mc_custom.padlock.MC_Custom_Padlock;
import com.mc_custom.padlock.lock.Padlock;
import com.mc_custom.padlock.lock.PadlockManager;

public class BlockListener implements Listener{
    private MC_Custom_Padlock plugin;
    private PadlockManager<Padlock> padlock_manager;
    public BlockListener(MC_Custom_Padlock plugin){
        this.plugin = plugin;
        this.padlock_manager = plugin.getPadlockManager();
    }
    @EventHandler (priority = EventPriority.HIGHEST)
    public void checkLock(final PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(!padlock_manager.hasPadlock(block)){
            block = Padlock.getAdjascentPrivateBlock(block);
        }
        if(!padlock_manager.hasPadlock(block) && (block.getType().equals(Material.WOODEN_DOOR) || block.getType().equals(Material.IRON_DOOR_BLOCK))){
            block = Padlock.getNeighboringDoorPart(block);
        }
        if(!padlock_manager.hasPadlock(block)){
            block = Padlock.getAdjascentPrivateBlock(block);
        }
        Player player = event.getPlayer();
        if(padlock_manager.hasPadlock(block)){
            Padlock lock = padlock_manager.getPadlock(block);
            if(lock.getOwner().equals(player.getUniqueId())
                    || lock.getUsers().contains(player.getUniqueId())){
                //Do nothing. The player is allowed to use the padlock
            }
            else if(player.hasMetadata("padlockOverride")){
                if(player.getMetadata("padlockOverride").get(0).asBoolean()){
                    for(Player check_player : Bukkit.getOnlinePlayers()){
                        if(check_player.hasPermission("Padlock.override")){
                            check_player.sendMessage(ChatColor.RED + "[Padlock] " + player.getName()
                                    + " has opened a(n) " + block.getType() + " owned by "
                                    + Bukkit.getPlayer(lock.getOwner()).getName() + " at " + lock.getID());
                        }
                    }
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "[Padlock] This block is private!");
                event.setCancelled(true);
            }
        }
    }
    @EventHandler (priority = EventPriority.HIGHEST)
    public void stopRedstone(final BlockRedstoneEvent event){
        Block block = event.getBlock();
        if(padlock_manager.hasPadlock(block)){
            Padlock lock = padlock_manager.getPadlock(block);
            if(!lock.allowsRedstone()){
                event.setNewCurrent(event.getOldCurrent());
            }
        }
    }
    @EventHandler (priority = EventPriority.HIGHEST)
    public void stopPiston(final BlockPistonRetractEvent event){
        Block block = event.getBlock().getRelative(event.getDirection());
        if(padlock_manager.hasPadlock(block)){
            event.setCancelled(true);
        }
    }
    @EventHandler (priority = EventPriority.HIGHEST)
    public void stopPiston(final BlockPistonExtendEvent event){
        List<Block> blocks = event.getBlocks();
        for(Block block : blocks){
            if(padlock_manager.hasPadlock(block)){
                event.setCancelled(true);
            }
        }
    }
}
