package com.mc_custom.patch.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.patch.MC_Custom_Password;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import java.util.Iterator;
import java.util.LinkedList;

public class PasswordListener implements BaseListener{

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player join_player = event.getPlayer();
        if(!MC_Custom_Core.getPlayerHandler().getPlayerSilently(join_player).hasPermission("password.bypass")){
            MC_Custom_Password.getAuthorizing().add(join_player.getName());
        }
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        Player player = event.getPlayer();

        //Allow All vertical movement
        if(event.getTo().getBlockX()==event.getFrom().getBlockX() &&
                event.getTo().getBlockZ()==event.getFrom().getBlockZ()){
            event.setCancelled(false);
            return;
        }

        //Only allow authorized players.
        if(MC_Custom_Password.getAuthorizing().contains(player.getName())){
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "This server requires a password.");
            player.sendMessage(ChatColor.RED + "Use /password to unlock interactivity.");
        }
    }

    @EventHandler
    public void cancelChat(final AsyncPlayerChatEvent event) {
        Player chat_player = event.getPlayer();
        if(MC_Custom_Password.getAuthorizing().contains(chat_player.getName())){
            event.setCancelled(true);
            chat_player.sendMessage(ChatColor.RED + "This server requires a password.");
            chat_player.sendMessage(ChatColor.RED + "Use /password to unlock interactivity.");
            return;
        }


        /* Don't send messages to players that have not been authorized yet. */
        Iterator<Player> player_iter = event.getRecipients().iterator();
        LinkedList<Player> removed_players = new LinkedList();
        //Gather list of players to remove.
        while(player_iter.hasNext()){ //TODO: Not terribly efficient. We should sort both lists and compare on position, rather than cycling multiple times.
            Player player = player_iter.next();

            if(MC_Custom_Password.getAuthorizing().contains(player.getName())){
                removed_players.add(player);
            }
        }
        //Remove 'em
        event.getRecipients().removeAll(removed_players);
    }

    @EventHandler
    public void interact(final PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR
                || event.getAction() == Action.LEFT_CLICK_BLOCK
                || event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(MC_Custom_Password.getAuthorizing().contains(event.getPlayer().getName())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void cancelCommands(final PlayerCommandPreprocessEvent event) throws NotOnlineException {
        Player player = event.getPlayer();
        if (MC_Custom_Password.getAuthorizing().contains(player.getName())) {
            String msg = event.getMessage().split(" ")[0];
            switch (msg.toLowerCase()) {
                // Allowed Commands
                case "/password":
                    break;
                default: //Bad commands
                    player.sendMessage(ChatColor.RED + "This server requires a password.");
                    player.sendMessage(ChatColor.RED + "Use /password to unlock interactivity.");
                    event.setCancelled(true);
            }
        }
    }
}
