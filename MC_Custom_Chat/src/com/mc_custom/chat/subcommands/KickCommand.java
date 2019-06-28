package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.member.ChatMember;
import com.mc_custom.chat.member.MemberHandler;

public class KickCommand extends BaseCommand {

    private MemberHandler member_handler;

    public KickCommand( MC_Custom_Chat chat ) {
        member_handler = chat.getMemberHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"kick"};
    }
    @SuppressWarnings("deprecation")
    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException, PlayerOnlyException, NotOnlineException {
        CommandSender command_sender = command.getSender();
        if (command_sender.hasPermission("chat.kick")) {
            String sender_name = command_sender.getName();

            if (command.getArgs().length > 2) {
                throw new TooManyArgumentsException();
            }

            if (command.getArgs().length < 2) {
                throw new TooFewArgumentsException();
            }

            if (command_sender instanceof ConsoleCommandSender) {
                throw new PlayerOnlyException();
            }

            // /chat kick [player]
            ChatChannel chat_channel = member_handler.getPlayer(sender_name).getChatChannel();
            String owner = chat_channel.getOwnerName();

            ChatMember kick_player = member_handler.getPlayer(command.getArg(1));

            if (sender_name == owner) {
                if (sender_name != kick_player.getPlayerName()) {
                    if (chat_channel.getName() == kick_player.getChatChannel().getName()) {

                        chat_channel.removeMember(kick_player);
                        kick_player.setChatChannel(MC_Custom_Chat.global_chat);
                        kick_player.setInvite(null);

                        Player player = Bukkit.getServer().getPlayer(command.getArg(1));
                        if (player != null) {
                            player.sendMessage(ChatColor.GOLD + "You have been kick from the channel.");
                        }
                        else {
                            return new String[]{command.getArg(1) + " is not online!"};
                        }
                    }
                }
                else {
                    return new String[]{ChatColor.RED + "Cannot kick yourself!"};
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
        return "chat.kick";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}