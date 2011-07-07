package me.psanker.portalcalc;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import org.bukkit.event.*;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Location;
import me.psanker.portalcalc.regions.*;

import me.psanker.portalcalc.persist.*;

public class PCMain extends JavaPlugin {
	
	public static final String VERSION = "1.2.0";
	
	protected PersistenceHandler handler;
	
	protected PCBlockListener blocklistener = new PCBlockListener(this);
	protected PCWorldListener worldlistener = new PCWorldListener(this);
    
    @Override
    public void onEnable() {
        PCLog.log("PortalCalc started.", 0);
        getCommand("pcalc").setExecutor(new PCCommandManager(this));
        getCommand("pc").setExecutor(new PCCommandManager(this));
        
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Event.Type.SIGN_CHANGE, blocklistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PORTAL_CREATE, worldlistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blocklistener, Priority.Normal, this);
        
        handler=new PersistenceHandler(this);
    }
    
    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
    }
    
    public void initializeDb(){
    	this.installDDL();
    }
    
    public void searchForPortal(int radius, Player player, boolean adviseLocation){
		RegionProvider p = new RegionProvider(player, radius);
        ChunkScanner s = new ChunkScanner(player, p, this, adviseLocation);
        Thread scanThread = new Thread(s);
        scanThread.start();
	}
    
    public void handleFoundPortal(Location l, boolean adviseLocation, Player p){
		PCMessage.message(p, "Portal found!", 2);
		if(adviseLocation){
			Location loc1 = p.getLocation();
			Location loc2 = l;
			int dis = VectorHelper.calculateDistance(loc1, loc2);
            String dir = VectorHelper.getDirection(loc1, loc2);
            PCMessage.message(p, "Portal is "+dis+" blocks "+dir, 1);
		}
		
		if(!handler.isPortalAt(l)){
			handler.recordPortalAt(l);
		}
		
		// select the portal?
	}
    
}
