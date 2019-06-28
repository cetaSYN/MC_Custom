package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.member.MemberHandler;

public class InviteCommand extends BaseCommand {

    private MemberHandler member_handler;

    public InviteCommand( MC_Custom_Chat chat ) {
        member_handler = chat.getMemberHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"invite"};
    }
    @SuppressWarnings("deprecation")
    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException, PlayerOnlyException, NotOnlineException {
        CommandSender command_sender = command.getSender();
        if (command_sender.hasPermission("chat.invite")) {
            //Assuming only the owner can invite
            if (command.getArgs().length > 2) {
                throw new TooManyArgumentsException();
            }

            if (command.getArgs().length < 2) {
                throw new TooFewArgumentsException();
            }

            if (command_sender instanceof ConsoleCommandSender) {
                throw new PlayerOnlyException();
            }

            // /chat invite [player]
            ChatChannel chat_channel = member_handler.getPlayer(command_sender.getName()).getChatChannel();
            if (command_sender.getName() == chat_channel.getOwnerName()) {

                Player player = Bukkit.getServer().getPlayer(command.getArg(1));
                if (player == null) {
                    throw new NotOnlineException();
                }

                Player sender_player = Bukkit.getServer().getPlayer(chat_channel.getOwnerName());
                if (command_sender.getName() != player.getName()) {
                    member_handler.getPlayer(player.getName()).setInvite(chat_channel);
                    player.sendMessage(ChatColor.GOLD + "You have been invited by " + sender_player.getDisplayName() + " to join "
                            + chat_channel.getName() + ".");
                    player.sendMessage(ChatColor.GOLD + "Use /chat accept to accept.");
                    return new String[]{ChatColor.GOLD + "You invited " + player.getDisplayName() + " to your channel."};
                }

                else {
                    return new String[]{ChatColor.RED + "Cannot invite yourself!"};
                }
            }
        }
        else {
            throw new NoPermissionException();
        }
        return new String[]{};
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.invite";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}