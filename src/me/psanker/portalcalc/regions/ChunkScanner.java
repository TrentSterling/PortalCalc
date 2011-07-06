package me.psanker.portalcalc.regions;

//import me.psanker.portalcalc.PCLog;
import me.psanker.portalcalc.PCMessage;
import org.bukkit.Location;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.World;

public class ChunkScanner implements Runnable {
    Player player;
   	RegionProvider provider;
    private static final long SLEEP_TIME= 250;
    
   // private volatile Thread scanThread;
    
    //private static PCLog log = new PCLog();
    private static VectorHelper vh = new VectorHelper();
    
    public void run() {
    	
    	World world = player.getWorld();
    	
    	for(Region region : provider){
    		for(int x=region.x1;x<region.x2;x++){
    			for(int y=region.y1;y<region.y2;y+=3){
    				for(int z=region.z1+(x%2);z<region.z2;z+=2){
    					
    					Block b = world.getBlockAt(x, y, z);
    					int id = b.getTypeId();
    					if (id == 90) {
                            PCMessage.message(player, "Active portal found", 2);
                            Location loc1 = player.getLocation();
                            Location loc2 = b.getLocation();
                            int dis = vh.calculateDistance(loc1, loc2);
                            String dir = vh.getDirection(loc1, loc2);
                            PCMessage.message(player, "Portal is "+dis+" blocks "+dir, 1);
                            return;
                        }
    				}
    			}
    		}
    		try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				break;
			}
    	}
    	
    	PCMessage.message(player, "No active portal found!", 2);
        
    }

    public ChunkScanner(Player play, RegionProvider p) {
        player = play;
        provider = p;
    }
}