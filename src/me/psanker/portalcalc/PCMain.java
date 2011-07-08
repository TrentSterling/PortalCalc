package me.psanker.portalcalc;

import java.util.ArrayList;
import java.util.List;

import me.psanker.portalcalc.persist.PersistenceHandler;
import me.psanker.portalcalc.persist.Portal;
import me.psanker.portalcalc.regions.ChunkScanner;
import me.psanker.portalcalc.regions.RegionProvider;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class PCMain extends JavaPlugin {
	
	public static PermissionHandler permissionHandler;
	
	public static final String VERSION = "1.3.0a";
	
	
	protected PersistenceHandler handler;
	
	protected PCBlockListener blocklistener = new PCBlockListener(this);
	protected PCWorldListener worldlistener = new PCWorldListener(this);
    
    @Override
    public void onEnable() {
        PCLog.log("PortalCalc started.", 0);
        getCommand("pcalc").setExecutor(new PCCommandManager(this));
        getCommand("pc").setExecutor(new PCCommandManager(this));
        
        // Set up permissions
        setupPermissions();
        
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
    
    private void setupPermissions() {
        if (permissionHandler != null) {
            return;
        }
        
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
        
        if (permissionsPlugin == null) {
            PCLog.log("Permission system not detected, defaulting to OP",1);
            return;
        }
        
        permissionHandler = ((Permissions) permissionsPlugin).getHandler();
        PCLog.log("Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName(),0);
    }
    
    public void initializeDatabase(){
 	   installDDL();
    }
     
     @Override
 	public List<Class<?>> getDatabaseClasses(){
 		ArrayList<Class<?>> l = new ArrayList<Class<?>>();
 		l.add(Portal.class);
 		return l;
 	}
    
     protected Portal selectedPortal;
    
  	void setSelectedPortal(Portal selectedPortal) {
		this.selectedPortal = selectedPortal;
	}

	public Portal getSelectedPortal() {
		return selectedPortal;
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
		Portal portal;
		if(!handler.isPortalAt(l)){
			if(handler.isPortal(l))
				portal= handler.recordPortalAt(l, false);
		
			else{
				return;
			}
		}
		else
			 portal = handler.getPortalAt(l);
		
		setSelectedPortal(portal);
		if(!adviseLocation){
			PCMessage.message(p, "Portal \""+portal.getName()+"\" selected", 0);
		}
		
		
	}
	
	public void searchForPortal(int radius, Player player, boolean adviseLocation){
		RegionProvider p = new RegionProvider(player, radius);
        ChunkScanner s = new ChunkScanner(player, p, this, adviseLocation);
        Thread scanThread = new Thread(s);
        scanThread.start();
	}
    
}
