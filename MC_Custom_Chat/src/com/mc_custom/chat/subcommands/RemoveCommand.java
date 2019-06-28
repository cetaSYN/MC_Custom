package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChannelHandler;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.exceptions.ChannelNotFoundException;

public class RemoveCommand extends BaseCommand {

    private ChannelHandler channel_handler;

    public RemoveCommand( MC_Custom_Chat chat ) {
        channel_handler = chat.getChannelHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"remove"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException {
        CommandSender command_sender = command.getSender();
        if (command_sender.hasPermission("chat.remove")) {
            // /chat remove [channel]
            if (command.getArgs().length > 2) {
                throw new TooManyArgumentsException();
            }
            try {
                ChatChannel channel = channel_handler.getChannelByName(command.getArg(1));
                if (channel.getOwnerName() == "Console") {
                    return new String[]{ChatColor.RED + "Cannot delete permanent channels"};
                }
                else {
                    channel_handler.dieChannel(channel, "Staff has removed this channel! You are moving to GlobalChat");
                }
            }
            catch (ChannelNotFoundException e) {
                return new String[]{ChatColor.RED + "This channel cannot be found"};
            }
        }
        else {
            throw new NoPermissionException();
        }
        return null;
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.remove";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}
