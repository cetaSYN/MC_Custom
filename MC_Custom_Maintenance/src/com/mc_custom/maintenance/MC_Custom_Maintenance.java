package com.mc_custom.maintenance;

/**
 * TODO: Multi-port file-based handling.
 */
public class MC_Custom_Maintenance extends JavaPlugin
{
	@Override public void onEnable( ){
		this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
	}
	
	private class JoinListener implements Listener{
		
		@EventHandler
		public void onJoin(AsyncPlayerPreLoginEvent event){
			event.setLoginResult(Result.KICK_OTHER);
			event.setKickMessage("The server is currently down for maintenance!\n\n" +
					"Please visit our forums for details!\nhttp://url_removed/forum");
		}
	}
}
