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

import static java.lang.Math.max;
import static java.lang.Math.min;



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
			plugin.initializeDb();
		}
	}
	
	public void clearDb(){
		Set<Portal> s = server.find(Portal.class).findSet();
		server.delete(s);
		PCLog.log("All portal entries deleted.", 0);
	}
	
	public boolean isPortalAt(Location l){
		return server.find(Portal.class).where().ieq("world", l.getWorld().getName())
		.between("x", new Integer(l.getBlockX()-1), new Integer(l.getBlockX()+1))
		.between("y", new Integer(l.getBlockY()-2), new Integer(l.getBlockY()+2))
		.between("z",new Integer( l.getBlockZ()-1), new Integer(l.getBlockZ()+1)).findRowCount()!=0;
	}
	
	public void removePortal(Portal p){
		server.delete(p);
	}
	
	public Portal getPortalAt(Location l){
		return server.find(Portal.class).where().ieq("world", l.getWorld().getName())
		.between("x", new Integer(l.getBlockX()-1), new Integer(l.getBlockX()+1))
		.between("y", new Integer(l.getBlockY()-2), new Integer(l.getBlockY()+2))
		.between("z",new Integer( l.getBlockZ()-1), new Integer(l.getBlockZ()+1)).findUnique();
		
	}
	
	public Set<Portal> findPortalsNear(Location l){
		return server.find(Portal.class).where().ieq("world", l.getWorld().getName())
		.between("x", l.getBlockX()-127, l.getBlockX()+127)
		.between("y", 1, 128)
		.between("z", l.getBlockZ()-127, l.getBlockZ()+127).findSet();
	}
	
	public Portal findPortalVeryNear(Location l){
		return server.find(Portal.class).where().ieq("world", l.getWorld().getName())
		.between("x", new Integer(l.getBlockX()-12), new Integer(l.getBlockX()+12))
		.between("y", new Integer(max(1, l.getBlockY()-6)), new Integer(min(128, l.getBlockY()+6)))
		.between("z", new Integer(l.getBlockZ()-12), new Integer(l.getBlockZ()+127)).findUnique();
	}
	
	public int portalCount(){
		return server.find(Portal.class).findRowCount();
	}
	
	public Portal recordPortalAt(Location l){
		Portal p = new Portal();
		p.setName("portal_"+portalCount());
		p.setWorld(l.getWorld().getName());
		p.setX(l.getBlockX());
		p.setY(l.getBlockY());
		p.setZ(l.getBlockZ());
		server.save(p);
		return p;
	}
	
	

}
