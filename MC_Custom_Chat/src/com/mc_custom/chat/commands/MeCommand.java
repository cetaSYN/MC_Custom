package com.mc_custom.chat.commands;

import com.mc_custom.chat.MC_Custom_Chat;

/**
 * Class that handles command delegation.
 */
public class MeCommand extends BaseCommand {
    private MC_Custom_Chat chat;

    public MeCommand( MC_Custom_Chat chat ) {
        this.chat = chat;
        chat.getCore().getCommandHandler().addCommand(this);
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] { "me" };
    }

    @Override
    public String getRequiredPermissions( ) {
        return null;
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws TooManyArgumentsException,
            NoPermissionException, InvalidArgumentException, NotOnlineException {
        return null;
    }

    @Override
    public String[] getHelp( ) {
        throw new NotImplementedException();
    }
}