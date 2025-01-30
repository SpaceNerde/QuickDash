package de.spacenerd.quickDashMc;

import java.util.logging.Logger;

import org.pac4j.core.config.Config;
import org.pac4j.javalin.CallbackHandler;
import org.pac4j.javalin.SecurityHandler;
import org.pac4j.oauth.client.GitHubClient;

import de.spacenerd.quickDashMc.api.API;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;

public class WebServer {
    private final QuickDashMc instance;
    private final Logger logger;

    private final Javalin javalin;

    private SecurityHandler gitSecurityHandler;
    CallbackHandler callback ;

    public WebServer(QuickDashMc instance, Logger logger) {
        this.instance = instance;
        this.logger = logger;
        this.javalin = Javalin.create();
    }

    public void initWebServer() {

        if (instance.getConfig().getBoolean("oAuth.active")) {
            setupOAuth();
        }
        
        setupRoutes();
        // Setup Authentication
        javalin.beforeMatched(ctx -> {
            if (!instance.getConfig().getBoolean("auth.active")) return;

            if (ctx.endpointHandlerPath().startsWith("/api")) {
                if (!isAuthenticated(ctx)) {
                    throw new UnauthorizedResponse(); // Tryed to access restricted content
               }
            }
        });
        
        // Start web server on port 7070
        javalin.start(instance.getConfig().getInt("api.port"));
    }

    public void setupRoutes() {
        API api = new API();
        
        javalin.get("/api/players", ctx -> api.getPlayers(ctx));
    }

    public void stopWebServer() {
        javalin.stop();
    }

    private void setupOAuth() {
        GitHubClient client = new GitHubClient();
        client.setKey(instance.getConfig().getString("oAuth.id"));
        client.setSecret(instance.getConfig().getString("oAuth.secret"));

        Config config = new Config("http://localhost:7070/login/oauth2/github", client);

        callback = new CallbackHandler(config, "http://localhost:7070/login/oauth2/github", true);

        gitSecurityHandler = new SecurityHandler(config, "GitHubClient");

        // callback
        javalin.get("/login/oauth2/github", callback);

        // Protected Route /api/players
        javalin.before("/api/players", gitSecurityHandler);
    }

    // TODO: Remove this function and replace it with a proper user handler and move AuthToken into config file
    private boolean isAuthenticated(Context ctx) {
        String header = ctx.header("Authorization");
        if (header != null && header.startsWith("Test ")) {
            String token = header.substring(5);
            // Yes.... yes i know this is cheap....
            return instance.getConfig().get("auth.token").equals(token);
        }

        return false;
    }
}
