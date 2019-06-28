package com.mc_custom.chat.channels;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.exceptions.ChannelAlreadyExistsException;
import com.mc_custom.chat.exceptions.ChannelNotFoundException;
import com.mc_custom.chat.member.MemberHandler;

public class ChannelHandler {

   private HashMap<String, ChatChannel> channels = new HashMap<String, ChatChannel>();
   private MemberHandler member_handler;
   private Logger log = Bukkit.getLogger();

   public ChannelHandler( MC_Custom_Chat chat ) {
      member_handler = chat.getMemberHandler();
   }

   /**
    * Adds a new ChatChannel. If a channel already exists with the specified name, a ChannelAlreadyExistsException is thrown.
    * 
    * @param name
    * @param owner_name
    * @throws ChannelAlreadyExistsException
    */
   public void addChannel( String name, String owner_name ) throws ChannelAlreadyExistsException {
      if (!channels.containsKey(name)) {
         channels.put(name, new ChatChannel(name, owner_name));
         log.info(owner_name + " created new channel " + name);
      }
      else {
         log.info(owner_name + " attempted to create " + name + " but it already exists!");
         throw new ChannelAlreadyExistsException();
      }
   }

   /**
    * Adds a new ChatChannel. If a channel already exists with the specified name, a ChannelAlreadyExistsException is thrown.
    * 
    * @param chat_channel
    * @throws ChannelAlreadyExistsException
    */
   public void addChannel( ChatChannel chat_channel ) throws ChannelAlreadyExistsException {
      if (!channels.containsKey(chat_channel.getName()) && !channels.containsValue(chat_channel)) {
         channels.put(chat_channel.getName(), chat_channel);
         log.info(chat_channel.getOwnerName() + " created new channel " + chat_channel.getName());
      }
      else {
         log.info(chat_channel.getOwnerName() + " attempted to create " + chat_channel.getName() + " but it already exists!");
         throw new ChannelAlreadyExistsException();
      }
   }

   /**
    * Returns all the ChatChannels in the HashMap.
    */
   public Collection<ChatChannel> getChannels( ) {
      return channels.values();
   }

   /**
    * Returns a ChatChannel by the given name, else returns null.
    * 
    * @param channel_name
    * @throws ChannelNotFoundException
    */
   public ChatChannel getChannelByName( String channel_name ) throws ChannelNotFoundException {
      ChatChannel channel = channels.get(channel_name);
      if (channel != null) {
         return channel;
      }
      else {
         throw new ChannelNotFoundException();
      }
   }

   /**
    * Returns a ChatChannel by the given owner, else returns null.
    * 
    * @param owner_name
    * @throws ChannelNotFoundException
    */
   public ChatChannel getChannelByOwner( String owner_name ) throws ChannelNotFoundException {
      for (ChatChannel channel : channels.values()) {
         if (channel != null && channel.getOwnerName() == owner_name) {
            return channel;
         }
      }
      throw new ChannelNotFoundException();
   }

   /**
    * Removes all active players from a channel and notifies them why they were removed, then removes the channel.
    * 
    * @param channel
    * @param reason
    */
   public void dieChannel(ChatChannel channel, String reason ) {
      log.info("Dying channel " + channel.getName() + " because " + reason);
      for (Player player : Bukkit.getOnlinePlayers()) {
         try {
            if (player != null) {
               if (member_handler.getPlayer(player).getChatChannel() == channel) {
                  member_handler.getPlayer(player).setChatChannel(MC_Custom_Chat.global_chat);
                  player.sendMessage(ChatColor.RED + reason);
               }
            }
            //channel.getOwnerName() !=player.getName() && 

         }
         catch (NotOnlineException e) {
            e.printStackTrace();
         }
      }
      try {
         removeChannel(channel);
      }
      catch (ChannelNotFoundException e) {
         log.warning("Attemped to die channel " + channel.getName() + " due to " + reason + " but it could not be found!");
         e.printStackTrace();
      }
   } 

   /**
    * Removes a ChatChannel.
    * 
    * @param channel
    * @throws ChannelNotFoundException
    */
   public void removeChannel( ChatChannel channel ) throws ChannelNotFoundException {
      if (channels.remove(channel.getName()) == null) {
         log.warning("Attemped to remove channel " + channel.getName() + " but it could not be found!");
         throw new ChannelNotFoundException();
      }
      log.info(channel.getName() + " removed.");
   }
}