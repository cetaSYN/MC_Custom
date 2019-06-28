package com.mc_custom.physics.listeners;

import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InfiniteSnowballListener implements BaseListener {

    @EventHandler()
    public void infinite(final PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }
        ItemStack item = event.getItem();
        Map<Enchantment, Integer> enchants = item.getEnchantments();
        if (item.getType().equals(Material.AIR)
                || !enchants.containsKey(Enchantment.ARROW_INFINITE)
                || event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (item.getType()==Material.SNOW_BALL) { //!(item.getType()==Material.BOW)) {
            if (item.getAmount() <= 2) {
                item.setAmount(3);
            }
        }
    }
}
