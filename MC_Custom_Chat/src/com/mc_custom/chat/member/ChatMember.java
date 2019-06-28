package com.mc_custom.chat.member;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.filter.FilterType;

public class ChatMember {

    private String player_name;
    private ChatChannel chat_channel;
    private ChatChannel invite_channel;
    private FilterType filter;
    private boolean is_staff;

    public ChatMember(String player_name){
        this.player_name = player_name;
        chat_channel = MC_Custom_Chat.global_chat;
        invite_channel = MC_Custom_Chat.global_chat;
    }

    /**
     * Returns the player name associated with the ChatMember.
     * @return player
     */
    public String getPlayerName(){
        return player_name;
    } 

    /**
     * Returns the chat channel the player is currently invited to.
     * @return chat_channel
     */
    public ChatChannel getInvite(){
        return invite_channel;
    }  

    /**
     * Sets the chat channel the player is currently invited to.
     * @return chat_channel
     */
    public void setInvite(ChatChannel chat_channel){
        invite_channel = chat_channel;
    }

    /**
     * Returns the chat channel the player is currently in.
     * @return chat_channel
     */
    public ChatChannel getChatChannel(){
        if(chat_channel==null){
            chat_channel = MC_Custom_Chat.global_chat;
        }
        return chat_channel;
    }

    /**
     * Sets the chat channel that the player is in.
     * @param chat_channel
     */
    public void setChatChannel(ChatChannel chat_channel){
        Bukkit.getLogger().info(player_name + " is moving to " + chat_channel.getName());
        this.chat_channel.removeMember(this);
        this.chat_channel = chat_channel;
        this.chat_channel.addMember(this);
    }

    public FilterType getFilterType(){
        return filter;
    }

    public void setFilterType(FilterType filter){
        this.filter = filter;
    }

    public boolean isStaff(){
        return is_staff;
    }

    public void setStaff(boolean is_staff){
        this.is_staff = is_staff;
    }
}