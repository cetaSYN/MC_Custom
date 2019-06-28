package com.mc_custom.chat.subcommands;

import com.mc_custom.chat.MC_Custom_Chat;
import com.mc_custom.chat.channels.ChannelHandler;

public class AddCommand extends BaseCommand{

   private ChannelHandler channel_handler;
   
   public AddCommand(MC_Custom_Chat chat){
      channel_handler = chat.getChannelHandler();
   }
   
   @Override
   public String[] getCommandNames( ){
      return new String[] {"rm"};
   }
   
    @Override
   public String[] exec(MC_CustomCommand command) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException, TooFewArgumentsException{
      return null;
    }

    @Override
    public String getRequiredPermissions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }
}
