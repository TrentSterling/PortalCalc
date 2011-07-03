package me.psanker.portalcalc;

import me.psanker.portalcalc.regions.ScanController;

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

    private static PCMain plugin;
    private static final PCLog log = new PCLog();
    private ScanController scan = new ScanController();
    
    PCCommandManager(PCMain instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        
        if (cs instanceof ConsoleCommandSender) {
            log.log("Player entity expected", 0);
            return false;
        } else {
            if (("pcalc".equals(label)) && (args.length == 1) && ("-s".equals(args[0])) || ("pc".equals(label)) && (args.length == 1) && ("-s".equals(args[0]))) {
                this.scan(cs);
                return true;
            }
                    
            else if (("pcalc".equals(label)) 
                    && (args.length == 2) && ("-s".equals(args[0])) 
                    || ("pc".equals(label)) && (args.length == 2) 
                    && ("-s".equals(args[0]))) {
                
                String height_string = args[1];
                double height_double = Double.parseDouble(height_string);
                int height = (int) height_double;
                
                if (height > 30) {
                    cs.sendMessage(ChatColor.RED+"Invalid scan height. Please choose less than or equal to 30.");
                    return true;
                }
                
                else if (height < 1) {
                    cs.sendMessage(ChatColor.RED+"Invalid scan height. Please choose a height of at least 1 block.");
                    return true;
                }
                
                else {
                    this.scanWithHeight(height, cs);
                    return true;
                }
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
                player.sendMessage(ChatColor.AQUA+"/pcalc -n : If in Overworld, calculate Nether position");
                player.sendMessage(ChatColor.AQUA+"/pcalc -o : If in Nether, calculate Overworld position");
                player.sendMessage(ChatColor.AQUA+"/pcalc -s : Scan region for active portals");
                player.sendMessage(ChatColor.AQUA+"/pcalc [-h, help] : Display PortalCalc help");
            }        

            else {
                player.sendMessage(ChatColor.AQUA+"Incorrect format. Use /pcalc [-n, -o, -s, -h, help]");
            }
        } 

        else if (("pcalc".equals(label) && args.length == 0) || ("pc".equals(label) && args.length == 0)){
            World world;
            world = player.getWorld();
            Environment env = world.getEnvironment();
            
            if (env == Environment.NETHER) {
                Location loc = player.getLocation();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();

                x = Math.floor(x * 8);
                y = Math.floor(y);
                z = Math.floor(z * 8);

                player.sendMessage(ChatColor.AQUA+"Overworld position (X,Y,Z) is (" + x + ", " + y + ", " + z + ")");
            }
            
            else if (env == Environment.NORMAL) {
                Location loc = player.getLocation();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();

                x = Math.floor(x / 8);
                y = Math.floor(y);
                z = Math.floor(z / 8);

                player.sendMessage(ChatColor.AQUA+"Nether position (X,Y,Z) is (" + x + ", " + y + ", " + z + ")");
            }
            
            else {
                player.sendMessage(ChatColor.AQUA+"/pcalc -n : If in Overworld, calculate Nether position");
                player.sendMessage(ChatColor.AQUA+"/pcalc -o : If in Nether, calculate Overworld position");
                player.sendMessage(ChatColor.AQUA+"/pcalc -s : Scan region for active portals");
                player.sendMessage(ChatColor.AQUA+"/pcalc [-h, help] : Display PortalCalc help");
            }
        }
    }

    private void scan(CommandSender cs) {
        Player player = (Player) cs;
        World world = player.getWorld();
        Location loc = player.getLocation();
        String world_name = world.getName();
        boolean scanned = scan.scan(player);
        if (scanned == true) {
            String name = player.getDisplayName();
            log.log(name + " scanned region of [" + world_name + "] (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ") for active portal", 0);
        } else {
            log.log("scan() called for no reason", 2);
        }
    }
    
    private void scanWithHeight(int height, CommandSender cs) {
        Player player = (Player) cs;
        World world = player.getWorld();
        Location loc = player.getLocation();
        String world_name = world.getName();
        boolean scanned = scan.scanWithHeight(height, player);
        if (scanned == true) {
            String name = player.getDisplayName();
            log.log(name + " scanned region of [" + world_name + "] (" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ") for active portal", 0);
        } else {
            log.log("scan() called for no reason", 2);
        }
    }
}