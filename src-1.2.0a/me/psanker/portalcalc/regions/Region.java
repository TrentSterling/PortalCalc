package me.psanker.portalcalc.regions;

import me.psanker.portalcalc.PCLog; // Debug purposes

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Region {
    
	private static PCLog log = new PCLog(); // Debug purposes
	
	public int x1, x2, y1, y2, z1, z2;  
  
    // Variations on creating region
  
    public Region newRegionFromLocation(Location loc) {
        Region region = new Region();
      
        region.x1 = (int) (loc.getX() - 127);
        region.y1 = 1;
        region.z1 = (int) (loc.getZ() - 127);

        region.x2 = (int) (loc.getX() + 127);
        region.y2 = 128;
        region.z2 = (int) (loc.getZ() + 127);

        return region;
    }

    public void newRegionFromPlayer(Player player) {
        Location loc = player.getLocation();
        this.newRegionFromLocation(loc);
    }

    public Region newRegionInNether(Location loc) {
        Region region = new Region();
        region.x1 = (int) (loc.getX() - 16);
        region.y1 = 1;
        region.z1 = (int) (loc.getZ() - 16);

        region.x2 = (int) (loc.getX() + 16);
        region.y2 = 128;
        region.z2 = (int) (loc.getZ() + 16);

        return region;
    }

    public void newRegionFromPlayerInNether(Player player) {
        Location loc = player.getLocation();
        this.newRegionInNether(loc);
    }

    // Chunk utilities
    public Region newChunk(Region reg) {
    	Region chunk = new Region();
    	
    	chunk.x1 = reg.x1;
    	chunk.y1 = 1;
    	chunk.z1 = reg.z1;
    	
    	chunk.x2 = chunk.x1 + 15;
    	chunk.y2 = 128;
    	chunk.z2 = chunk.z1 + 15;
    	
    	return chunk;
    }
    
    public Region nextChunk(Region chunk) {
    	chunk.x1 = chunk.x1 + 16;
    	chunk.z1 = chunk.z1;
    	chunk.x2 = chunk.x1 + 15;
    	chunk.z2 = chunk.z1 + 15;
    	
    	// Remove comments for debugging scan chunks
    	String coords1 = "Chunk (x1,z1): (" + chunk.x1 + ", " + chunk.z1 + ")";
    	String coords2 = "Chunk (x2,z2): (" + chunk.x2 + ", " + chunk.z2 + ")";
    	log.log(coords1, 0);
    	log.log(coords2, 0);
    	 
    	return chunk;
    }
    
    public Region nextChunkLine(Region chunk) {
    	chunk.x1 = chunk.x1 - 256;
    	chunk.z1 = chunk.z1 + 16;
    	chunk.x2 = chunk.x1 + 15;
    	chunk.z2 = chunk.z1 + 15;
    	
    	return chunk;
    }
}
