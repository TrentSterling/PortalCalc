package me.psanker.portalcalc.regions;

import me.psanker.portalcalc.PCLog;

import org.bukkit.World.Environment;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;

class Coords {
    int x1;
    int y1;
    int z1;
    int x2;
    int y2;
    int z2;
    
    private static PCLog log = new PCLog();
    
    Coords(Location loc, Environment env) {
        if (env == Environment.NORMAL) {
            double tmpX = loc.getX();
            double tmpY = loc.getY();
            double tmpZ = loc.getZ();
            
            x1 = (int) (Math.floor(tmpX) - 64);
            y1 = (int) (Math.floor(tmpY) - 20);
            z1 = (int) (Math.floor(tmpZ) - 64);
            
            x2 = (int) (Math.floor(tmpX) + 64);
            y2 = (int) (Math.floor(tmpY) + 20);
            z2 = (int) (Math.floor(tmpZ) + 64);
        }
        
        else if (env == Environment.NETHER) {
            double tmpX = loc.getX();
            double tmpY = loc.getY();
            double tmpZ = loc.getZ();
            
            x1 = (int) (Math.floor(tmpX) - 8);
            y1 = (int) (Math.floor(tmpY) - 20);
            z1 = (int) (Math.floor(tmpZ) - 8);
            
            x2 = (int) (Math.floor(tmpX) + 8);
            y2 = (int) (Math.floor(tmpY) + 20);
            z2 = (int) (Math.floor(tmpZ) + 8);
        }
        
        else {
            log.log("Coords() called for no apparent reason", 2);
        }
    }
};

class CoordsWithHeight {
    int x1;
    int y1;
    int z1;
    int x2;
    int y2;
    int z2;
    
    private static PCLog log = new PCLog();
    
    CoordsWithHeight(int height, Location loc, Environment env) {
        if (env == Environment.NORMAL) {
            double tmpX = loc.getX();
            double tmpY = loc.getY();
            double tmpZ = loc.getZ();
            
            x1 = (int) (Math.floor(tmpX) - 64);
            y1 = (int) (Math.floor(tmpY) - height);
            z1 = (int) (Math.floor(tmpZ) - 64);
            
            x2 = (int) (Math.floor(tmpX) + 64);
            y2 = (int) (Math.floor(tmpY) + height);
            z2 = (int) (Math.floor(tmpZ) + 64);
        }
        
        else if (env == Environment.NETHER) {
            double tmpX = loc.getX();
            double tmpY = loc.getY();
            double tmpZ = loc.getZ();
            
            x1 = (int) (Math.floor(tmpX) - 8);
            y1 = (int) (Math.floor(tmpY) - height);
            z1 = (int) (Math.floor(tmpZ) - 8);
            
            x2 = (int) (Math.floor(tmpX) + 8);
            y2 = (int) (Math.floor(tmpY) + height);
            z2 = (int) (Math.floor(tmpZ) + 8);
        }
        
        else {
            log.log("CoordsWithHeight() called for no apparent reason", 2);
        }
    }
};
        
public class ScanController {
    // create globals
    
    private static PCLog log = new PCLog();
    
    public boolean scan(Player player) {
        Location loc = player.getLocation();
        World world = player.getWorld();
        Environment env = world.getEnvironment();
        VectorController vector = new VectorController();
        
        Coords coords = new Coords(loc, env);
        player.sendMessage(ChatColor.RED + "Scanning for active portals...");
        
        // Scan for portal
        int count = 0;
        
        for (int x = coords.x1; x <= coords.x2; x++) {
            for (int y = coords.y1; y <= coords.y2; y++) {
                for (int z = coords.z1; z <= coords.z2; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    int id = block.getTypeId();

                    if ((id == 90) && (count > 0)) {
                        return true;
                    }
                    
                    else if (id == 90) {
                        Location pLoc = block.getLocation();
                        double distance = vector.calculateDistance(loc, pLoc);
                        String direction = vector.getDirection(loc, pLoc);
                        player.sendMessage(ChatColor.GOLD + "Found active portal in range. (" + x + ", " + y + ", " + z + ")");
                        player.sendMessage(ChatColor.AQUA + "Portal location: " + distance + " blocks away");
                        count++;
                        return true;
                    }
                }
            }
        }

        if (count == 0) {
            player.sendMessage(ChatColor.GREEN + "No active portals found in range!");
            return true;
        } else {
            log.log("Scan returned null (no portal found, but count > 0)", 2);
        }
        
        return false;
    }
    
    public boolean scanWithHeight(int height, Player player) {
        Location loc = player.getLocation();
        World world = player.getWorld();
        Environment env = world.getEnvironment();
        VectorController vector = new VectorController();
        
        CoordsWithHeight coords = new CoordsWithHeight(height, loc, env);
        player.sendMessage(ChatColor.RED + "Scanning for active portals...");
        
        // Scan for portal
        int count = 0;
        
        for (int x = coords.x1; x <= coords.x2; x++) {
            for (int y = coords.y1; y <= coords.y2; y++) {
                for (int z = coords.z1; z <= coords.z2; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    int id = block.getTypeId();

                    if ((id == 90) && (count > 0)) {
                        return true;
                    }
                    
                    else if (id == 90) {
                        Location pLoc = block.getLocation();
                        double distance = vector.calculateDistance(loc, pLoc);
                        String direction = vector.getDirection(loc, pLoc);
                        player.sendMessage(ChatColor.GOLD + "Found active portal in range. (" + x + ", " + y + ", " + z + ")");
                        player.sendMessage(ChatColor.AQUA + "Portal location: " + distance + " blocks away");
                        count++;
                        return true;
                    }
                }
            }
        }

        if (count == 0) {
            player.sendMessage(ChatColor.GREEN + "No active portals found in range!");
            return true;
        } else {
            log.log("Scan returned null (no portal found, but count > 0)", 2);
        }
        
        return false;
    }
}
