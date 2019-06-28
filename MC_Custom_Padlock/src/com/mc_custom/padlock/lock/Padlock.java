package com.mc_custom.padlock.lock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Padlock {
    private String lock_id;
    private UUID lock_owner;
    private List<UUID> users;
    private String password;
    private int timer;
    private boolean allow_redstone;
    private Block block;
    public Padlock(Block block, UUID lock_owner, List<UUID> users, String password, int timer, boolean allow_redstone){
        this.lock_id = block.getWorld().getName() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ();
        this.block = block;
        this.lock_owner = lock_owner;
        this.users = users;
        this.password = password;
        this.timer = timer;
        this.allow_redstone = allow_redstone;
    }
    public boolean hasPassword(){
        return !password.equalsIgnoreCase("");
    }
    public UUID getOwner(){
        return lock_owner;
    }
    public List<UUID> getUsers(){
        return users;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setTimer(int timer){
        this.timer = timer;
    }
    public void addUser(UUID user){
        users.add(user);
    }
    public boolean isUser(UUID user){
        return users.contains(user);
    }
    public void removeUser(UUID user){
        users.remove(user);
    }
    public int getTimer(){
        return timer;
    }
    public void setAllowsRedstone(boolean allow_redstone){
        this.allow_redstone = allow_redstone;
    }
    public boolean allowsRedstone(){
        return allow_redstone;
    }
    public Block getBlock(){
        return block;
    }
    public String getID(){
        return lock_id;
    }
    public static Block getNeighboringDoorPart(Block block){
        BlockFace[] door_sides = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.EAST};
        for(BlockFace face : door_sides){
            if(block.getRelative(face).getType().equals(block.getType())){
                return block.getRelative(face);
            }
        }
        return null;
    }
    public static Block getAdjascentPrivateBlock(Block block){
        BlockFace[] chest_sides = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.EAST};
        BlockFace[] door_sides = {BlockFace.UP, BlockFace.DOWN};
        if(block.getType().equals(Material.IRON_DOOR_BLOCK) || block.getType().equals(Material.WOODEN_DOOR)){
            for(BlockFace face : door_sides){
                if(block.getRelative(face).getType().equals(block.getType())){
                    return block.getRelative(face);
                }
            }
        }
        if(block.getType().equals(Material.CHEST) || block.getType().equals(Material.TRAPPED_CHEST)){       
            for(BlockFace face : chest_sides){
                if(block.getRelative(face).getType().equals(block.getType())){
                    return block.getRelative(face);
                }
            }
        }
        return null;
    }
    public Map<String, Object> serialize(){
        Map<String, Object> serialized = new HashMap<String, Object>();
        serialized.put("lock_owner", lock_owner);
        serialized.put("users", users);
        serialized.put("password", password);
        serialized.put("timer", timer);
        serialized.put("allow_redstone", allow_redstone);
        serialized.put("world", block.getWorld().getUID());
        serialized.put("x", block.getX());
        serialized.put("y", block.getY());
        serialized.put("z", block.getZ());
        return serialized;
    }
    @SuppressWarnings("unchecked")
    public static Padlock deserialize(Map<String, Object> map){
        return new Padlock(
                Bukkit.getWorld((UUID)map.get("world")).getBlockAt((int)map.get("x"), (int)map.get("y"), (int)map.get("z")),
                (UUID)map.get("lock_owner"),
                (List<UUID>)map.get("users"),
                (String)map.get("password"),
                (int)map.get("timer"),
                (boolean)map.get("allow_redstone")
                );
    }
}
