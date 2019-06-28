package com.mc_custom.classes.commands;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.classes.MCCustomClass;
import com.mc_custom.classes.classes.MCCustomType;
import com.mc_custom.classes.events.ClassChangeEvent;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.core.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

/**
 * Class that handles command delegation.
 */
public class ClassCommands extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"class"};
	}

	@Override
	public String getRequiredPermissions() {
		return null;
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException,
			NoPermissionException, InvalidArgumentException, NotOnlineException {
		if (command.getArgs().length > 2) {
			throw new TooManyArgumentsException();
		}

		CommandSender sender = command.getSender();

		switch (command.getArgs().length) {
			case 0: //  /class
				return getClass(sender, sender.getName());
			case 1: // /class list OR /class [player] OR /class [class] OR /class time
				if (command.getArg(0).equals("list")) {
					return listClasses();
				}
				if (command.getArg(0).equals("time")) {
					return timeRemaining(MC_Custom_Classes.getPlayerHandler().getPlayer(sender.getName()));
				}
				else if (MCCustomType.isMCCustomType(command.getArg(0))) {
					return setClass(sender, sender.getName(), command.getArg(0));
				}
				return getClass(sender, command.getArg(0));
			case 2: //  /class [player] [class]
				return setClass(sender, command.getArg(0), command.getArg(1));
		}
		throw new InvalidArgumentException();
	}

	@Override
	public String[] getHelp() {
		return new String[]{"Syntax: /class <class> \tChanges your class.",
				"Syntax: /class list \tLists the available classes.",
				"Syntax: /class time \tCheck time remaining."};
	}

	private String[] listClasses() {
		return MCCustomType.listNames();
	}

	private String[] timeRemaining(MCCustomPlayer player) {
		long change_seconds = player.getChangeSecondsRemaining();
		if (change_seconds <= 0) {
			return new String[]{"You can currently change your class"};
		}
		return new String[]{
				Utils.millisToDHMS(change_seconds)
				+ " remaining before you can change classes."};
	}

	/**
	 * Changes player's class.
	 *
	 * @throws NotOnlineException
	 */
	private String[] setClass(CommandSender sender, String target, String class_type)
			throws NoPermissionException, InvalidArgumentException, NotOnlineException {

		MCCustomPlayer mc_custom_player = MC_Custom_Classes.getPlayerHandler().getPlayer(target);
		Player target_player = mc_custom_player.getPlayer();

		// TARGET NULL
		if (target_player == null) {
			return new String[]{ChatColor.RED + "Player not found!"};
		}

		MCCustomClass old_class = mc_custom_player.getMCCustomClass();
		MCCustomClass new_class = MCCustomType.getMCCustomClass(class_type);
		if (new_class == null) {
			return new String[]{ChatColor.RED + class_type + " is not a class."};
		}

		// TARGET SELF
		if (sender instanceof Player && sender == target_player) {
			if (PermissionUtils.hasWildcardPermission(sender, "class.changeself")) {
				MC_Custom_Classes.callEvent(new ClassChangeEvent(mc_custom_player, old_class, new_class));
				return new String[]{"You are now a " + new_class.getClassColor() + new_class.getClassName()};
			}
			else {
				if (PermissionUtils.hasWildcardPermission(sender, "class.timechange")) {
					long change_seconds = mc_custom_player.getChangeSecondsRemaining();
					if (change_seconds <= 0 || old_class.getClassName().equals("None")) {
						if (new_class != old_class) {
							if (new_class.getClassName().equals("Custom1") || new_class.getClassName().equals("Custom10")) {
								if (PermissionUtils.hasWildcardPermission(sender, "class.custom1")) {
									MC_Custom_Classes.callEvent(new ClassChangeEvent(mc_custom_player, old_class, new_class));
									return new String[]{"You are now a "
											+ new_class.getClassColor()
											+ new_class.getClassName()};
								}
								else {
									return new String[]{"This class is not available to everyone yet."};
								}
							}
							else {
								MC_Custom_Classes.callEvent(new ClassChangeEvent(mc_custom_player, old_class, new_class));
								return new String[]{"You are now a "
										+ new_class.getClassColor()
										+ new_class.getClassName()};
							}
						}
						else {//new_class==old_class
							return new String[]{"You're already a "
									+ old_class.getClassColor()
									+ old_class.getClassName()};
						}
					}
					else {
						return new String[]{
								Utils.millisToDHMS(change_seconds)
								+ " remaining before you can change plugin."};
					}
				}
			}
		}

		// TARGET NOT SELF
		else if (sender != target_player) {
			if (PermissionUtils.hasWildcardPermission(sender, "class.changeother")) {
				MC_Custom_Classes.callEvent(new ClassChangeEvent(mc_custom_player, old_class, new_class));
				if (mc_custom_player.isOnline()) {
					mc_custom_player.sendMessage(ChatColor.AQUA + "You are now a " + new_class.getClassColor() + new_class.getClassName());
				}
				return new String[]{mc_custom_player.getDisplayName() + " is now a " + new_class.getClassColor() + new_class.getClassName()};
			}
		}

		throw new NoPermissionException();
	}

	/**
	 * Prints player's class.
	 *
	 * @throws NotOnlineException
	 */
	private String[] getClass(CommandSender sender, String target) throws
			NoPermissionException, NotOnlineException {

		MCCustomPlayer target_player = MC_Custom_Classes.getPlayerHandler().getPlayer(target);

		// TARGET NULL
		if (target_player == null) {
			return new String[]{"Player not found!"};
		}

		MCCustomClass player = target_player.getMCCustomClass();

		// TARGET SELF
		if ((sender instanceof Player && sender == target_player.getPlayer() && PermissionUtils.hasWildcardPermission(sender, "class.getself"))) {
			return new String[]{"You are a " + player.getClassColor() + player.getClassName()};
		}

		// TARGET NOT SELF
		if (sender != target_player.getPlayer() && PermissionUtils.hasWildcardPermission(sender, "class.getother")) {
			return new String[]{target_player.getPlayerName() + " is a " + player.getClassColor() + player.getClassName()};
		}

		throw new NoPermissionException();
	}
}