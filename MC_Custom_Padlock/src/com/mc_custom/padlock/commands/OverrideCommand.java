package com.mc_custom.padlock.commands;

import com.mc_custom.padlock.EntityMetadata;
import com.mc_custom.padlock.MC_Custom_Padlock;
import com.mc_custom.padlock.lock.Padlock;
import com.mc_custom.padlock.lock.PadlockManager;
public class OverrideCommand extends BaseCommand {

    private MC_Custom_Padlock plugin;
    private PlayerHandler<CorePlayer> player_handler;
    private PadlockManager<Padlock> padlock_manager;

    public OverrideCommand( MC_Custom_Padlock plugin  ) {
        this.plugin = plugin;
        this.player_handler = plugin.getPlayerHandler();
        this.padlock_manager = plugin.getPadlockManager();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"masterkey","ignorelocks"};
    }

    @Override
    public String getRequiredPermissions() {
        return "Padlock.override";
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws TooFewArgumentsException, TooManyArgumentsException,
    NoPermissionException, NotImplementedException, InvalidArgumentException, NotOnlineException {
        if(command.getArgs().length > 0){
            throw new TooManyArgumentsException();
        }
        if(!(command.getSender() instanceof Player)){
            return new String[]{"This is a player-only command"};
        }
        Player player = (Player)command.getSender();
        if(player.hasMetadata("padlockOverride")){
            player.setMetadata("padlockOverride", new EntityMetadata(plugin,
                    !player.getMetadata("padlockOverride").get(0).asBoolean()));
        }
        else{
            player.setMetadata("padlockOverride", new EntityMetadata(plugin, true));
        }
        return new String[]{"[Padlock] Set padlock override to " + player.getMetadata("padlockOverride").get(0).asBoolean()};
    }

    @Override
    public String[] getHelp() {
        return new String[] {
                "Syntax: /masterkey",
                "Toggle whether you can access private blocks",
                "/ignorelocks"
        };
    }
}