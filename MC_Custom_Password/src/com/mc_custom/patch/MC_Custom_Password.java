package com.mc_custom.patch;

import com.mc_custom.core.MC_CustomPlugin;
import com.mc_custom.core.handlers.CommandHandler;
import java.util.LinkedList;

public class MC_Custom_Password extends MC_CustomPlugin {

    private static MC_Custom_Password plugin;
    //Players who have not yet entered the password.
    private static LinkedList<String> authorizing = new LinkedList<>();

    @Override
    public void onEnable( ) {
        checkCore();
        plugin = this;
        // Register Listeners
        registerEvents(this, "com.mc_custom.password.listeners");

        // Register Commands
        CommandHandler.getInstance().addCommands(this, "com.mc_custom.password.commands");
    }

    public static MC_Custom_Password getInstance() {
        return plugin;
    }

    public static LinkedList<String> getAuthorizing() { return authorizing; }

    public static void runTaskAsynchronously(Runnable runnable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }
}