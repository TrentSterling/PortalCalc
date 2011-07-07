package me.psanker.portalcalc;

import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.WorldListener;
import org.bukkit.block.Block;


public class PCWorldListener extends WorldListener {
	protected PCMain plugin;
	
	public PCWorldListener(PCMain plugin){
		this.plugin = plugin;
	}
	
	public void onPortalCreate(PortalCreateEvent e){
		Block b = e.getBlocks().get(0);
		plugin.handler.recordPortalAt(b.getLocation());
		
	}
}
