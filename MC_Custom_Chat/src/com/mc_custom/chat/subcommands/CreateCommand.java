package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChannelHandler;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.events.ChatChannelChangeEvent;
import com.mc_custom.chat.exceptions.ChannelAlreadyExistsException;
import com.mc_custom.chat.member.ChatMember;
import com.mc_custom.chat.member.MemberHandler;

public class CreateCommand extends BaseCommand {

    private ChannelHandler channel_handler;
    private MemberHandler member_handler;
    private MC_Custom_Chat chat;

    public CreateCommand( MC_Custom_Chat chat ) {
        this.chat = chat;
        channel_handler = chat.getChannelHandler();
        member_handler = chat.getMemberHandler();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"create"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException, PlayerOnlyException, NotOnlineException {
        CommandSender command_sender = command.getSender();

        if (command_sender.hasPermission("chat.create")) {

            if (command.getArgs().length > 3) {
                throw new TooManyArgumentsException();
            }

            if (command_sender instanceof ConsoleCommandSender) {
                throw new PlayerOnlyException();
            }
            String status;
            switch (command.getArgs().length) {
            case 2: //  /create [channel]
                status = createChannel(command_sender, command.getArg(1), "");
                return new String[]{status};
            case 3: //  /create [channel] [password]
                status = createChannel(command_sender, command.getArg(1), command.getArg(2));
                return new String[]{};
            }
        }
        else {
            throw new NoPermissionException();
        }
        return new String[]{};
    }

    private String createChannel( CommandSender command_sender, String channel_name, String password ) {
        try {
            ChatChannel new_channel = new ChatChannel(channel_name, command_sender.getName());
            new_channel.setPassword(password);

            channel_handler.addChannel(new_channel);
            ChatMember creator = member_handler.getPlayer(command_sender.getName());

            chat.getServer().getPluginManager().callEvent(new ChatChannelChangeEvent(creator, creator.getChatChannel(), new_channel, password, false));
            return ChatColor.GOLD + "You have created and been moved to channel " + new_channel.getName();
        }
        catch (ChannelAlreadyExistsException ex) {
            return ChatColor.RED + "That channel already exists!";
        }
        catch (NotOnlineException e) {
            return command_sender.getName() + " is not online!";
            //Should never happen as Sender should always be online...
        }
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.create";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}
