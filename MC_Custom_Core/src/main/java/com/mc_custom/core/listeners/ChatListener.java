package com.mc_custom.core.listeners;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements BaseListener {

	private final Pattern mc_custom_regex = Pattern.compile(
			"(?!.*(mc_custom\\.com).*)\\b(https?:\\/\\/)?([a-z\\-]{2,}\\.?)+\\.[a-z]{2,}[-a-zA-Z0-9@:%_\\+.~#?&\\/=]*",
			Pattern.CASE_INSENSITIVE);
	private final Pattern common_regex = Pattern.compile(
			"(?!.*(mc_custom\\.com|youtube|imgur\\.com|equestriadaily\\.com|playervillelive\\.com|" +
					"poniverse\\.net|mlpforums\\.com|player\\.fm|equestria\\.tv|poniarcade\\.com|playerroleplay\\.com" +
					").*)\\b(https?:\\/\\/)?([a-z\\-]{2,}\\.?)+\\.[a-z]{2,}[-a-zA-Z0-9@:%_\\+.~#?&\\/=]*",
			Pattern.CASE_INSENSITIVE);
	private PlayerHandler<CorePlayer> player_handler;

	public ChatListener(){
		this.player_handler = MC_Custom_Core.getPlayerHandler();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void linkFiltering(AsyncPlayerChatEvent event){
		CorePlayer core_player = player_handler.getPlayerSilently(event.getPlayer());
		if(core_player.hasPermission("chat.all")) {
			return;
		}
		if(core_player.hasPermission("chat.common")){
			event.setMessage(common_regex.matcher(event.getMessage()).replaceAll("**"));
			return;
		}
		// No special perms? You can only link to MC_Custom.
		event.setMessage(mc_custom_regex.matcher(event.getMessage()).replaceAll("**"));
	}
}
