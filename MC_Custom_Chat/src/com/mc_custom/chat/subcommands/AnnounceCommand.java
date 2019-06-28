package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;

public class AnnounceCommand extends BaseCommand {
    private MC_Custom_Chat chat;
    public AnnounceCommand(MC_Custom_Chat chat){
        this.chat = chat;
    }
    
    @Override
    public String[] getCommandNames( ) {
        return new String[] {"announce"};
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException,
    TooFewArgumentsException {
        CommandSender sender = command.getSender();

        if (sender.hasPermission("chat.announce")) {
            if (command.getArgs().length < 1) {
                throw new TooFewArgumentsException();
            }

            String comment = "";

            for (int i = 1; i < command.getArgs().length; i++) {
                comment += " ";
                comment += command.getArg(i);
            }
            chat.getServer().broadcastMessage(comment);
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