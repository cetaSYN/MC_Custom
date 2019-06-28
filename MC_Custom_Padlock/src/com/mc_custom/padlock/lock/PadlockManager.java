package com.mc_custom.padlock.lock;

import java.util.HashMap;

public class PadlockManager<P extends Padlock> {
    private final HashMap<String, P> padlocks = new HashMap<>();
    public PadlockManager(){
        /*
         * TODO: Load padlocks from database using Padlock.deserialize()
         *  for(Map<String, Object> lock : Some JSON bullshit that gives a list of Map<String, Object>){
         *      addPadlock(Padlock.deserialize(lock));
         *  }
         */
    }
    
    public void addPadlock(P padlock){
        padlocks.put(padlock.getID(), padlock);
    }
    
    public void removePadlock(String lock_id){
        padlocks.remove(lock_id);
    }
    
    public void removePadlock(Block block){
        padlocks.remove(block.getWorld().getName() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ());
    }
    
    public void removePadlock(Location location){
        padlocks.remove(location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ());
    }
    
    public P getPadlock(String lock_id){
        return padlocks.get(lock_id);
    }
    
    public P getPadlock(Block block){
        return padlocks.get(block.getWorld().getName() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ());
    }
    
    public P getPadlock(Location location){
        return padlocks.get(location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ());
    }
    
    public boolean hasPadlock(Block block){
        return padlocks.containsKey(block.getWorld().getName() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ());
    }
}
