package me.quickscythe.quipt.minigames;

import me.quickscythe.quipt.minigames.listeners.PlayerListener;
import me.quickscythe.quipt.minigames.utils.MinigamesUtils;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class PaperEntrypoint extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            MinigamesUtils.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Plugin startup logic

        new PlayerListener(this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        MinigameManager.shutdown();
    }
}
