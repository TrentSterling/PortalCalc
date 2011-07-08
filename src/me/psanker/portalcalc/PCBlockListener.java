package me.psanker.portalcalc;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.block.Block;

import me.psanker.portalcalc.persist.Portal;


public class PCBlockListener extends BlockListener {
	protected PCMain plugin;

	public PCBlockListener(PCMain plugin){
		this.plugin=plugin;
	}
	
	@Override
	
	public void onSignChange(SignChangeEvent e){
		Location l=e.getBlock().getLocation();
		Portal p = plugin.handler.findPortalVeryNear(l);
		if(p==null)
			return;
		if(p.isBlockAdjacent(l)){
			p.setName(e.getLine(0));
			plugin.handler.savePortal(p);
		}
		
	}
	
	
	
	public void onBlockPhysics(BlockPhysicsEvent e){
	//	PCLog.log("physics "+e.getBlock().getTypeId()+", "+e.getChangedTypeId(), 0);

		if(e.getBlock().getTypeId()==90&&e.getChangedTypeId()==90)
			if(plugin.handler.isPortalAt(e.getBlock().getLocation())){
				Portal p= plugin.handler.getPortalAt(e.getBlock().getLocation());
				plugin.handler.removePortal(p);
				
			}
			
		
	}


}
