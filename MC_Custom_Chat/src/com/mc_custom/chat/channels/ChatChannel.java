package com.mc_custom.chat.channels;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.mc_custom.chat.member.ChatMember;

public class ChatChannel {
	
	private LinkedList<ChatMember> channel_members;
	private String name;
	private String owner_name;
	private boolean locked;
	private boolean muted; //TODO String muter_name, null for not muted, so staff can override channel-owner mutes.
	private String password = "";
	private Logger log = Bukkit.getLogger();
	
	/**
	 * @param name
	 * @param owner_name
	 */
	public ChatChannel(String name, String owner_name){
		channel_members = new LinkedList<ChatMember>();
		this.name = name;
		this.owner_name = owner_name;
		this.locked = false;
		this.muted = false;
	}
	
	/**
	 * Returns the members in the channel.
	 * @return channel_members
	 */
	public LinkedList<ChatMember> getChannelMembers(){
		return channel_members;
	}
	
	/**
	 * Adds a member to the channel.
	 * @param member
	 */
	public void addMember(ChatMember member){
		channel_members.add(member);
		log.info(member.getPlayerName() + " added to channel " + name);
	}
	
	/**
	 * Removes a member from the channel.
	 * @param member
	 */
	public void removeMember(ChatMember member){
		channel_members.remove(member);
		log.info(member.getPlayerName() + " removed from channel " + name);
	}
	
	/**
	 * Returns the name of the channel.
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the name of the ChatChannel's owner.
	 * @return owner_name
	 */
	public String getOwnerName(){
		return owner_name;
	}
	
	/**
	 * Sets the name of the ChatChannel's owner.
	 * @param owner_name
	 */
	public void setOwnerName(String owner_name){
		this.owner_name = owner_name;
	}
	
	/**
	 * Returns true if the channel is locked, or false is the channel is open.
	 * @return locked
	 */
	public boolean isLocked(){
		return locked;
	}
	
	/**
	 * Sets whether or not the channel is locked.
	 * @param locked
	 */
	public void setLocked(boolean locked){
		this.locked = locked;
		log.info(name + " is now " + ((locked)?"locked":"unlocked"));
	}
		
	/**
	 * Returns true if the channel is muted or false if the channel in not muted.
	 * @return muted
	 */
	public boolean isMuted(){
		return muted;
	}
	
	/**
	 * Sets whether or not the channel is muted.
	 * @param muted
	 */
	public void setMuted(boolean muted){
		this.muted = muted;
		log.info(name + " is now " + ((muted)?"muted":"unmuted"));
	}
	
	/**
	 * Returns the password for the ChatChannel.
	 * @return password
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Sets the password for the ChatChannel.
	 * @param password
	 */
	public void setPassword(String password){
		this.password = password;
		if(password!=""){
			log.info(name + " is now using a password.");
		}
		else{
			log.info(name + " is now unpassworded.");
		}
	}
}