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
        } else {
            if (("pcalc".equals(label)) && (args.length == 1) && ("-s".equals(args[0])) 
                    || ("pc".equals(label)) && (args.length == 1) && ("-s".equals(args[0]))
                    || ("pcalc".equals(label)) && (args.length == 1) && ("scan".equals(args[0]))
                    || ("pc".equals(label)) && (args.length == 1) && ("scan".equals(args[0]))) {
            	
            	Set<Portal> s = plugin.handler.findPortalsNear(player.getLocation());
            	if(s.isEmpty())
            		plugin.searchForPortal(3, (Player) cs, true);
            	else
            		for(Portal p : s){
            			Location loc1 = player.getLocation();
            			Location loc2 = new Location(player.getWorld(), p.getX(), p.getY(), p.getZ());
            			int dis = VectorHelper.calculateDistance(loc1, loc2);
                        String dir = VectorHelper.getDirection(loc1, loc2);
                        PCMessage.message(player, "Active portal "+dis+" blocks "+dir, 1);
            		}
                return true;
            }
            
            else if ((("pcalc".equals(label)) && (args.length == 0)) || (("pc".equals(label)) && args.length == 0)) {
            	PCMessage.sendHelp(player);
            	return true;
            }
            
            else {
                this.calculate(cs, cmnd, label, args);
                return true;
            }
        }
    }
    
    // Created for future features (not completely extraneous)
    private void calculate(CommandSender cs, Command cmnd, String label, String[] args) {
        Player player = (Player) cs;
        
        if (("pcalc".equals(label) && args.length == 1) || ("pc".equals(label) && args.length == 1)) {
            World world;
            world = player.getWorld();
            Environment env = world.getEnvironment();
            
            if ("-n".equals(args[0]) && env == Environment.NORMAL) {
                Location loc = player.getLocation();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();

                x = Math.floor(x / 8);
                y = Math.floor(y);
                z = Math.floor(z / 8);

                player.sendMessage(ChatColor.AQUA+"Nether position (X,Y,Z) is (" + x + ", " + y + ", " + z + ")");
                return;
            }

            else if ("-o".equals(args[0]) && env == Environment.NETHER) {
                Location loc = player.getLocation();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();

                x = Math.floor(x * 8);
                y = Math.floor(y);
                z = Math.floor(z * 8);

                player.sendMessage(ChatColor.AQUA+"Overworld position (X,Y,Z) is (" + x + ", " + y + ", " + z + ")");
                return;
            }

            else if (("-n".equals(args[0]) && env == Environment.NETHER) || ("-o".equals(args[0]) && env == Environment.NORMAL)) {
                Location loc = player.getLocation();
                double x = Math.floor(loc.getX());
                double y = Math.floor(loc.getY());
                double z = Math.floor(loc.getZ());

                player.sendMessage(ChatColor.AQUA+"Current position (X,Y,Z) is (" + x + ", " + y + ", " + z + ")");
                return;
            }
            
            else if(args[0].equals("select")){
        		Portal p = plugin.handler.findPortalVeryNear(player.getLocation());
        		if(p == null){
        			PCMessage.message(player, "No portal known nearby, searching now", 0);
        			plugin.searchForPortal(1, player, false);
        			
        			
        			return;
        		}
        		plugin.setSelectedPortal(p);
        		PCMessage.message(player, "Portal \""+p.getName()+"\" selected.", 0);
        		return;
        	}

            else if (("help".equals(args[0])) || ("-h".equals(args[0]))) {
                PCMessage.sendHelp(player);
                return;
            }   
        }
        
        else if((label.equals("pcalc")||label.equals("pc"))&&args.length==2){
        	if(args[0].equals("db")){
        		if(args[1].equals("flush")){
        			plugin.handler.clearDb();
        			PCMessage.message(player, "All portal entries removed.", 2);
        			return;
        		}
        	}
        	
        }
        
        PCMessage.message(player, "Incorrect format.", 1);
        PCMessage.sendHelp(player);
    }
    
}