package me.psanker.portalcalc.regions;

import org.bukkit.Location;


// -X is north
// +X is south
// -Z is east
// +Z is west

public class VectorHelper {
    
    public int calculateDistance(Location loc1, Location loc2) {
        double distance;
        double x1 = loc1.getX();
        double x2 = loc2.getX();
        double z1 = loc1.getZ();
        double z2 = loc2.getZ();
        
        distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((z2 - z1), 2));
        
        return (int) Math.floor(distance);
    }
    
    public java.lang.String getDirection(Location loc1, Location loc2) {
        String direction = null;
        double x1 = loc1.getX();
        double x2 = loc2.getX();
        double z1 = loc1.getZ();
        double z2 = loc2.getZ();
        
        double hyp = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((z2 - z1), 2));
        double opp = Math.sqrt(Math.pow((x2 - x2), 2) + Math.pow((z2 - z1), 2));
        
        double sinangle = opp / hyp;
        double angle = Math.asin(sinangle);
        angle = Math.toDegrees(angle);
        
        if (angle < 0) {
            angle += 360.0D;
        }
        
        // Construct quadrants
        
        if ((x1 > x2) && (z1 > z2)) {
            double comp = 90 - angle;
            angle = comp;
        }
        
        if ((x1 > x2) && (z1 < z2))
            angle += 90;
        
        if ((x1 < x2) && (z1 < z2)) {
            double comp = 270 - angle;
            angle = comp;
        }
        
        if ((x1 < x2) && (z1 > z2))
            angle += 270;
        
        if ((0.0D <= angle) && (angle < 22.5D))
            direction = "east";
        if ((22.5D <= angle) && (angle < 67.5D))
            direction = "northeast";
        if ((67.5D <= angle) && (angle < 112.5D))
            direction = "north";
        if ((112.5D <= angle) && (angle < 157.5D))
            direction = "northwest";
        if ((157.5D <= angle) && (angle < 202.5D))
            direction = "west";
        if ((202.5D <= angle) && (angle < 247.5D))
            direction = "southwest";
        if ((247.5D <= angle) && (angle < 292.5D))
            direction = "south";
        if ((292.5D <= angle) && (angle < 337.5D))
            direction = "southeast";
        if ((337.5D <= angle) && (angle < 360.0D))
            direction = "east";
        
        return direction;
    }
}
