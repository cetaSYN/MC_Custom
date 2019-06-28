package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChannelHandler;
import com.mc_custom.chat.channels.ChatChannel;

public class ListCommand extends BaseCommand {

    private ChannelHandler channel_handler;

    public ListCommand( MC_Custom_Chat chat ) {
        channel_handler = chat.getChannelHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"list"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException {
        CommandSender command_sender = command.getSender();
        if (command_sender.hasPermission("chat.list")) {
            String[] channels = new String[]{};
            for (ChatChannel channel : channel_handler.getChannels()) {
                channels = (String[])ArrayUtils.add(channels, ChatColor.BLUE + "- " + channel.getName());
                //TODO Pagination
            }
            return channels;
        }
        else {
            throw new NoPermissionException();
        }
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.list";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}
