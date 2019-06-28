package com.mc_custom.patch.commands;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.patch.MC_Custom_Password;
import org.bukkit.command.CommandSender;


public class PasswordCommand extends BaseCommand {

    @Override
    public String[] getCommandNames() {
        return new String[]{"password"};
    }

    @Override
    public String getRequiredPermissions() {
        return null;
    }

    @Override
    public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException, NoPermissionException, InvalidArgumentException, TooManyArgumentsException {
        CommandSender command_sender = command.getSender();
        if (command.getArgs().length < 1) {
            throw new TooFewArgumentsException();
        }
        if (command.getArgs().length > 1) {
            throw new TooManyArgumentsException();
        }

        if(!MC_Custom_Password.getAuthorizing().contains(command_sender.getName())){
            return new String[]{ChatColor.AQUA + "You are already authorized!"};
        }
        else{
            if (command.getArg(0).equals("No18+OutsideOfThisServerEver")){
                MC_Custom_Password.getAuthorizing().remove(command_sender.getName());
                return new String[]{ChatColor.AQUA + "Authorized."};
            }
            else{
                return new String[]{ChatColor.RED + "Not authorized."};
            }
        }
    }

    @Override
    public String[] getHelp() {
        return new String[]{
                "Authorizes you to play on a server.",
                "Syntax: /password <password>",
                "The password will never have any spaces."
        };
    }
}