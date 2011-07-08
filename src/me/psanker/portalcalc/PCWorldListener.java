package me.psanker.portalcalc;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.WorldListener;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;


public class PCWorldListener extends WorldListener {
	protected PCMain plugin;
	
	public PCWorldListener(PCMain plugin){
		this.plugin = plugin;
	}
	
	public void onPortalCreate(PortalCreateEvent e){
		// WHY DOES CRAFTBUKKIT DO THIS TO MEEEEEEE
		boolean found = false;
		int i=0;
		HashSet<Block> set = new HashSet<Block>();
		
		for(Block s : e.getBlocks()){
			int count=0;
			for(BlockFace f : new BlockFace[]{BlockFace.NORTH,
					BlockFace.EAST,
					BlockFace.WEST,
					BlockFace.SOUTH,
					BlockFace.UP,
					BlockFace.DOWN}){
				
				if(e.getBlocks().contains(s.getRelative(f)))
					count++;
				
			}
			if(count==4)
				set.add(s);
		}
		
		if(set.isEmpty())
			return;
		Block bl=null;
		for(Block b: set){
			if(bl==null){
				bl=b; continue;
			}
			
			if(bl.getX() > b.getX())
				bl=b;
			if(bl.getY() > b.getY())
				bl=b;
			if(bl.getZ() > b.getZ())
				bl=b;
		}
		
		plugin.handler.recordPortalAt(bl.getLocation(), true);
		
		
		//blocks.watch(set);
		
	}
}
