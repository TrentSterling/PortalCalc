package me.psanker.portalcalc;

import java.util.ArrayList;
import java.util.List;

import me.psanker.portalcalc.persist.PersistenceHandler;
import me.psanker.portalcalc.persist.Portal;

import org.bukkit.plugin.java.JavaPlugin;

public class PCMain extends JavaPlugin {
	
	protected PersistenceHandler handler;
    
    @Override
    public void onEnable() {
        PCLog.log("PortalCalc started.", 0);
        getCommand("pcalc").setExecutor(new PCCommandManager(this));
        getCommand("pc").setExecutor(new PCCommandManager(this));
        
        handler=new PersistenceHandler(this);
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
    
    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
    }
            
}