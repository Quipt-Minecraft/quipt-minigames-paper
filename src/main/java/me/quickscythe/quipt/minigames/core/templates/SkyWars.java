package me.quickscythe.quipt.minigames.core.templates;

import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameSettings;
import me.quickscythe.quipt.minigames.core.annotations.MinigameStructure;
import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@MinigameStructure(
        name = "SkyWars",
        description = "Fight with your team, and don't fall in the void! You get 3 lives.",
        author = "QuickScythe",
        rules = {"Have fun!"},
        arenas = {"mushrooms"},
        minPlayers = 2,
        maxPlayers = 10,
        canRespawn = false
)
public class SkyWars extends Minigame {

    GameListener listener;


    public SkyWars(JavaPlugin plugin, MinigameSettings settings, ArenaDefinition arenaDefinition) throws IOException {
        super(plugin, settings, arenaDefinition);
        listener = new GameListener();
        if(arenaDefinition.teams().length == 0) throw new IllegalArgumentException("No teams defined for arena");

    }

    @Override
    public Listener listener() {
        return listener;
    }

    @Override
    public boolean check() {
        return false;
    }

    private class GameListener implements Listener {

    }
}
