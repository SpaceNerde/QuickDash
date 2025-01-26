package de.spacenerd.quickDashMc.api.model;

import java.util.UUID;

public class Player {
    private UUID uuid;
    private String name;

    public Player(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
