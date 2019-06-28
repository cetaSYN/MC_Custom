package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DrownImmunity implements BaseListener {

    private PlayerHandler<MCCustomPlayer> player_handler;

    public DrownImmunity(  ) {
        player_handler = MC_Custom_Classes.getPlayerHandler();
    }

    @EventHandler
    public void waterImmunity( EntityDamageEvent event ) {
        if (event.getEntity() instanceof Player) {

            Player player = ( Player ) event.getEntity();

            if (event.getCause() == DamageCause.DROWNING) {
                try {
                    if (player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.DROWN_IMMUNITY)) {
                        event.setDamage(0.0);
                        event.setCancelled(true);
                    }
                }
                catch (NotOnlineException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}