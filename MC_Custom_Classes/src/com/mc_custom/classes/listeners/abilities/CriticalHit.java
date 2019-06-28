package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class CriticalHit implements BaseListener {

    private PlayerHandler<MCCustomPlayer> player_handler;

    public CriticalHit(  ) {
        player_handler = MC_Custom_Classes.getPlayerHandler();
    }

    @EventHandler
    public void criticalHit( final EntityDamageByEntityEvent event ) {
        if (event.getDamager() instanceof Player) {
            Player player = ( Player ) event.getDamager();
            try {
                if (player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.CRITICAL_HIT)
                        && player.getLocation().getBlock().getLightLevel() <= 7) {
                    Random rand = new Random();
                    event.setDamage(rand.nextInt(( int ) (event.getDamage() / 2 + 1 + event
                            .getDamage())) + 1.0);
                }
            }
            catch (NotOnlineException e) {
                e.printStackTrace();
            }
        }
    }
}