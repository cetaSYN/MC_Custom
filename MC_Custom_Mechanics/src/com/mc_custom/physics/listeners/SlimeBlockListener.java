package com.mc_custom.physics.listeners;

import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class SlimeBlockListener implements BaseListener{

    @EventHandler
    public void sideBounce(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location to = event.getTo();

        //Get Movement Direction
        double diff_x = event.getFrom().getX() - to.getX();
        diff_x *= 4;
        double diff_y = event.getFrom().getY() - to.getY();
        diff_y *= 2;
        double diff_z = event.getFrom().getZ() - to.getZ();
        diff_z *= 4;

        //Check block type
        if (player.getLocation().subtract(diff_x, diff_y, diff_z).getBlock().getType()== Material.SLIME_BLOCK
                && player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.SLIME_BLOCK){
            player.setVelocity(new Vector(diff_x/3, diff_y/2, diff_z/3));
        }
    }
}
