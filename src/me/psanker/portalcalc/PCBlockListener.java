package me.psanker.portalcalc;

import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;

import me.psanker.portalcalc.persist.Portal;


public class PCBlockListener extends BlockListener {
	protected PCMain plugin;

	public PCBlockListener(PCMain plugin){
		this.plugin=plugin;
	}
	
	@Override
	
	public void onSignChange(SignChangeEvent e){
		Location l=e.getBlock().getLocation();
		
	}
	
	
	
	public void onBlockPhysics(BlockPhysicsEvent e){
		if(e.getBlock().getTypeId()==90&&e.getChangedTypeId()==90)
			if(plugin.handler.isPortalAt(e.getBlock().getLocation())){
				Portal p= plugin.handler.getPortalAt(e.getBlock().getLocation());
				PCLog.log("Portal removed from db: "+p.getName(), 0);
				plugin.handler.removePortal(p);
				
			}
			
		
	}
	

}
