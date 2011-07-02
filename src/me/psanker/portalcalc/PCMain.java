package me.psanker.portalcalc;

import org.bukkit.plugin.java.JavaPlugin;

public class PCMain extends JavaPlugin {
    
    private static final PCLog log = new PCLog();
    
    @Override
    public void onEnable() {
        log.log("PortalCalc init", 0);
        getCommand("pcalc").setExecutor(new PCCommandManager(this));
        getCommand("pc").setExecutor(new PCCommandManager(this));
    }
    
    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
        log.log("PortalCalc terminated, no errors.", 0);
    }
            
}
