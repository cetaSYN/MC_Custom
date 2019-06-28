package com.mc_custom.chat.listeners;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChannelHandler;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.member.ChatMember;
import com.mc_custom.chat.member.MemberHandler;

public class JoinQuitListener implements Listener {

   private MemberHandler member_handler;
   private ChannelHandler channel_handler;
   private MC_Custom_Chat chat;

   public JoinQuitListener( MC_Custom_Chat chat ) {
      this.chat = chat;
      this.member_handler = chat.getMemberHandler();
      this.channel_handler = chat.getChannelHandler();
   }

   @EventHandler
   public void onJoin( final PlayerJoinEvent event ) {
      Player player = event.getPlayer();
      try {
         member_handler.playerJoin(player);
         ChatMember member = member_handler.getPlayer(player);
         member.setChatChannel(MC_Custom_Chat.global_chat);

         if (player.hasPermission("chat.monitor")) {
            chat.addToMonitor(member);
         }
      }
      catch (NotOnlineException e) {
         e.printStackTrace();
      }
   }

   @EventHandler
   public void onQuit( final PlayerQuitEvent event ) {
      Player player = event.getPlayer();

      ChatChannel player_channel = null;
      ChatMember member;
      try {
         member = member_handler.getPlayer(player);
         player_channel = member.getChatChannel();

         //If player was owner of channel, channel must die.
         if (player_channel != null && player_channel.getOwnerName() == player.getName()) {
            channel_handler.dieChannel(player_channel, "You have been removed from this chat because the original channel owner has left the game.");
         }

         player_channel.removeMember(member);
      }
      catch (NotOnlineException e) {
         e.printStackTrace();
      }
   }
}
