package de.spacenerd.quickDashMc;

import java.util.logging.Logger;

import de.spacenerd.quickDashMc.api.API;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;

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
        setupRoutes();
        
        // Setup Authentication
        javalin.beforeMatched(ctx -> {
            if (ctx.endpointHandlerPath().startsWith("/api")) {
                if (!isAuthenticated(ctx)) {
                    throw new UnauthorizedResponse(); // Tryed to access restricted content
               }
            }
        });
        
        // TODO: Make port configureable
        // Start web server on port 7070
        javalin.start(7070);
    }

    public void setupRoutes() {
        API api = new API();
        
        // Protected Route /api/
        javalin.get("/api/players", ctx -> api.getPlayers(ctx));
    }

    public void stopWebServer() {
        javalin.stop();
    }

    // TODO: Remove this function and replace it with a proper user handler and move AuthToken into config file
    private static boolean isAuthenticated(Context ctx) {
        String header = ctx.header("Authorization");
        if (header != null && header.startsWith("Test ")) {
            String token = header.substring(5);
            // Yes.... yes i know this is cheap....
            return "rand-token".equals(token);
        }

        return false;
    }
}
