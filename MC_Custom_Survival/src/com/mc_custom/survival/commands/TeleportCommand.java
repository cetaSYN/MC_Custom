package com.mc_custom.survival.commands;

import com.mc_custom.survival.MC_Custom_Survival;

/**
 * Class that handles command delegation.
 */
public class TeleportCommand extends BaseCommand //TODO: Console handling.
{

   public TeleportCommand( MC_Custom_Survival survival ) {
      survival.getCore().getCommandHandler().addCommand(this);
   }

   @Override
   public String getCommandName( ) {
      return "tp";
   }

   @Override
   public void exec( MC_CustomCommand command ) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
         NotImplementedException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

      CommandSender sender = command.getSender();

      if (sender.hasPermission("teleport.tp")) {
         if (command.getArgs().length > 2) {
            throw new TooManyArgumentsException();
         }

         switch (command.getArgs().length) {
         case 1: //  /tp [player]
            teleport(sender, command.getArg(0), null);
            return;
         case 2: //  /tp [player] [player]
            teleport(sender, command.getArg(0), command.getArg(1));
            return;
         }
      }
      else {
         throw new NoPermissionException();
      }
   }

   private void teleport( CommandSender sender, String name1, String name2 ) throws NoPermissionException, InvalidArgumentException {

      Player sender_player = ( Player ) sender;
      Player player1 = Bukkit.getPlayer(name1);
      Player player2 = Bukkit.getPlayer(name2);
      //teleport sender to player
      if (player1 == null) {
         sender_player.sendMessage("Player not found!");
         return;
      }

      if (player2 == null) {
         sender_player.teleport(player1);
      }
      //teleport player1 to player2
      else {
         player1.teleport(player2);
      }

   }
}