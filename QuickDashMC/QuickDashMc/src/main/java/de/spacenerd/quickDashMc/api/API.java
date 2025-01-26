package de.spacenerd.quickDashMc.api;

import io.javalin.http.Context;

public class API {
    private final PlayerAPI playerAPI;

    public API() {
        this.playerAPI = new PlayerAPI();
    }

    public void getPlayers(Context ctx) {
        ctx.json(playerAPI.getOnlinePlayers());
    }
}
