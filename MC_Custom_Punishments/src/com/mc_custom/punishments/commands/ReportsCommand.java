package com.mc_custom.punishments.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotImplementedException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;


public class ReportsCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"reports", "griefs"};
	}

	@Override
	public String getRequiredPermissions() {
		return "punish.reports.list";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException,
			NoPermissionException, InvalidArgumentException, NotImplementedException {
		int args_length = command.getArgs().length;
		if (args_length > 2) {
			throw new TooManyArgumentsException();
		}
		// /reports | views reports list
		//reports [id] clear | clears report from list
	  /*switch (args_length) {
		 case 0:
            //Convert list to string array
            return reports_array;
         case 2:
            if (id is in list) {
               return new String[] {"The report " + id + "has been cleared."};
            }
            else {
               return new String[] {"The report " + id + "cannot be found"};
            }
      }*/
		throw new NotImplementedException();
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Syntax: /staffhelp [comment]",
				"Sends a help report to staff with your name, location and comment",
				"Alternative: /grief, /modhelp"
		};
	}
}