package de.spacenerd.quickDashMc;

import java.util.logging.Logger;

import io.javalin.Javalin;

public class WebServer {
    private final QuickDashMc instance;
    private final Logger logger;

    private final Javalin javalin;

    public WebServer(QuickDashMc instance, Logger logger) {
        this.instance = instance;
        this.logger = logger;
        this.javalin = Javalin.create();
    }

    public void initWebServer() {
        // TODO: Make port configureable
        // Start web server on port 7070
        javalin.start(7070);
    }
}
