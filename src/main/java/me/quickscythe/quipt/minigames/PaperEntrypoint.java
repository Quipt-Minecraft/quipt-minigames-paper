package me.quickscythe.quipt.minigames;

import me.quickscythe.quipt.commands.CommandExecutor;
import me.quickscythe.quipt.minigames.commands.MinigameCommand;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import me.quickscythe.quipt.minigames.listeners.PlayerListener;
import me.quickscythe.quipt.minigames.utils.MinigamesUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperEntrypoint extends JavaPlugin {

    @Override
    public void onEnable() {
        MinigamesIntegration minigamesIntegration = new MinigamesIntegration(this);
        MinigamesUtils.init(minigamesIntegration);


        // Plugin startup logic

        new PlayerListener(this);
        new CommandExecutor.Builder(new MinigameCommand(this)).register();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        MinigameManager.shutdown();
    }
}
