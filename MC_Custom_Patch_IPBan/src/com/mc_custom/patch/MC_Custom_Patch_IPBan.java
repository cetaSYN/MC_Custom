package com.mc_custom.patch;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.handlers.CommandHandler;
import com.mc_custom.patch.commands.BanIPCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.LinkedList;

public class MC_Custom_Patch_IPBan extends JavaPlugin {

    private MC_Custom_Core core;
    private LinkedList<String> banned_ips = new LinkedList<>();

    @Override
    public void onEnable( ) {
        /* Get Handle to Core */
        try {
            core = ( MC_Custom_Core ) getServer().getPluginManager().getPlugin("MC_Custom_Core");
        }
        catch (Exception ex) {
            Bukkit.getLogger().severe(ex.getMessage());
            ex.printStackTrace();
            Bukkit.getLogger().severe("Error linking to MC_Custom_Core! Shutting Down!");
            this.getServer().shutdown();
        }

        /* Add Commands */
        CommandHandler.getInstance().addCommand(new BanIPCommand(this));

        /* Add Listeners */
        this.getServer().getPluginManager().registerEvents(new IPBanListener(), this);
    }

    public MC_Custom_Core getCore( ) {
        return core;
    }

    public LinkedList<String> getBannedIPs(){
        return banned_ips;
    }

    private class IPBanListener implements Listener{

        @EventHandler
        public void onLogin(PlayerLoginEvent event){
            Iterator<String> iter = banned_ips.iterator();
            while(iter.hasNext()){
                if(event.getAddress().toString().equals(iter.next().toString())){
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "This address has been blocked.\nPlease visit the forums for support.\n\nhttps://url_removed");
                }
            }
        }
    }
}