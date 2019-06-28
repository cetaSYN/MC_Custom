package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.Column;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.players.CorePlayer;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class GiveCommand extends BaseCommand {
	PlayerHandler<CorePlayer> player_handler;

	public GiveCommand() {
		player_handler = MC_Custom_Core.getPlayerHandler();
	}

	@Override
	public String[] getCommandNames() {
		return new String[]{"give", "item", "i"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.give";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, NotOnlineException, PlayerOnlyException {

		if (command.getArgs().length > 3) {
			throw new TooManyArgumentsException();
		}
		CommandSender sender = command.getSender();
		int quantity = 1;

		switch (command.getArgs().length) {
			case 1: // /give [item]
				if(command.fromPlayer()) {
					return give(sender, player_handler.getPlayer((Player) sender), command.getArg(0), quantity);
				}
				else{
					throw new PlayerOnlyException();
				}
			case 2: // /give [player] [item] OR /give [item] [quantity]
				CorePlayer player = player_handler.getPlayerSilently(command.getArg(0)); //returns null if not found
				String item = command.getArg(1);
				if (player == null && command.fromPlayer()) {
					player = player_handler.getPlayer((Player) sender);
					item = command.getArg(0);
					try {
						quantity = Integer.parseInt(command.getArg(1));
					}
					catch (NumberFormatException e) {
						throw new InvalidArgumentException(ChatColor.RED + command.getArg(1) + " is not a valid quantity!");
					}
				}
				return give(sender, player, item, quantity);
			case 3: // /give [player] [item] [quantity]
				player = player_handler.getPlayer(command.getArg(0)); //throws NotOnline
				item = command.getArg(1);
				try {
					quantity = Integer.parseInt(command.getArg(2));
				}
				catch (NumberFormatException e) {
					throw new InvalidArgumentException(ChatColor.RED + command.getArg(2) + " is not a valid quantity!");
				}
				return give(sender, player, item, quantity);
			default:
				throw new TooFewArgumentsException();
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /give [player] [item:data] [amount]"
		};
	}

	private String[] give(final CommandSender sender, final CorePlayer player, String item, final int quantity) throws InvalidArgumentException, NotOnlineException {
		if (player == null) {
			// Just in case...
			throw new NotOnlineException();
		}
		short meta = 0;
		if (item.contains(":")) {
			String[] item_metadata = item.split(":");
			item = item_metadata[0];
			try {
				meta = Short.parseShort(item_metadata[1]);
			}
			catch (NumberFormatException e) {
				throw new InvalidArgumentException(ChatColor.RED + item_metadata[1] + " is not a valid metadata value!");
			}
		}

		final String final_item = item;
		final short final_meta = meta;

		MC_Custom_Core.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				ItemStack item_stack = null;
				try {
					int item_id = Integer.parseInt(final_item);
					Material material = Material.getMaterial(item_id);
					if (material != null) {
						item_stack = new ItemStack(material, quantity, final_meta);
					}
				}
				catch (NumberFormatException e) {
					item_stack = getItemAlias(final_item, quantity, final_meta);
				}
				if (item_stack != null) {
					player.give(item_stack);
					sender.sendMessage("Giving " + quantity + " " + item_stack.getType().name() + " to " + player.getName());
				}
				else {
					sender.sendMessage(ChatColor.RED + final_item + " is not a valid item!");
				}
			}
		});
		return new String[]{""};
	}

	// Unless this needs to be used elsewhere, I'm leaving it here.
	public ItemStack getItemAlias(final String alias, final int amount, final short meta) {
		try {
			long duration = System.currentTimeMillis();
			Column<String> name = new Column<>(String.class);
			Column<Short> damage = new Column<>(Short.class);
			new QueryBuilder(MC_Custom_Core.getDBConnection())
					.setQuery("SELECT `damage`,`name` FROM `item_aliases` WHERE `alias` = ?")
					.setString(alias)
					.executeQuery()
					.fetchOne(damage, name);
			PluginLogger.database().info(Utils.debugRunDuration(duration));
			String material_name = name.getValue();
			Short material_damage = damage.getValue();
			if (material_name != null && material_damage != null) {
				Material material = Material.getMaterial(material_name);
				if (meta > 0) {
					return new ItemStack(material, amount, meta);
				}
				return new ItemStack(material, amount, material_damage);
			}
			return null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}