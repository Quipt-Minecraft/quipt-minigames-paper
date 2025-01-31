package me.quickscythe.quipt.minigames.core.templates;

import me.quickscythe.quipt.minigames.core.annotations.MinigameStructure;
import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameSettings;
import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@MinigameStructure(
        name = "TestMinigame",
        description = "A minigame",
        author = "QuickScythe",
        rules = {"Have fun!"},
        arenas = {"mushrooms"},
        minPlayers = 2
)
public class TestMinigame extends Minigame {

    GameListener listener;

    public TestMinigame(JavaPlugin plugin, MinigameSettings settings, ArenaDefinition arenaDefinition) throws IOException {
        super(plugin, settings, arenaDefinition);
        listener = new GameListener();
    }

    @Override
    public Listener listener() {
        return listener;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }


    @Override
    public boolean check() {
        return false;
    }

    private class GameListener implements Listener {


        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            TestMinigame.this.join(e.getPlayer(), true);
        }

    }


}
