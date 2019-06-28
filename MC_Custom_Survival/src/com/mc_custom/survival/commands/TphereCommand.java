package com.mc_custom.survival.commands;

import com.mc_custom.survival.MC_Custom_Survival;

/**
 * Class that handles command delegation.
 */
public class TphereCommand extends BaseCommand //TODO: Console handling.
{

   public TphereCommand( MC_Custom_Survival survival ) {
      survival.getCore().getCommandHandler().addCommand(this);
   }

   @Override
   public String getCommandName( ) {
      return "tphere";
   }

   @Override
   public void exec( MC_CustomCommand command ) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
         NotImplementedException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

      CommandSender command_sender = command.getSender();

      if (command_sender.hasPermission("teleport.tphere")) {
         if (command.getArgs().length > 1) {
            throw new TooManyArgumentsException();
         }

         Player sender_player = ( Player ) command_sender;
         Player player = Bukkit.getPlayer(command.getArg(0));
         if (player != null) {
         player.teleport(sender_player);
         }
         else {
            throw new NotOnlineException();
         }

      }
      else {
         throw new NoPermissionException();
      }
   }
}