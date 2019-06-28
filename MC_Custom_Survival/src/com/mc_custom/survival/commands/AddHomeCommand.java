package com.mc_custom.survival.commands;

import com.mc_custom.survival.MC_Custom_Survival;

/**
 * Class that handles command delegation.
 */
public class AddHomeCommand extends BaseCommand {

   public AddHomeCommand(MC_Custom_Survival core){
      core.getCore().getCommandHandler().addCommand(this);
   }
   
   
   @Override
   public String getCommandName( ) {
      return "addhome";
   }

   @Override
   public void exec( MC_CustomCommand command ) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
         NotImplementedException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

      CommandSender sender = command.getSender();
      
      if (sender.hasPermission("survival.addhome")) {
         if (command.getArgs().length > 1) {
            throw new TooManyArgumentsException();
         }
         if (command.getArgs().length == 0) {
            throw new TooFewArgumentsException();
         }

         Player sender_player = ( Player ) sender;
         String home_name = command.getArg(0);
         Location home_location = sender_player.getLocation();
         // Add home to with name to player's log
      }
   }
}