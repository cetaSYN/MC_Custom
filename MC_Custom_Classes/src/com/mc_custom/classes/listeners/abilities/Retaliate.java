package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.core.listeners.BaseListener;

public class Retaliate implements BaseListener {


	public Retaliate() {

	}

    /*@EventHandler //Stahp it
	public void retaliate(final EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player) {
           Player player = ( Player ) event.getEntity();
           try {
              if (player_handler.getPlayer(player.getName()).getMCCustomClass()
                    .hasAbility(AbilityType.RETALIATE)) {
                 if (event.getDamager() instanceof LivingEntity) {
                    LivingEntity living_entity = ( LivingEntity ) event.getDamager();
                    living_entity.damage((double)(event.getDamage() * .25), player);
                 }
              }
           }
           catch (NotOnlineException e) {
              e.printStackTrace();
           }
        }
     }*/
}