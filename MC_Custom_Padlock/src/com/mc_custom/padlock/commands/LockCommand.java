package com.mc_custom.padlock.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mc_custom.padlock.MC_Custom_Padlock;
import com.mc_custom.padlock.lock.Padlock;
import com.mc_custom.padlock.lock.PadlockManager;
@SuppressWarnings({"deprecation"})
public class LockCommand extends BaseCommand {

    private MC_Custom_Padlock plugin;
    private PlayerHandler<CorePlayer> player_handler;
    private PadlockManager<Padlock> padlock_manager;

    public LockCommand( MC_Custom_Padlock plugin  ) {
        this.plugin = plugin;
        this.player_handler = plugin.getPlayerHandler();
        this.padlock_manager = plugin.getPadlockManager();
    }

    @Override
    public String[] getCommandNames( ) {
        return new String[] {"lock","private"};
    }

    @Override
    public String getRequiredPermissions() {
        return "Padlock.lock";
    }

    @Override
    public String[] exec( MC_CustomCommand command ) throws TooFewArgumentsException, TooManyArgumentsException,
    NoPermissionException, NotImplementedException, InvalidArgumentException, NotOnlineException {

        if(!(command.getSender() instanceof Player)){
            return new String[]{"This is a player-only command"};
        }
        Player player = (Player)command.getSender();
        String allow_redstone = command.getParam("redstone");
        String allowed_users = command.getParam("users").replace(",", "");
        String password = command.getParam("pasword");
        String timer = command.getParam("timer");
        List<UUID> users = new ArrayList<UUID>();
        if(allow_redstone.equalsIgnoreCase("")){
            allow_redstone = "false";
        }
        if(timer.equalsIgnoreCase("")){
            timer = "0";
        }
        if(password.equalsIgnoreCase("")){
            password = "";
        }
        if(!allowed_users.equalsIgnoreCase("")){
            for(String user : allowed_users.split(" ")){
                users.add(Bukkit.getOfflinePlayer(user).getUniqueId());
            }
        }
        try{
            Block target = player.getTargetBlock(null, 5);
            if(!target.getType().equals(Material.CHEST) && !target.getType().equals(Material.WOODEN_DOOR)
                    && !target.getType().equals(Material.IRON_DOOR_BLOCK) && !target.getType().equals(Material.TRAPPED_CHEST)
                    && !target.getType().equals(Material.TRAP_DOOR) && !target.getType().equals(Material.FURNACE)){
                return new String[]{"[Padlock] That block cannot be locked!"};
            }
            int lock_timer = Integer.parseInt(timer);
            if(lock_timer < 1){
                throw new NumberFormatException("Number is less than 1");
            }
            //Add the padlock to the block, and any adjascent private block (Doors, double chests)
            padlock_manager.addPadlock(new Padlock(target, player.getUniqueId(), users, password,
                    lock_timer, Boolean.parseBoolean(allow_redstone)));
        }
        catch(NumberFormatException e){
            return new String[] {"The parameter <timer> must be an integer greater than 1"};
        }
        return new String[]{"[Padlock] Successfully added the padlock!"};
    }

    @Override
    public String[] getHelp() {
        return new String[] {
                "Syntax: /lock [users:AllowedUsers] [redstone:true|false] [password:Password] [timer:Seconds]",
                "Lock the targeted block",
                "/private"
        };
    }
}