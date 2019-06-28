package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.events.ChatChannelChangeEvent;
import com.mc_custom.chat.member.ChatMember;
import com.mc_custom.chat.member.MemberHandler;

public class AcceptCommand extends BaseCommand {

    private MemberHandler member_handler;
    private MC_Custom_Chat chat;

    public AcceptCommand( MC_Custom_Chat chat ) {
        this.chat = chat;
        member_handler = chat.getMemberHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"accept"};
    }
    public String getRequiredPermissions(){
        return "chat.invite";
    }
    @Override
    public String[] exec( MC_CustomCommand command ) throws TooFewArgumentsException,
    TooManyArgumentsException, PlayerOnlyException, InvalidArgumentException, NotOnlineException {
        CommandSender command_sender = command.getSender();
        //Assuming only the owner can invite
        if (command.getArgs().length > 1) {
            throw new TooManyArgumentsException();
        }

        if (!(command_sender instanceof Player)) {
            throw new PlayerOnlyException();
        }

        ChatMember sender_member = member_handler.getPlayer(command_sender.getName());
        ChatChannel invite_channel = sender_member.getInvite();
        if (invite_channel != null) {
            chat.getServer().getPluginManager().callEvent(new ChatChannelChangeEvent(sender_member, sender_member.getChatChannel(), invite_channel, "", false));
            return new String[]{"You have been moved to channel " + invite_channel.getName()};
        }
        else {
            return new String[]{ChatColor.RED + "The invite has expired."};
        }
    }
    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}








