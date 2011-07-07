package me.psanker.portalcalc;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class PCMessage {
    
    
    public static void message(Player player, String message, int importance) {
        if ((importance > 2) || (importance < 0)) {
            player.sendMessage(ChatColor.BLUE+"[PORTALCALC] "+ChatColor.AQUA+"Something broke... Logging accident to console.");
            PCLog.log("me.psanker.portalcalc.PCMessage.message passed incorrect importance. Report this.", 2);
        }
        
        if (importance == 0)
            player.sendMessage(ChatColor.BLUE+"[PORTALCALC] "+ChatColor.WHITE+message);
        if (importance == 1)
            player.sendMessage(ChatColor.BLUE+"[PORTALCALC] "+ChatColor.AQUA+message);
        if (importance == 2)
            player.sendMessage(ChatColor.BLUE+"[PORTALCALC] "+ChatColor.RED+message);
    }
    
    public static void sendHelp(Player player) {
        player.sendMessage(ChatColor.AQUA+"/pcalc -n : If in Overworld, calculate Nether position");
        player.sendMessage(ChatColor.AQUA+"/pcalc -o : If in Nether, calculate Overworld position");
        player.sendMessage(ChatColor.AQUA+"/pcalc [-s, scan] : Scan region for active portals");
        player.sendMessage(ChatColor.AQUA+"/pcalc [-h, help] : Display PortalCalc help");
        player.sendMessage(ChatColor.AQUA+"/pcalc -v: Get PortalCalc version number.");
    }
}
