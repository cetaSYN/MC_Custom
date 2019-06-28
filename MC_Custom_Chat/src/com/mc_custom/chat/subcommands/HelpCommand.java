package com.mc_custom.chat.subcommands;

public class HelpCommand extends BaseCommand {

    /*
     * TODO: Move help messages into their appropriate commands
     */

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"help"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException {
        /*
	   CommandSender sender = command.getSender();
	   sender.sendMessage(ChatColor.BLUE + "[MC_Custom_Chat]");
      sender.sendMessage(ChatColor.BLUE + "/chat accept | Invites pending invites.");
      sender.sendMessage(ChatColor.BLUE + "/chat create [name] | Creates a channel with the specified name.");
      sender.sendMessage(ChatColor.BLUE + "/chat invite [player] | Invites player to your channel.");
      sender.sendMessage(ChatColor.BLUE + "/chat kick [player] | Kicks player from channel.");
      sender.sendMessage(ChatColor.BLUE + "/chat list | Lists chat channels.");
      sender.sendMessage(ChatColor.BLUE + "/chat lock | Locks channel. Players must be invited.");
      sender.sendMessage(ChatColor.BLUE + "/chat move [name] [pass] | Moves you to the channel of the specified name.");
      sender.sendMessage(ChatColor.BLUE + "/chat open | Unlocks channel.");
      sender.sendMessage(ChatColor.BLUE + "/chat pass | Sets password for your channel.");
         */
        return null;
    }

    @Override
    public String getRequiredPermissions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}
