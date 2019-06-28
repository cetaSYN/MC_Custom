package com.mc_custom.survival.commands;

/**
 * Class that handles command delegation.
 */
public class HomeCommand extends BaseCommand {


   public HomeCommand(MC_Custom_Core core){
      core.getCommandHandler().addCommand(this);
   }
   
   @Override
   public String getCommandName( ) {
      return "home";
   }

   @Override
   public void exec( MC_CustomCommand command ) throws TooManyArgumentsException, TooFewArgumentsException, NoPermissionException,
         NotImplementedException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {
      if (command.getArgs().length > 1) {
         throw new TooManyArgumentsException();
      }

      CommandSender sender = command.getSender();

      switch (command.getArgs().length) {
      case 0: //  /home
         home(sender, null);
         return;
      case 1: //  /home [name]
         home(sender, command.getArg(0));
         return;
      }
   }

   private void home( CommandSender sender, String home_name ) throws NoPermissionException, InvalidArgumentException {

      if (sender.hasPermission("survival.home")) {
         Player sender_player = ( Player ) sender;
         if (home_name == null) {
            //Display list of homes
         }
         else {
            Location home_location = sender_player.getLocation();
            //Warp to home with that name
         }
      }
      else {
         throw new NoPermissionException();
      }

   }
}