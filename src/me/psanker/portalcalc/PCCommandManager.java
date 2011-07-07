package me.psanker.portalcalc;

import java.util.Set;

import me.psanker.portalcalc.persist.Portal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.ChatColor;

public class PCCommandManager implements CommandExecutor {

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
            else if(args[0].equals("help")||
            		args[0].equals("h")){
            	help(player);
            }
            else if(args[0].equals("-s")||
            		args[0].equals("scan")){
            	scan(player);
            }
            else if(args[0].equals("-v")){
            	version(player);
            }
            else if(args[0].equals("-l")){
            	list(player);
            }
            else{
            	help(player);
            }
            	
        }
        
        else{
        	help(player);
        }
        
        return true;
    }
    
    @SuppressWarnings("static-access")
	protected void version(Player player) {
		PCMessage.message(player, "PortalCalc v. "+plugin.VERSION, 2);
		
	}

	protected void scan(Player player){
		plugin.searchForPortal(3, player, true);
    }
    
    protected void position(boolean nether_request, Player player){
    	boolean in_nether = player.getWorld().getEnvironment()==World.Environment.NETHER;
    	int x,y,z;
    	Location loc = player.getLocation();
    	x=loc.getBlockX();
    	y=loc.getBlockY();
    	z=loc.getBlockZ();
    	
    	if(nether_request ^ in_nether){
    		if(nether_request){
    			x/=8;
    			z/=8;
    		}
    		else{
    			x*=8;
    			z*=8;
    		}
    	}
    	
    	player.sendMessage(ChatColor.AQUA+(nether_request?"Nether":"Overworld")+"position (X,Y,Z) is (" + x + ", " + y + ", " + z + ")");
    }
    
    protected void help(Player player){
    	PCMessage.sendHelp(player);
    }
    
    protected void list(Player player){
    	Set<Portal> s = plugin.handler.allPortals();
    	for(Portal p : s){
    		PCMessage.message(player, "portal name "+p.getName()+" in "+p.getWorld(), 1);
    	}
    }
    
}