package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BookCommand extends BaseCommand {
	@Override
	public String[] getCommandNames() {
		return new String[]{"book"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.book";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			InvalidArgumentException, PlayerOnlyException, ConsoleOnlyException, NotOnlineException {
		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		CorePlayer player = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		ItemStack hand = player.getItemInHand();
		if (hand == null || (!hand.getType().equals(Material.WRITTEN_BOOK) && !hand.getType().equals(Material.BOOK_AND_QUILL))) {
			return new String[]{"You are not holding a book!"};
		}
		hand.setType(hand.getType().equals(Material.WRITTEN_BOOK) ? Material.BOOK_AND_QUILL : Material.WRITTEN_BOOK);
		return new String[]{};
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /book",
				"Toggles a book's signed state"
		};
	}
}
