package me.psanker.portalcalc.regions;

import java.awt.geom.Line2D;
import me.psanker.portalcalc.PCLog;

import org.bukkit.Location;


// -X is east
// +X is west
// -Z is north
// +Z is south

public class VectorController {
    
    private static PCLog log = new PCLog();
    
    public double calculateDistance(Location loc1, Location loc2) {
        double distance;
        double x1 = loc1.getX();
        double x2 = loc2.getX();
        double z1 = loc1.getZ();
        double z2 = loc2.getZ();
        
        distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((z2 - z1), 2));
        
        return Math.floor(distance);
    }
    
    public java.lang.String getDirection(Location loc1, Location loc2) {
        String direction = null;
        double x1 = loc1.getX();
        double x2 = loc2.getX();
        double y1 = loc1.getY();
        double z1 = loc1.getZ();
        double z2 = loc2.getZ();
        
        // Assume player is standing on (x, 0) coord of hypotenuse
        // Therefore, set distance (hypotenuse) and adjacent side to vectors used
        
        Line2D line1 = new Line2D.Double(x1, z1, x2, z1);
        Line2D line2 = new Line2D.Double(x1, z1, x2, z2);
        
        double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
                                   line1.getX1() - line1.getX2());
        double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
                                   line2.getX1() - line2.getX2());
        double angled = angle1 - angle2;
        int angle = (int) Math.floor(Math.toDegrees(angled));
        
        if (angle < 0) {
            angle += 360;
        }
        
        // ----------
        
        if ((angle == 0) || ((angle > 0) && (angle < 30))) {
            direction = "north";
        }
                
        else if ((angle > 30) && (angle < 60)) {
            direction = "northw";
        }        
        
        else if ((angle == 90) || ((angle > 60) && (angle < 120))) {
            direction = "west";
        }
        
        else if ((angle == 180) || ((angle > 150) && (angle < 210))) {
            direction = "south";
        }
        
        else if ((angle == 270) || ((angle > 240) && (angle < 300))) {
            direction = "east";
        }
        
        return direction; // INACTIVE... will get back to this...
    }
}
