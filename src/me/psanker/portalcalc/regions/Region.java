package me.psanker.portalcalc.regions;

//import me.psanker.portalcalc.PCLog; // Debug purposes

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.World.Environment;

public class Region {
    
//	private static PCLog log = new PCLog(); // Debug purposes
	
	public int x1, x2, y1, y2, z1, z2;  
  
    // Variations on creating region
	
	protected Region(int x1_, int y1_, int z1_, int x2_, int y2_, int z2_){
		x1=x1_;
		x2=x2_;
		y1=y1_;
		y2=y2_;
		z1=z1_;
		z2=z2_;
	}
	
	public Region(Location loc, boolean nether){
        this(
        		loc.getBlockX()-(nether? 16: 127),
        		1,
        		loc.getBlockZ() - (nether? 16: 127),
        		loc.getBlockX()+(nether? 16: 127),
        		128,
        		loc.getBlockZ()+(nether? 16: 127));
	}

	public Region(Player p){
		this(p.getLocation(), p.getWorld().getEnvironment()==Environment.NETHER);
	}
	
	public Region regionByOfsettingRegion(int[] offset){
		return new Region(
				this.x1+offset[0],
				this.y1+offset[1],
				this.z1+offset[2],
				this.x2+offset[0],
				this.y2+offset[1],
				this.z2+offset[2]);
	}

    // Chunk utilities
    /*public Region newChunk(Region reg) { //What exactly is the point of this method?
    	Region chunk = new Region();
    	
    	chunk.x1 = reg.x1;
    	chunk.y1 = 1;
    	chunk.z1 = reg.z1;
    	
    	chunk.x2 = chunk.x1 + 15;
    	chunk.y2 = 128;
    	chunk.z2 = chunk.z1 + 15;
    	
    	return chunk;
    }*/
    
}
