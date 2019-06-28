package com.mc_custom.survival.commands;

import com.mc_custom.survival.MC_Custom_Survival;

/**
 * Class that handles command delegation.
 */
public class RemoveHomeCommand extends BaseCommand {

   public RemoveHomeCommand(MC_Custom_Survival core){
      core.getCore().getCommandHandler().addCommand(this);
   }
   
   
   @Override
   public String getCommandName( ) {
      return "rmhome";
   }

   @Override
   public void exec( MC_CustomCommand command ) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
         NotImplementedException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

      CommandSender sender = command.getSender();
      
      if (sender.hasPermission("survival.rmhome")) {
         if (command.getArgs().length > 1) {
            throw new TooManyArgumentsException();
         }
         if (command.getArgs().length == 0) {
            throw new TooFewArgumentsException();
         }

         Player sender_player = ( Player ) sender;
         String home_name = command.getArg(0);
         // remove home with home_name from player's log
      }
   }
}