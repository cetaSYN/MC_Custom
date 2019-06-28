package com.mc_custom.survival.commands;

import com.mc_custom.survival.MC_Custom_Survival;

/**
 * Class that handles command delegation.
 */
public class TpposCommand extends BaseCommand {

   public TpposCommand( MC_Custom_Survival survival ) {
      survival.getCore().getCommandHandler().addCommand(this);
   }

   @Override
   public String getCommandName( ) {
      return "tppos";
   }

   @Override
   public void exec( MC_CustomCommand command ) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
         NotImplementedException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

      CommandSender command_sender = command.getSender();
      if (command_sender.hasPermission("teleport.tppos")) {
         if (command.getArgs().length > 3) {
            throw new TooManyArgumentsException();
         }
         
         if (command.getArgs().length < 3) {
            throw new TooFewArgumentsException();
         }

         Player sender_player = ( Player ) command_sender;

         //TODO: check if player can fit at location
         try {
            Location teleport_location = new Location(sender_player.getWorld(), Integer.parseInt(command.getArg(0)), Integer.parseInt(command
                  .getArg(1)), Integer.parseInt(command.getArg(2)));

            //teleport sender to location
            sender_player.teleport(teleport_location);

         }
         catch (NumberFormatException e) {
            command_sender.sendMessage("Not a valid location");
         }
      }
      else {
         throw new NoPermissionException();
      }
   }
}