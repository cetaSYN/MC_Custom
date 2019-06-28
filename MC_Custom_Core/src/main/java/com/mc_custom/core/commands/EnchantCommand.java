package com.mc_custom.core.commands;

import com.mc_custom.core.exceptions.ConsoleOnlyException;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotImplementedException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;

public class EnchantCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"enchant"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.enchant";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
			NotImplementedException, InvalidArgumentException, PlayerOnlyException, ConsoleOnlyException, NotOnlineException {
		throw new NotImplementedException();
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Enchants an item",
				"Syntax: /enchant <enchantment> <level>",
		};
	}
}
