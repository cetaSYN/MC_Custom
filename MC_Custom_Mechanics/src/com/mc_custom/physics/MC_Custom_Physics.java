package com.mc_custom.physics;

import com.mc_custom.core.MC_CustomPlugin;

public class MC_Custom_Physics extends MC_CustomPlugin {

    private static MC_Custom_Physics plugin;

    @Override
    public void onEnable() {
        checkCore();
        plugin = this;

        // Register Listeners
        registerEvents(this, "com.mc_custom.physics.listeners");

    }

    public static MC_Custom_Physics getInstance() {
        return plugin;
    }

    public static void runTaskAsynchronously(Runnable runnable) {
        getScheduler().runTaskAsynchronously(plugin, runnable);
    }
}