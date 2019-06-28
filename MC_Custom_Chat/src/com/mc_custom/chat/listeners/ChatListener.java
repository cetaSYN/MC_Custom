package com.mc_custom.chat.listeners;

import java.util.Map;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.member.ChatMember;

public class ChatListener implements Listener {

    private MC_Custom_Chat chat;

    public ChatListener( MC_Custom_Chat chat ) {
        this.chat = chat;
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true) //Allows other plugins to ignore cancelled events
    public void onChat( AsyncPlayerChatEvent event ) {
        try {
            ChatChannel player_channel = chat.getMemberHandler().getPlayer(event.getPlayer()).getChatChannel();

            //event.setFormat("[" + player_channel.getName() + "]" + event.getFormat());

            event.getRecipients().clear();
            if (!player_channel.isMuted()) {
                for (ChatMember member : player_channel.getChannelMembers()) {
                    System.out.println(member.getPlayerName());
                    event.getRecipients().add(Bukkit.getPlayer(member.getPlayerName()));
                }
            }
            else {
                event.getPlayer().sendMessage(ChatColor.GOLD + "You are muted.");
                event.setCancelled(true);
                return;
            }
            /*
             * To allow for text highlighting, the event must be cancelled, and the message
             * sent to each individual recipient.
             */
            for (ChatMember member : chat.getMonitoringStaff()) {
                event.getRecipients().add(Bukkit.getPlayer(member.getPlayerName()));
            }
            String format_message = ChatColor.translateAlternateColorCodes('&', event.getMessage());
            if(!event.getPlayer().hasPermission("chat.color")){
                format_message = ChatColor.stripColor(format_message);
            }
            String format_name = event.getPlayer().getName();
            if(event.getPlayer().getDisplayName() != null){
                if(event.getPlayer().getDisplayName().equals(event.getPlayer().getName())){
                    format_name = "~" + event.getPlayer().getDisplayName();
                }
            }
            for(Player player : event.getRecipients()){
                if(chat.getHighlightList().containsKey(player.getName())){
                    Map<String, ChatColor> highlight_keys = chat.getHighlightList().get(player.getName());
                    for(String key : highlight_keys.keySet()){
                        format_message = format_message.replace(key, highlight_keys.get(key) + key + ChatColor.RESET);
                    }
                }
                player.sendMessage("[" + player_channel.getName() + "] " + format_name + ": " + format_message);
            }
            event.setCancelled(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}