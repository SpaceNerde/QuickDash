package de.spacenerd.quickDashMc.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import de.spacenerd.quickDashMc.api.model.Player;

public class PlayerAPI {
    public ArrayList<Player> getOnlinePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        
        Bukkit.getOnlinePlayers().forEach(player -> players.add(
            new Player(
                player.getUniqueId(), 
                player.getName()
            )));

        return players;
    }
}
