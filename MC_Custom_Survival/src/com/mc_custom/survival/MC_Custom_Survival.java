package com.mc_custom.survival;

import com.mc_custom.survival.commands.TeleportCommand;
import com.mc_custom.survival.commands.TpposCommand;
import com.mc_custom.survival.listeners.BoundsListener;
import com.mc_custom.survival.listeners.ExploitListener;

public class MC_Custom_Survival extends JavaPlugin {
	
	private MC_Custom_Core core;
	
	@Override
	public void onEnable(){
		/* Get Handle to Core */
		try{
			core = (MC_Custom_Core) getServer().getPluginManager().getPlugin("MC_Custom_Core");
    	}
    	catch(Exception ex){
    		Bukkit.getLogger().severe(ex.getMessage());
    		ex.printStackTrace();
    		Bukkit.getLogger().severe("Error linking to MC_Custom_Core! Shutting Down!");
    		this.getServer().shutdown();
    	}
		
		/* Register Listeners */
		this.getServer().getPluginManager().registerEvents(new BoundsListener(), this);
        this.getServer().getPluginManager().registerEvents(new ExploitListener(), this);
		
		/* Register Commands */
      new TpposCommand(this);
		new TeleportCommand(this);
	}
	
	public MC_Custom_Core getCore(){
		return core;
	}
}