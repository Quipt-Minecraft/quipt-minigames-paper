package me.quickscythe.quipt.minigames.utils;

import me.quickscythe.quipt.api.QuiptIntegration;
import me.quickscythe.quipt.commands.CommandExecutor;
import me.quickscythe.quipt.minigames.MinigamesIntegration;
import me.quickscythe.quipt.minigames.commands.MinigameCommand;
import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import me.quickscythe.quipt.minigames.core.arenas.ArenaManager;
import me.quickscythe.quipt.minigames.core.templates.Spleef;
import me.quickscythe.quipt.minigames.core.templates.TestMinigame;
import me.quickscythe.quipt.utils.chat.MessageUtils;
import me.quickscythe.quipt.utils.heartbeat.HeartbeatUtils;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static net.kyori.adventure.text.Component.text;

public class MinigamesUtils {


    private static QuiptIntegration integration;
    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) throws IOException {
        MinigamesUtils.plugin = plugin;
        integration = new MinigamesIntegration(plugin);
        integration.enable();

        new CommandExecutor.Builder(new MinigameCommand(plugin)).register();

        ArenaManager.init(integration);


        MinigameManager.registerMinigame(Spleef.class);
        MinigameManager.registerMinigame(TestMinigame.class);

        HeartbeatUtils.init(plugin);

        MessageUtils.addMessage("minigame.common.countdown", text("").append(text("Starting in ", NamedTextColor.GRAY)).append(text("[0]", NamedTextColor.GREEN)).append(text(" seconds...", NamedTextColor.GRAY)));

        HeartbeatUtils.heartbeat(plugin).addFlutter(() -> {
            for (Minigame minigame : MinigameManager.minigames()) {
                if (minigame.started() > 0 && minigame.check()) {
                    minigame.end();
                }
            }
            return true;
        });
    }

    public static QuiptIntegration integration() {
        return integration;
    }

    public static @NotNull JavaPlugin plugin() {
        return plugin;
    }
}
