package com.mc_custom.chat.commands;

import com.mc_custom.chat.MC_Custom_Chat;

public class NickCommand extends BaseCommand{
    private MC_Custom_Chat chat;
    public NickCommand(MC_Custom_Chat chat){
        this.chat = chat;
    }
    @Override
    public String[] getCommandNames() {
        return new String[]{"nick", "nickname"};
    }

    @Override
    public String getRequiredPermissions() {
        return "chat.nick";
    }

    @Override
    public String[] exec(MC_CustomCommand command)
            throws TooManyArgumentsException, TooFewArgumentsException,
            NoPermissionException, NotImplementedException,
            InvalidArgumentException, PlayerOnlyException, NotOnlineException {
        // TODO Auto-generated method stub
        CommandSender sender = command.getSender();
        if(!(sender instanceof Player)){
            throw new PlayerOnlyException();
        }
        if(command.getArgs().length < 1){
            throw new TooFewArgumentsException();
        }
        if(command.getArgs().length > 2){
            throw new TooManyArgumentsException();
        }
        if(!sender.hasPermission("chat.nick")){
            throw new NoPermissionException();
        }
        if(command.getArgs().length == 2){
            if(!sender.hasPermission("chat.nick.others")){
                throw new NoPermissionException();
            }
            Player player = Bukkit.getPlayer(command.getArg(0));
            if(player.isOnline()){
                if(command.getArg(1).equalsIgnoreCase("off")){
                    player.setDisplayName(null);
                    return new String[]{player.getName() + "'s nickname has been removed"};
                }
                String format_name = ChatColor.translateAlternateColorCodes('&', command.getArg(1));
                if(!sender.hasPermission("chat.color")){
                    format_name = ChatColor.stripColor(format_name);
                }
                player.setDisplayName(format_name);
                return new String[]{player.getName() + "'s nickname is now ~" + format_name};
            }
            return new String[]{ChatColor.RED + command.getArg(0) + " is not online!"};
        }
        Player player = (Player)sender;
        if(command.getArg(1).equalsIgnoreCase("off")){
            player.setDisplayName(null);
            return new String[]{"Your nickname has been removed"};
        }
        String format_name = ChatColor.translateAlternateColorCodes('&', command.getArg(1));
        if(!sender.hasPermission("chat.color")){
            format_name = ChatColor.stripColor(format_name);
        }
        player.setDisplayName(format_name);
        return new String[]{"Your nickname is now ~" + format_name};
    }

    @Override
    public String[] getHelp() {
        // TODO Auto-generated method stub
        return null;
    }

}
