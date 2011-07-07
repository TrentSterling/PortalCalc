package me.psanker.portalcalc.regions;

//import me.psanker.portalcalc.PCLog;
import me.psanker.portalcalc.PCMessage;

import org.bukkit.Location;
import me.psanker.portalcalc.PCMain;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.World;

public class ChunkScanner implements Runnable {
    Player player;
   	RegionProvider provider;
    private static final long SLEEP_TIME= 250;
    protected PCMain plugin;
    protected boolean adviseLocation = true; // FORCE TRUE (this will be used in future update
    
   // private volatile Thread scanThread;
    
    //private static PCLog log = new PCLog();
   
    
    public void run() {
    	
    	World world = player.getWorld();
    	
    	for(Region region : provider){
    		for(int x=region.x1;x<region.x2;x++){
    			for(int y=region.y1;y<region.y2;y+=3){
    				for(int z=region.z1+(x%2);z<region.z2;z+=2){
    					
    					Block b = world.getBlockAt(x, y, z);
    					int id = b.getTypeId();
    					if (id == 90) {
    						plugin.handleFoundPortal(new Location(player.getWorld(), x,y,z), adviseLocation, player);
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

    public ChunkScanner(Player play, RegionProvider p, PCMain plugin, boolean adviseLocation) {
        player = play;
        provider = p;
        this.plugin = plugin;
        this.adviseLocation = adviseLocation;
        
    }
}