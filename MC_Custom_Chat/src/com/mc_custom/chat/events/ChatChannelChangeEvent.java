package com.mc_custom.chat.events;

import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.member.ChatMember;

public class ChatChannelChangeEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	private ChatChannel old_channel;
	private ChatChannel new_channel;
	private ChatMember chat_member;
	private String password_given;
	private boolean staff_forced;
	
	public ChatChannelChangeEvent(ChatMember player, ChatChannel old_channel, ChatChannel new_channel, String password_given, boolean staff_forced){
		this.chat_member = player;
		this.old_channel = old_channel;
		this.new_channel = new_channel;
		this.password_given = password_given;
		this.staff_forced = staff_forced;
	}
	
	/**
	 * Returns the player changing channels.
	 * @return player
	 */
	public ChatMember getChatMember(){
		return chat_member;
	}
	
	/**
	 * Returns the old channel the player was in.
	 * @return old_channel
	 */
	public ChatChannel getOldChannel(){
		return old_channel;
	}
	
	/**
	 * Returns the channel the player is moving to.
	 * @return new_channel
	 */
	public ChatChannel getNewChannel(){
		return new_channel;
	}
	
	/**
	 * Returns the password given by the player trying to change channels.
	 * @return password_given
	 */
	public String getGivenPassword(){
		return password_given;
	}
	
	/**
	 * Returns true if the move is forced by a staff member.
	 * @return staff_forced
	 */
	public boolean isStaffForced(){
		return staff_forced;
	}
	
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
