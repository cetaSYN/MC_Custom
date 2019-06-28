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

import org.bukkit.Location;

public class TeleportCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"tp", "teleport", "tpo", "tpaccept", "tpa", "tpahere", "tppos", "tptoggle", "tpdeny", "tphere"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.teleport";
	}

	/**
	 * /tp [player]
	 * /tpa [player]
	 * /tpahere [player]
	 * /tphere [player]
	 * /tp [player] [player]
	 * /tp [x] [y] [z]
	 * /tppos [x] [y] [z]
	 * /tpaccept
	 * /tptoggle
	 * /tpdeny
	 */
	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}

		CorePlayer sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		String player_name = "";
		if (command.getArgs().length > 0) {
			player_name = command.getArg(0);
		}

		switch (command.getCommand()) {
			case "tpa":
				if (player_name.isEmpty()) {
					throw new TooFewArgumentsException();
				}
				CorePlayer player1 = getFuzzyPlayer(player_name);
				if (player1.isVanished()) {
					throw new NotOnlineException();
				}
				return teleportAccept(sender, player1, false);
			case "tpaccept":
				return teleportAccept(sender, null, false);
			case "tpdeny":
				player1 = sender.getLastTeleportRequest();
				if (player1 != null) {
					player1.sendMessage(ChatColor.BLUE + sender.getName() + ChatColor.BLUE + " has denied your request.");
				}
				sender.setLastTeleportRequest(null);
				return new String[]{"You have denied the request"};
			case "tpahere":
				if (player_name.isEmpty()) {
					throw new TooFewArgumentsException();
				}
				player1 = getFuzzyPlayer(player_name);
				if (player1.isVanished()) {
					throw new NotOnlineException();
				}
				return teleportAccept(sender, player1, true);
			case "tptoggle":
				if (sender.hasPermission("core.teleport.toggle")) {
					sender.setAllowTeleportRequest(!sender.allowTeleportRequest());
					return sender.allowTeleportRequest() ? new String[]{"You have enabled tpa requests"} : new String[]{"You have disabled tpa requests"};
				}
				throw new NoPermissionException();
			case "tp":
			case "tpo":
			case "teleport":
			case "tppos":
				switch (command.getArgs().length) {
					case 0:
						throw new TooFewArgumentsException();
					case 1: //  /tp [player]
						player1 = getFuzzyPlayer(player_name);
						return teleport(sender, sender, player1);
					case 2: //  /tp [player] [player]
						player1 = getFuzzyPlayer(player_name);
						CorePlayer player2 = getFuzzyPlayer(command.getArg(1));
						return teleport(sender, player1, player2);
					case 3: // /tp[pos] [x] [y] [z]
						return teleportPosition(sender, command.getArg(0), command.getArg(1), command.getArg(2));
					default:
						throw new TooManyArgumentsException();
				}
			case "tphere":
				if (player_name.isEmpty()) {
					throw new TooFewArgumentsException();
				}
				player1 = getFuzzyPlayer(player_name);
				return teleport(sender, player1, sender);
			default:
				throw new InvalidArgumentException();
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /tpa [player]",
				"Used for teleporting to player.",
				"/tpahere [player]",
				"Used for teleporting a player to you."
		};
	}

	private String[] teleport(CorePlayer sender, CorePlayer player1, CorePlayer player2) throws NotOnlineException, NoPermissionException {

		if (!sender.hasPermission("core.teleport.staff")) {
			throw new NoPermissionException();
		}

		if (player1 == player2) {
			return new String[]{"You cannot teleport to yourself"};
		}

		if (player1 == null || player2 == null) {
			return new String[]{"Player not found!"};
		}
		else {
			//teleport player1 to player2
			player1.teleport(player2);
			return new String[]{"Teleporting " + player1.getName() + " to " + player2.getName() + "."};
		}
	}

	private String[] teleportAccept(CorePlayer sender, CorePlayer player1, boolean here) throws NotOnlineException, NoPermissionException {

		if (!sender.hasPermission("core.teleport.accept")) {
			throw new NoPermissionException();
		}

		if (sender == player1) {
			return new String[]{"You cannot teleport to yourself"};
		}
		if (player1 == null) {
			//tpaccept
			// sender is the player who is accepting
			// player1 is the player that sent the request
			boolean to = sender.getLastTeleportRequestDirection();
			player1 = sender.getLastTeleportRequest();
			// we just reset the player1 object, so checking for null again.
			if (player1 == null) {
				return new String[]{"No requests have been sent to you."};
			}
			if (to) {
				//teleport player to acceptor (/tpa)
				player1.teleport(sender);
			}
			else {
				//teleport acceptor to player (/tpahere)
				sender.teleport(player1);
			}
			player1.setLastTeleportRequest(null);
			sender.setLastTeleportRequest(null);
			return new String[]{"Teleporting " + sender.getName() + " to " + player1.getName() + "."};
		}

		if (player1.isVanished()) {
			throw new NotOnlineException();
		}

		if (!player1.allowTeleportRequest()) {
			return new String[]{"This player has requests disabled."};
		}

		if (here) {
			//tpahere
			player1.setLastTeleportRequestDirection(false);
			player1.sendMessage(ChatColor.YELLOW + sender.getName() + ChatColor.BLUE + " has requested that you teleport to them. Use /tpaccept to accept");
		}
		else {
			//tpa
			player1.setLastTeleportRequestDirection(true);
			player1.sendMessage(ChatColor.YELLOW + sender.getName() + ChatColor.BLUE + " has requested to teleport to you. Use /tpaccept to accept");
		}

		player1.setLastTeleportRequest(sender);

		//teleport request
		return new String[]{"Teleport request sent to " + player1.getName()};
	}

	private String[] teleportPosition(CorePlayer sender, String sx, String sy, String sz) throws NotOnlineException, NoPermissionException {

		if (!sender.hasPermission("core.teleport.pos")) {
			throw new NoPermissionException();
		}

		double x = Double.valueOf(sx);
		double y = Double.valueOf(sy);
		double z = Double.valueOf(sz);

		Location location = new Location(sender.getWorld(), x, y, z);
		sender.teleport(location);
		return new String[]{"Teleporting " + sender.getName() + " to " + x + " " + y + " " + z};
	}

	private CorePlayer getFuzzyPlayer(String name) throws NotOnlineException {
		CorePlayer player;
		try {
			player = MC_Custom_Core.getPlayerHandler().getPlayerByNameFuzzy(name);
		}
		catch (NotOnlineException noex) {
			//if not ign, check nick
			try {
				player = MC_Custom_Core.getPlayerHandler().getPlayerByNicknameFuzzy(name);
			}
			catch (NotOnlineException ex) {
				//if not nick
				throw new NotOnlineException();
			}
		}
		return player;
	}
}