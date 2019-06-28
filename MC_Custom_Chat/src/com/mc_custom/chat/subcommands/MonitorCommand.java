package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.member.ChatMember;
import com.mc_custom.chat.member.MemberHandler;

public class MonitorCommand extends BaseCommand {

    private MemberHandler member_handler;
    private MC_Custom_Chat chat;

    public MonitorCommand( MC_Custom_Chat chat ) {
        this.chat = chat;
        member_handler = chat.getMemberHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"monitor"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException, NotOnlineException, PlayerOnlyException {
        // /chat monitor
        CommandSender command_sender = command.getSender();

        if (!(command_sender instanceof Player)) {
            throw new PlayerOnlyException();
        }

        if (command_sender.hasPermission("chat.monitor")) {

            ChatMember member = member_handler.getPlayer(command_sender.getName());
            if (chat.toggleMonitor(member)) {
                return new String[]{ChatColor.BLUE + "You are now monitoring chat channels"};
            }
            else {
                return new String[]{ChatColor.BLUE + "You stopped monitoring chat channels"};
            }
        }
        return new String[]{};
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.monitor";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}