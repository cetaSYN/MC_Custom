package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;

import java.util.regex.Pattern;

public class NickCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"nick", "nickname", "setnick", "setnickname", "changenick", "changenickname"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.nick";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (command.getArgs().length > 2) {
			throw new TooManyArgumentsException("Are you using a space in your nickname? Don't do that!");
		}

		if (command.getArgs().length == 0) {
			throw new TooFewArgumentsException();
		}

		String command_name = command.getArg(0);
		CorePlayer core_sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());

		switch (command.getArgs().length) {
			//Nick Self
			case 1:
				if (!command.fromPlayer()) {
					throw new PlayerOnlyException();
				}
				//nick off
				if (command_name.equalsIgnoreCase("off")) {
					return removeNick(core_sender);
				}
				//nick new_nick
				checkNick(core_sender, command_name);
				return nickSelf(core_sender, command_name);
			//Nick Other
			case 2:
				String command_other = command.getArg(1);
				//nick off player_name
				if (command_name.equalsIgnoreCase("off")) {
					return removeNickOther(core_sender, command_other);
				}
				//nick player_name off
				if (command_other.equalsIgnoreCase("off")) {
					return removeNickOther(core_sender, command_name);
				}
				//nick player_name new_nick
				checkNick(core_sender, command_other);
				return nickOther(core_sender, command_name, command_other);
		}

		throw new InvalidArgumentException();
	}

	@Override
	public String[] getHelp() {
		return new String[]{"Syntax: /nick <name>",
				"Changes your nickname."};
	}

	private void checkNick(CorePlayer core_player, String nickname) throws InvalidArgumentException {
		if (nickname.length() > 20) {
			throw new InvalidArgumentException("That nickname is too long!");
		}

		String regex = "^[a-zA-Z0-9_]+$";
		if (core_player.hasPermission("core.nick.color")) {
			regex = "^[a-zA-Z0-9_&]+$";
		}
		Pattern pattern = Pattern.compile(regex);
		if (!pattern.matcher(nickname).matches()) {
			throw new InvalidArgumentException("Nicknames must be letters, numbers, or an underscore!");
		}

	}

	private String[] nickSelf(CorePlayer core_player, String nickname) throws NotOnlineException {
		core_player.setNickname(nickname);
		return new String[]{"Your nickname is now " + ChatColor.convertColor(nickname)};
	}

	private String[] nickOther(CorePlayer core_sender, String player_name, String nickname) throws NoPermissionException, NotOnlineException {
		if (core_sender.hasPermission("core.nick.other")) {
			CorePlayer core_player = MC_Custom_Core.getPlayerHandler().getPlayer(player_name);
			core_player.staffSetNickname(nickname);
			if (core_player.isOnline()) {
				core_player.sendMessage(ChatColor.AQUA + "Your nickname is now " + ChatColor.convertColor(nickname));
			}
		}
		else {
			throw new NoPermissionException();
		}
		return new String[]{player_name + "'s nickname is now " + ChatColor.convertColor(nickname)};
	}

	private String[] removeNick(CorePlayer core_player) throws NotOnlineException {
		core_player.removeNickname();
		return new String[]{"Your nickname has been removed."};
	}

	private String[] removeNickOther(CorePlayer core_player, String player_name) throws NoPermissionException, NotOnlineException {
		CorePlayer other_player = MC_Custom_Core.getPlayerHandler().getPlayer(player_name);
		if (core_player.hasPermission("core.nick.other")) {
			removeNick(other_player);
			other_player.sendMessage("Your nickname has been removed.");
		}
		else {
			throw new NoPermissionException();
		}
		return new String[]{other_player.getName() + "'s nickname has been removed."};
	}
}