package com.mc_custom.chat.listeners;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.events.ChatChannelChangeEvent;
import com.mc_custom.chat.member.ChatMember;

public class ChannelChangeListener implements Listener {

   private MC_Custom_Chat chat;

   public ChannelChangeListener( MC_Custom_Chat chat ) {
      this.chat = chat;
   }

   @EventHandler
   public void onChatEvent( ChatChannelChangeEvent event ) throws NoPermissionException {
      Player player = Bukkit.getPlayer(event.getChatMember().getPlayerName());
      ChatChannel new_channel = event.getNewChannel();
      ChatChannel old_channel = event.getOldChannel();

      if (event.isStaffForced()) {
         changeChannel(player, event.getChatMember(), old_channel, new_channel);
         player.sendMessage(ChatColor.BLUE + "Bypassing lock/password");
         return;
      }
      //Checks now handled in Sub-command Classes.
      if (player.hasPermission("chat.moveself")) {
         changeChannel(player, event.getChatMember(), old_channel, new_channel);
      }
      else {
         throw new NoPermissionException();
      }
   }

   private void changeChannel( Player player, ChatMember chat_member, ChatChannel old_channel, ChatChannel new_channel ) {
      //Let's just submit this to scheduler, since it will probably be running async.
      Bukkit.getScheduler().runTask(chat, new ChannelChangeTask(player, chat_member, old_channel, new_channel));
   }

   private class ChannelChangeTask extends BukkitRunnable {

      private Player player;
      private ChatMember chat_member;
      private ChatChannel old_channel;
      private ChatChannel new_channel;

      public ChannelChangeTask( Player player, ChatMember chat_member, ChatChannel old_channel, ChatChannel new_channel ) {
         this.player = player;
         this.chat_member = chat_member;
         this.old_channel = old_channel;
         this.new_channel = new_channel;
      }

      @Override
      public void run( ) {
         old_channel.removeMember(chat_member);
         chat_member.setChatChannel(new_channel);
         player.sendMessage(ChatColor.GOLD + "You have changed to channel " + new_channel.getName());

         if (old_channel.getOwnerName() == player.getName()) {
            chat.getChannelHandler().dieChannel(old_channel, "The owner of the channel has left the channel!");
         }
      }
   }
}
