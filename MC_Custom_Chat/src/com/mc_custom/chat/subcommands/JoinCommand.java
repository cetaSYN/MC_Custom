package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChannelHandler;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.events.ChatChannelChangeEvent;
import com.mc_custom.chat.exceptions.ChannelNotFoundException;
import com.mc_custom.chat.member.ChatMember;
import com.mc_custom.chat.member.MemberHandler;

public class JoinCommand extends BaseCommand {

    private ChannelHandler channel_handler;
    private MemberHandler member_handler;
    private MC_Custom_Chat chat;

    public JoinCommand( MC_Custom_Chat chat ) {
        this.chat = chat;
        channel_handler = chat.getChannelHandler();
        member_handler = chat.getMemberHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"join"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException, PlayerOnlyException, NotOnlineException {
        CommandSender command_sender = command.getSender();
        if (command_sender.hasPermission("chat.moveself")) {
            if (command.getArgs().length > 3) {
                throw new TooManyArgumentsException();
            }
            else if (command.getArgs().length < 2) {
                throw new TooFewArgumentsException();
            }

            if (command_sender instanceof ConsoleCommandSender) {
                throw new PlayerOnlyException();
            }

            String password = "";
            if (command.getArgs().length == 2) {
                //  /chat move [channel]
                password = "";
            }
            else if (command.getArgs().length == 3) {
                //  /chat move [channel] [password]
                password = command.getArg(2);
            }

            try {
                ChatChannel new_channel = channel_handler.getChannelByName(command.getArg(1));
                ChatMember member = member_handler.getPlayer(command_sender.getName());

                if (command_sender.hasPermission("chat.bypass")) {
                    chat.getServer().getPluginManager().callEvent(new ChatChannelChangeEvent(member, member.getChatChannel(), new_channel, password, true));
                }
                else {
                    if (!new_channel.isLocked()) {
                        if (password.equals(new_channel.getPassword()))
                            chat.getServer().getPluginManager().callEvent(new ChatChannelChangeEvent(member, member.getChatChannel(), new_channel, password, false));
                        else {
                            return new String[]{ChatColor.RED + "Incorrect password."};
                        }
                    }
                    else {
                        return new String[]{ChatColor.RED + "This channel is locked."};
                    }
                }
            }
            catch (ChannelNotFoundException ex) {
                return new String[]{ChatColor.RED + "That channel does not exist!"};
            }
        }
        else {
            throw new NoPermissionException();
        }
        return new String[]{};
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.moveself";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}