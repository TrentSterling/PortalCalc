package me.psanker.portalcalc.persist;


import java.util.Set;

import javax.persistence.PersistenceException;

import com.avaje.ebean.*;

import me.psanker.portalcalc.PCLog;
import me.psanker.portalcalc.PCMain;
import me.psanker.portalcalc.PCMessage;

import org.bukkit.plugin.*;
import org.bukkit.Location;
import org.bukkit.World;

public class PersistenceHandler {
	
	EbeanServer server;
	PCMain plugin;
	
	public PersistenceHandler(PCMain p){
		server=p.getDatabase();
		if(server.equals(null)){
			PCLog.log("!!!", 2);
		}
		PCLog.log(""+server, 0);
		plugin=p;
		
		try {
			server.find(Portal.class).findRowCount();
		} catch (PersistenceException ex) {
			//System.out.println("Installing database for " + plugin.getDescription().getName() + " due to first time usage");
			plugin.initializeDatabase();
		}
	}
	
	public void clearDb(){
		Set<Portal> s = server.find(Portal.class).findSet();
		server.delete(s);
		PCLog.log("All portal entries deleted.", 0);
	}
	
	public Portal getPortalAt(Location l){
		return server.find(Portal.class).where().ieq("worldName", l.getWorld().getName())
		.between("x", l.getBlockX()-1, l.getBlockX()+1)
		.between("y", l.getBlockY()-2, l.getBlockY()+2)
		.between("z", l.getBlockZ()-1, l.getBlockZ()+1).findUnique();
		
	}
	
	public Set<Portal> findPortalsNear(Location l){
		return server.find(Portal.class).where().ieq("worldName", l.getWorld().getName())
		.between("x", l.getBlockX()-127, l.getBlockX()+127)
		.between("y", 1, 128)
		.between("z", l.getBlockZ()-127, l.getBlockZ()+127).findSet();
	}
	
	public int portalCount(){
		return server.find(Portal.class).findRowCount();
	}
	
	public void recordPortalAt(Location l){
		Portal p = new Portal();
		p.setName("portal_"+portalCount());
		p.setWorldName(l.getWorld().getName());
		p.setX(l.getBlockX());
		p.setY(l.getBlockY());
		p.setZ(l.getBlockZ());
		server.save(p);
	}
	
	

}
