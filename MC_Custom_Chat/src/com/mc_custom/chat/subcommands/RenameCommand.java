package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChannelHandler;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.exceptions.ChannelAlreadyExistsException;
import com.mc_custom.chat.exceptions.ChannelNotFoundException;
import com.mc_custom.chat.member.ChatMember;

public class RenameCommand extends BaseCommand {

    private ChannelHandler channel_handler;

    public RenameCommand( MC_Custom_Chat chat ) {
        channel_handler = chat.getChannelHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"rename"};
    }
    @SuppressWarnings("deprecation")
    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException {
        CommandSender command_sender = command.getSender();
        if (command_sender.hasPermission("chat.rename")) {
            if (command.getArgs().length > 3) {
                throw new TooManyArgumentsException();
            }

            // /chat rename [old name] [new name]
            try {
                ChatChannel old_channel = channel_handler.getChannelByName(command.getArg(1));

                if (old_channel.getOwnerName() == "Console") {
                    return new String[]{ChatColor.RED + "Cannot rename permanent channels"};
                }
                else {
                    ChatChannel new_channel = new ChatChannel(command.getArg(2), old_channel.getOwnerName());
                    for (ChatMember member : old_channel.getChannelMembers()) {
                        member.setChatChannel(new_channel);
                        Bukkit.getPlayer(member.getPlayerName()).sendMessage(ChatColor.GOLD + "Staff has renamed your channel to " + new_channel.getName());
                    }
                    channel_handler.removeChannel(old_channel);
                    channel_handler.addChannel(new_channel);
                }
            }
            catch (ChannelNotFoundException e) {
                return new String[]{ChatColor.RED + "There is no channel named " + command.getArg(2)};
            }
            catch (ChannelAlreadyExistsException e) {
                return new String[]{ChatColor.RED + "This channel already exists"};
            }
        }
        else {
            throw new NoPermissionException();
        }
        return new String[]{};
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.rename";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}
