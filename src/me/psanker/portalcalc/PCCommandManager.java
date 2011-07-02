package me.psanker.portalcalc;

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
    
    PCCommandManager(PCMain instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        
        if (cs instanceof ConsoleCommandSender) {
            log.log("Player entity expected", 0);
            return false;
        } else {
            this.calculate(cs, cmnd, label, args);
            return true;
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
                player.sendMessage(ChatColor.AQUA+"/pcalc [-h, help] : Display PortalCalc help");
            }        

            else {
                player.sendMessage(ChatColor.AQUA+"Incorrect format. Use /pcalc [-n, -o, -h, help]");
            }
        } 

        else if (("pcalc".equals(label) && args.length == 0) || ("pc".equals(label) && args.length == 0)){
            player.sendMessage(ChatColor.AQUA+"/pcalc -n : If in Overworld, calculate Nether position");
            player.sendMessage(ChatColor.AQUA+"/pcalc -o : If in Nether, calculate Overworld position");
            player.sendMessage(ChatColor.AQUA+"/pcalc [-h, help] : Display PortalCalc help");
        }
    }   
}