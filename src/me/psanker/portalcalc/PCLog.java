package me.psanker.portalcalc;

import java.util.logging.Logger;
import java.util.logging.Level;

public class PCLog {
    
    private static final Logger logger = Logger.getLogger("Minecraft");
    
    public static void log(String string, int level) {
        if (level == 0)
            logger.log(Level.INFO, "[PortalCalc] " + string);
        if (level == 1)
            logger.log(Level.WARNING, "[PortalCalc] " + string);
        if (level == 2)
            logger.log(Level.SEVERE, "[PortalCalc] " + string);
    }
}
