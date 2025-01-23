package de.spacenerd.quickDashMc;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class QuickDashMc extends JavaPlugin{

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "QuickDashMc has been enabled");
    }
}
