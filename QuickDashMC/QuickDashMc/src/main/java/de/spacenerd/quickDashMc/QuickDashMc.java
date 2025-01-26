package de.spacenerd.quickDashMc;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickDashMc extends JavaPlugin{
    private static Logger logger;
    private static QuickDashMc instance;
    private WebServer webServer;

    @Override
    public void onEnable() {
        logger = Bukkit.getLogger();
        instance = this;

        this.webServer = new WebServer(this, logger);
        webServer.initWebServer();

        logger.log(Level.INFO, "QuickDashMc has been enabled");
    }
}
