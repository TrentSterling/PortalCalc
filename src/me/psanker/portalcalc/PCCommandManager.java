package me.psanker.portalcalc;

import me.psanker.portalcalc.regions.ChunkScanner;
import me.psanker.portalcalc.persist.Portal;
import me.psanker.portalcalc.regions.RegionProvider;

import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.ChatColor;

import java.util.Set;

public class PCCommandManager implements CommandExecutor {

    @SuppressWarnings("unused")
	private static PCMain plugin;
    
    PCCommandManager(PCMain instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
    	Player player = (Player) cs;
        if (cs instanceof ConsoleCommandSender) {
            PCLog.log("Player entity expected", 0);
            return false;
        }
        
        if(args.length==0){
        	version(player);
        	help(player);
        }
        else if(args.length==1){
            if(args[0].equals("-n")){
            	position(true, player);
            }
            else if(args[0].equals("-o")){
            	position(false, player);
            }
            else if(args[0].equals("select")){
            	select(player);
            }
            else if(args[0].equals("help")||
            		args[0].equals("h")){
            	help(player);
            }
            else if(args[0].equals("-s")||
            		args[0].equals("scan")){
            	scan(player);
            }
            else if(args[0].equals("flush")){
            	flush(player);
            }
            else if(args[0].equals("-v")){
            	version(player);
            }
            else{
            	syntax(player);
            	help(player);
            }
            	
        }
        
        else{
        	syntax(player);
        	help(player);
        }
        
        
        
        return true;
    }
    
    
    protected void syntax(Player player){
    	PCMessage.message(player, "Usage:", 2);
    }
    
    protected void version(Player player) {
		PCMessage.message(player, "PortalCalc v. "+plugin.VERSION, 2);
		
	}

	protected void scan(Player player){
    	Set<Portal> s = plugin.handler.findPortalsNear(player.getLocation());
    	if(s.isEmpty())
    		plugin.searchForPortal(3, player, true);
    	else
    		for(Portal p : s){
    			Location loc1 = player.getLocation();
    			Location loc2 = new Location(player.getWorld(), p.getX(), p.getY(), p.getZ());
    			int dis = VectorHelper.calculateDistance(loc1, loc2);
                String dir = VectorHelper.getDirection(loc1, loc2);
                PCMessage.message(player, "Active portal "+dis+" blocks "+dir, 1);
    		}
    }
    
    protected void position(boolean nether_pos, Player player){
    	boolean in_nether = player.getWorld().getEnvironment()==World.Environment.NETHER;
    	int x,y,z;
    	Location loc = player.getLocation();
    	x=loc.getBlockX();
    	y=loc.getBlockY();
    	z=loc.getBlockZ();
    	
    	if(nether_pos ^ in_nether){
    		if(nether_pos){
    			x/=8;
    			z/=8;
    		}
    		else{
    			x*=8;
    			z*=8;
    		}
    	}
    	
    	player.sendMessage(ChatColor.AQUA+(nether_pos?"Nether":"Overworld")+"position (X,Y,Z) is (" + x + ", " + y + ", " + z + ")");
    }
    
    protected void help(Player player){
    	PCMessage.sendHelp(player);
    }
    
    protected void flush(Player player){
    	plugin.handler.clearDb();
    	PCMessage.message(player, "All portal entries removed", 2);
    }
    
    protected void select(Player player){
    	Portal p = plugin.handler.findPortalVeryNear(player.getLocation());
		if(p == null){
			PCMessage.message(player, "No portal known nearby, searching now", 0);
			plugin.searchForPortal(1, player, false);
			
			
			return;
		}
		plugin.setSelectedPortal(p);
		PCMessage.message(player, "Portal \""+p.getName()+"\" selected.", 0);
    }
    
    
}