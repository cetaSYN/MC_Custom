package com.mc_custom.survival.commands;

/**
 * Class that handles command delegation.
 */
public class WarpCommand extends BaseCommand {

   @Override
   public String getCommandName( ) {
      return "warp";
   }

   @Override
   public void exec( MC_CustomCommand command ) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
         NotImplementedException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {
      if (command.getArgs().length > 2) {
         throw new TooManyArgumentsException();
      }

      CommandSender sender = command.getSender();

      switch (command.getArgs().length) {
      case 0: //  /warp [place]
         warp(sender, command.getArg(0));
         return;
      }
   }

   private void warp( CommandSender sender, String warp ) throws NoPermissionException, InvalidArgumentException {

      Player sender_player = ( Player ) sender;

      if (sender.hasPermission("survival.warp")) {
         //warp sender to location
         sender_player.sendMessage("Not in use yet.");
      }
      else {
         throw new NoPermissionException();
      }
   }
}