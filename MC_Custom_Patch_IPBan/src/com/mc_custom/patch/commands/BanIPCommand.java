package com.mc_custom.patch.commands;

import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.patch.MC_Custom_Patch_IPBan;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mc_custom.core.commands.BaseCommand;
import com.mc_custom.core.commands.MC_CustomCommand;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;

public class BanIPCommand extends BaseCommand {

    private MC_Custom_Patch_IPBan plugin;

    public BanIPCommand(MC_Custom_Patch_IPBan plugin){
        this.plugin = plugin;
    }

    @Override
    public String[] getCommandNames() {
        return new String[]{"banip", "ipban"};
    }

    @Override
    public String getRequiredPermissions() {
        return "punish.ban";
    }

    @Override
    public String[] exec(MC_CustomCommand command) throws TooFewArgumentsException,
            NoPermissionException, InvalidArgumentException, TooManyArgumentsException {
        CommandSender command_sender = command.getSender();
        if (command.getArgs().length < 1) {
            throw new TooFewArgumentsException();
        }
        if(command.getArgs().length > 1){
            throw new TooManyArgumentsException();
        }

        Player player = Bukkit.getPlayer(command.getArg(0));
        if (player != command_sender) {
            if (player != null && player.isOnline()) { //if they are online, they are not banned
                if (command_sender.hasPermission("punish.override") || !player.hasPermission("punish.ban")) {
                    plugin.getBannedIPs().add(player.getAddress().getAddress().toString());
                    player.kickPlayer("You have been sent to the moon!");
                    for(Player list_p : Bukkit.getOnlinePlayers()){
                        if(player.getAddress().toString().equals(list_p.getAddress().getAddress().toString())){
                            list_p.kickPlayer("You have been sent to the moon!");
                        }
                    }
                    plugin.getServer().broadcast(ChatColor.RED + player.getName() + "(" + player.getAddress().getAddress().toString() +
                            ") has been IP banned.", this.getRequiredPermissions());
                    return new String[]{player.getName() + "(" + player.getAddress().getAddress().toString() + ") has been IP banned."};
                }
                else {
                    return new String[]{"You cannot ban staff!"};
                }
            }
            return new String[]{"This player is not online.", "IP Banning is currently in patch-state " +
                    "and requires online players.", "Please contact a tech for more details on IP banning."};
        }
        else {
            return new String[]{"You cannot ban yourself!"};
        }
    }

    @Override
    public String[] getHelp() {
        return new String[]{
                "Temporarily bans an IP address from the server.",
                "NOTE: IP BAN IS REMOVED WHEN SERVER RESTARTS.",
                "NOTE: DOES NOT BAN PLAYERS ACCOUNT",
                "Syntax: /banip <playername>"
        };
    }
}