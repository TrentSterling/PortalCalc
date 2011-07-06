package me.psanker.portalcalc.regions;

import org.bukkit.World.Environment;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class ScanController {
	
    public boolean scan(Player player) {
        Location loc = player.getLocation();
        World world = player.getWorld();
        Environment env = world.getEnvironment();
        Region region = new Region();
        
        if (env == Environment.NETHER) {
            player.sendMessage(ChatColor.RED + "Scanning region...");
            region = region.newRegionInNether(loc);
        } else {
            long delay = 250;
            player.sendMessage(ChatColor.RED + "Scanning region (256 chunks)...");
            region = region.newRegionFromLocation(loc);
            Thread scanThread = new Thread(new ChunkScanner(player, region, delay));
            scanThread.start();
        }
        
        return true;
    }
}