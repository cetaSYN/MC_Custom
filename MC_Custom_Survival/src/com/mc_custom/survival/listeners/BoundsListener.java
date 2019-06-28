package com.mc_custom.survival.listeners;

public class BoundsListener implements Listener{
	
	@EventHandler
	public void onMove(final PlayerMoveEvent event){
		
		double loc_x = event.getTo().getX();
      double loc_z = event.getTo().getZ();
		double max_x = DatabaseConfiguration.getMaxBound().getX();
		double min_x = DatabaseConfiguration.getMinBound().getX();
		double max_z = DatabaseConfiguration.getMaxBound().getZ();
		double min_z = DatabaseConfiguration.getMinBound().getZ();
      
		//Greater than max bounds
		if (loc_x < min_x || loc_x > max_x || loc_z < min_z || loc_z > max_z) {
		   event.setCancelled(true);
		}
		
//		if(loc.getX()>DatabaseConfiguration.getMaxBound().getX()){
//			loc.setX(DatabaseConfiguration.getMaxBound().getX() - 1);
//		}
//		if(loc.getZ()>DatabaseConfiguration.getMaxBound().getZ()){
//			loc.setZ(DatabaseConfiguration.getMaxBound().getZ() - 1);
//		}
//		//Lesser than min bounds.
//		if(loc.getX()<DatabaseConfiguration.getMinBound().getX()){
//			loc.setX(DatabaseConfiguration.getMinBound().getX() + 1);
//		}
//		if(loc.getZ()<DatabaseConfiguration.getMinBound().getZ()){
//			loc.setZ(DatabaseConfiguration.getMinBound().getZ() + 1);
//		}
	}
}
