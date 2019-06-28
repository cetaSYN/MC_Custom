package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.member.MemberHandler;

public class PasswordCommand extends BaseCommand {

    private MemberHandler member_handler;

    public PasswordCommand( MC_Custom_Chat chat ) {
        member_handler = chat.getMemberHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"pass"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException, NotOnlineException, PlayerOnlyException {
        CommandSender command_sender = command.getSender();
        if (command_sender.hasPermission("chat.pass")) {
            if (command.getArgs().length > 2) {
                throw new TooManyArgumentsException();
            }
            if (!(command_sender instanceof Player)) {
                throw new PlayerOnlyException();
            }

            // /pass
            ChatChannel chat_channel = member_handler.getPlayer(command_sender.getName()).getChatChannel();
            String owner = chat_channel.getOwnerName();

            if (command_sender.getName() == owner) {
                chat_channel.setPassword(command.getArg(1));
                return new String[]{ChatColor.GOLD + "Your channel is now using a password"};
            }
            else {
                return new String[]{ChatColor.RED + "You are not the owner of this channel."};
            }

        }
        else {
            throw new NoPermissionException();
        }
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.pass";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }

}
