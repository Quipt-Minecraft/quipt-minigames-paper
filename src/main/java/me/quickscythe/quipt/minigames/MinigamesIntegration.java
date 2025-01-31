package me.quickscythe.quipt.minigames;

import me.quickscythe.quipt.api.QuiptIntegration;
import me.quickscythe.quipt.api.logger.QuiptLogger;
import me.quickscythe.quipt.utils.chat.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MinigamesIntegration extends QuiptIntegration {

    private final JavaPlugin plugin;
    private final QuiptLogger paperLogger;
    private final File dataFolder;

    public MinigamesIntegration(JavaPlugin plugin) {
        this.plugin = plugin;
        paperLogger = new QuiptLogger(this);
        dataFolder = plugin.getDataFolder();
    }

    @Override
    public void enable() {
        if(!dataFolder.exists()) dataFolder.mkdirs();
    }

    @Override
    public void log(String tag, String message) {
        paperLogger.log("[" + tag + "] " + message);
    }

    @Override
    public File dataFolder() {
        return dataFolder;
    }

    @Override
    public String name() {
        return "quipt-minigames";
    }
}
