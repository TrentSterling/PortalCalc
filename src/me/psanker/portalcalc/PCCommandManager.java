package me.psanker.portalcalc;

import me.psanker.portalcalc.regions.ChunkScanner;
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

public class PCCommandManager implements CommandExecutor {

    @SuppressWarnings("unused")
	private static PCMain plugin;
    
    PCCommandManager(PCMain instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        
        if (cs instanceof ConsoleCommandSender) {
            PCLog.log("Player entity expected", 0);
            return false;
        } else {
            if (("pcalc".equals(label)) && (args.length == 1) && ("-s".equals(args[0])) 
                    || ("pc".equals(label)) && (args.length == 1) && ("-s".equals(args[0]))
                    || ("pcalc".equals(label)) && (args.length == 1) && ("scan".equals(args[0]))
                    || ("pc".equals(label)) && (args.length == 1) && ("scan".equals(args[0]))) {
                this.scan(cs);
                return true;
            }
            
            else if ((("pcalc".equals(label)) && (args.length == 0)) || (("pc".equals(label)) && args.length == 0)) {
            	Player player = (Player) cs;
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
            }

            else if (("-n".equals(args[0]) && env == Environment.NETHER) || ("-o".equals(args[0]) && env == Environment.NORMAL)) {
                Location loc = player.getLocation();
                double x = Math.floor(loc.getX());
                double y = Math.floor(loc.getY());
                double z = Math.floor(loc.getZ());

                player.sendMessage(ChatColor.AQUA+"Current position (X,Y,Z) is (" + x + ", " + y + ", " + z + ")");
            }

            else if (("help".equals(args[0])) || ("-h".equals(args[0]))) {
                PCMessage.sendHelp(player);
            }        

            else {
                PCMessage.message(player, "Incorrect format.", 1);
                PCMessage.sendHelp(player);
            }
        }
    }
    
    private void scan(CommandSender cs) {
        Player player = (Player) cs;
    //    boolean scanned = scan.scan(player); <-- this method always returns true, so what's the point of testing the return value?!
        RegionProvider p = new RegionProvider(player);
        ChunkScanner s = new ChunkScanner(player, p);
        Thread scanThread = new Thread(s);
        scanThread.start();
        
       /* if (scanned == true) {
            // Cool.
        } else {
            log.log("scan() called for no reason", 2);
        }*/
    }
}