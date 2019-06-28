package com.mc_custom.chat.subcommands;

import java.util.HashMap;
import java.util.Map;

import com.mc_custom.chat.MC_Custom_Chat;

public class HighlightCommand extends BaseCommand {
    private MC_Custom_Chat chat;
    public HighlightCommand(MC_Custom_Chat chat){
        this.chat = chat;
    }
    
    @Override
    public String[] getCommandNames( ) {
        return new String[] {"highlight"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException {
        CommandSender sender = command.getSender();

        if (sender.hasPermission("chat.highlight")) {
            if (command.getArgs().length < 1) {
                throw new TooFewArgumentsException();
            }
            if(command.getArgs().length > 2){
                throw new TooManyArgumentsException();
            }
            ChatColor color = ChatColor.RESET;
            if(command.getArgs().length == 2){
                if(ChatColor.valueOf(command.getArg(1).toUpperCase()) != null){
                    color = ChatColor.valueOf(command.getArg(0).toUpperCase());
                }
            }
            Map<String, ChatColor> highlight = new HashMap<String, ChatColor>();
            if(chat.getHighlightList().containsKey(sender.getName())){
                highlight = chat.getHighlightList().get(sender.getName());
            }
            highlight.put(command.getArg(0), color);
            chat.getHighlightList().put(sender.getName(), highlight);
        }
        else {
            throw new NoPermissionException();
        }
        return null;
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.announce";
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}