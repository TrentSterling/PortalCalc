package me.psanker.portalcalc.regions;

import me.psanker.portalcalc.PCLog;
import me.psanker.portalcalc.PCMessage;
import org.bukkit.Location;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.World;

public class ChunkScanner implements Runnable {
    Player player;
    Region region;
    long sleepTime;
    
    private volatile Thread scanThread;
    
    private static PCLog log = new PCLog();
    private static PCMessage message = new PCMessage();
    private static VectorHelper vh = new VectorHelper();
    
    @Override
    public void run() {
    	Thread thisThread = Thread.currentThread();
        thisThread = scanThread; // Force thread change.
        boolean wtbs = false; 
        while (scanThread == thisThread) {  
        	
        	if (wtbs == true) {
        		this.stop();
        		break;
        	}
        	
        	try {
        		
        		Region chunk = new Region();
                chunk = region.newChunk(region);
                
                World world = player.getWorld();

                int logger = 0;
                int lognext = 16;
                int chunknum = 1;
                int counter = 0;

                scan:
                
                for (int i = 0; i <= 255; i++) {
                    for (int x = chunk.x1; x <= chunk.x2; x++)
                        for (int y = chunk.y1; y <= chunk.y2; y++)
                            for (int z = chunk.z1; z <= chunk.z2; z++) {
                                Block block = world.getBlockAt(x, y, z);
                                int id = block.getTypeId();
                                
                                if (id == 90) {
                                    message.message(player, "Active portal found", 2);
                                    Location loc1 = player.getLocation();
                                    Location loc2 = block.getLocation();
                                    int dis = vh.calculateDistance(loc1, loc2);
                                    String dir = vh.getDirection(loc1, loc2);
                                    message.message(player, "Portal is "+dis+" blocks "+dir, 1);
                                    wtbs = true;
                                    break scan;
                                }
                                
                                counter++;
                            }
                    if (logger == lognext) {
                        message.message(player, "Scanning chunk "+logger, 0);
                        lognext *= 2;
                        logger++;
                    } 
                    
                    else if (chunknum == 16) {
                    	chunk = region.nextChunk(chunk);
                    	chunknum = 1;
                    }
                    
                    else {
                        logger++;
                        chunknum++;
                    }

                    // log.log("Scanned "+counter+" blocks", 0);
                    Thread.sleep(sleepTime);
                }
                
                message.message(player, "No active portal found!", 0);
                break;
            
        	} catch (InterruptedException e) {
        		log.log(player.getDisplayName()+"'s portal scan is complete.", 0);
	        }
        }
        
    }
    
    public void stop() {
    	Thread thisThread = Thread.currentThread();	
    	try {	
    		thisThread.interrupt();
    		scanThread.interrupt();
    		scanThread = null;
    	} catch (NullPointerException e) {
    		// All good.
    	}
    }
    
    public ChunkScanner(Player play, Region reg, long delay) {
        player = play;
        region = reg;
        sleepTime = delay;
    }
}