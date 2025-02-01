package me.quickscythe.quipt.minigames.core.templates;

import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import me.quickscythe.quipt.minigames.core.MinigameSettings;
import me.quickscythe.quipt.minigames.core.annotations.MinigameStructure;
import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@MinigameStructure(
        name = "Spleef",
        description = "Classic spleef!",
        author = "QuickScythe",
        rules = {"Have fun!"},
        arenas = {"mushrooms"},
        minPlayers = 2,
        maxPlayers = 10,
        canRespawn = false
)
public class Spleef extends Minigame {

    GameListener listener;
    List<String> layers;

    public Spleef(JavaPlugin plugin, MinigameSettings settings, ArenaDefinition arenaDefinition) throws IOException {
        super(plugin, settings, arenaDefinition);
        listener = new GameListener();
        layers = new ArrayList<>();
        for(Object layer : arenaData().metadata().get("spleef").value("layers", JSONArray.class).toList()){
            layers.add(layer.toString());
        }
    }

    @Override
    public Listener listener() {
        return listener;
    }

    @Override
    public boolean check() {
        return players().size() == 1;
    }

    class GameListener implements Listener {

        @EventHandler
        public void onBlockPunch(BlockDamageEvent e) {
            if (MinigameManager.getMinigame(e.getPlayer()) != Spleef.this) return;
            if (started() > 0 && layers.contains(e.getBlock().getType().name().toLowerCase(Locale.ROOT)))
                e.getBlock().setType(Material.AIR);
        }
    }
}
