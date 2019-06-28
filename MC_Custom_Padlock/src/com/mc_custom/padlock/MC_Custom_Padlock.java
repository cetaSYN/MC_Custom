package com.mc_custom.padlock;

import com.mc_custom.padlock.commands.LockCommand;
import com.mc_custom.padlock.commands.OverrideCommand;
import com.mc_custom.padlock.listeners.BlockListener;
import com.mc_custom.padlock.lock.Padlock;
import com.mc_custom.padlock.lock.PadlockManager;

public class MC_Custom_Padlock extends JavaPlugin{
    private PlayerHandler<CorePlayer> player_handler;
    private PadlockManager<Padlock> padlock_manager;
    private MC_Custom_Core core;
    @Override
    public void onEnable( ) {
        /* Get Handle to Core */
        try {
            core = ( MC_Custom_Core ) getServer().getPluginManager().getPlugin("MC_Custom_Core");
        }
        catch (Exception ex) {
            Bukkit.getLogger().severe(ex.getMessage());
            ex.printStackTrace();
            Bukkit.getLogger().severe("Error linking to MC_Custom_Core! Shutting Down!");
            this.getServer().shutdown();
        }

        player_handler = core.getPlayerHandler();
        padlock_manager = new PadlockManager<Padlock>();
        /* Register Listeners */
        Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
        /* Register Commands */
        CommandHandler<BaseCommand> command_handler = core.getCommandHandler();
        command_handler.addCommand(new LockCommand(this));
        command_handler.addCommand(new OverrideCommand(this));
        /* Add recipes */
    }
    public MC_Custom_Core getCore( ) {
        return core;
    }
    public PlayerHandler<CorePlayer> getPlayerHandler(){
        return player_handler;
    }
    public PadlockManager<Padlock> getPadlockManager(){
        return padlock_manager;
    }
}
