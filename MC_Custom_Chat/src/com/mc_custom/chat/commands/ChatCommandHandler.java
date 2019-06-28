package com.mc_custom.chat.commands;

import java.util.LinkedList;

import com.mc_custom.chat.MC_Custom_Chat;

public class ChatCommandHandler extends BaseCommand{
	private LinkedList<BaseCommand> command_list = new LinkedList<BaseCommand>();
	//private HelpCommand help_command = new HelpCommand();
	
	public ChatCommandHandler(MC_Custom_Chat chat){
		chat.getCore().getCommandHandler().addCommand(this);
		//this.addCommand(help_command);
	}
	
	public String[] getCommandNames() {
		return new String[] {"chat"};
	}
	
	public String getRequiredPermissions(){
	    return "chat";
	}
	
	public String[] getHelp(){
	    return null;
	}
	
	public String[] exec(MC_CustomCommand command) throws NoPermissionException, InvalidArgumentException, TooManyArgumentsException, TooFewArgumentsException{
		try{
			if(command.getArgs().length >= 1){
				String command_name = command.getArg(0); // Command is /chat [command], so the 0 arg is the subcommand we are looking for.

				for(BaseCommand i_command : command_list){
					if(i_command.getCommandNames()[0].equalsIgnoreCase(command_name)){
						return i_command.exec(command);
					}
				}
			}
			//help_command.exec(command);
		}
		catch(NoPermissionException ex){
			return new String[]{ChatColor.RED + "You do not have permission to use this!"};
		}
		catch(TooManyArgumentsException ex){
			return new String[]{ChatColor.RED + "Too many parameters!"};
		}
		catch(TooFewArgumentsException ex){
			return new String[]{ChatColor.RED + "Too few parameters!"};
		}
		catch(InvalidArgumentException ex){
			return new String[]{ChatColor.RED + ex.getMessage()};
		}
		catch(NotImplementedException ex){
			return new String[]{ChatColor.RED + "This command has not yet been implemented!"};
		}
		catch(PlayerOnlyException ex){
			return new String[]{ChatColor.RED + "This command can only be used by a player!"};
		}
		catch(NotOnlineException ex){
			return new String[]{ChatColor.RED + "The specified player is not online!"};
		}
		return new String[]{};
	}
	
	/**
	 * Adds a command to the list of commands to check for processing.
	 */
	public void addCommand(BaseCommand command){
		command_list.add(command);
	}
}