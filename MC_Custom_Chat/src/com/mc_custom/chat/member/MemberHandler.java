package com.mc_custom.chat.member;

import java.util.HashMap;

public class MemberHandler {

		private HashMap<String, ChatMember> member_list = new HashMap<String, ChatMember>();
		
		/**
		 * PlayerManager constructor.
		 * Adds all currently online players to the list to
		 * prevent non-listed players during /reloads.
		 */
		public MemberHandler(MC_Custom_Core core){
			for(Player player : core.getServer().getOnlinePlayers()){
				playerJoin(player);
			}
		}
		
		/**
		 * Called when a player joins the server.
		 * Adds player to player_list.
		 */
		public void playerJoin(Player join_player)
		{
			ChatMember member = new ChatMember(join_player.getName()); //TODO: Database pull.
			member_list.put(join_player.getName(), member);
		}

		/**
		 * @param player_name The name of the player to retrieve.
		 * @return The ChatMember object for player_name.
		 * @throws NotOnlineException 
		 */
		public ChatMember getPlayer(String player_name) throws NotOnlineException
		{
			ChatMember member = member_list.get(player_name);
				if(member!=null){
					return member;
				}
			throw new NotOnlineException();
		}
		
		/**
		 * @param The player to retrieve.
		 * @return The ChatMember object for player_name.
		 * @throws NotOnlineException 
		 */
		public ChatMember getPlayer(Player given_player) throws NotOnlineException
		{
			ChatMember player = member_list.get(given_player.getName());
				if(player!=null){
					return player;
				}
			throw new NotOnlineException();
		}
		
		/**
		 * Called when a player quits the server.
		 * Removes the player from player_list.
		 */
		public void playerQuit(Player quit_player)
		{
			ChatMember player = member_list.remove(quit_player);
				if(player!=null){
					player = null;
				}
		}
}
