package me.quickscythe.quipt.minigames.utils;

import me.quickscythe.quipt.api.QuiptIntegration;
import me.quickscythe.quipt.commands.CommandExecutor;
import me.quickscythe.quipt.minigames.MinigamesIntegration;
import me.quickscythe.quipt.minigames.commands.MinigameCommand;
import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import me.quickscythe.quipt.minigames.core.arenas.ArenaManager;
import me.quickscythe.quipt.minigames.core.templates.SkyWars;
import me.quickscythe.quipt.minigames.core.templates.Spleef;
import me.quickscythe.quipt.minigames.core.templates.TestMinigame;
import me.quickscythe.quipt.utils.PaperIntegration;
import me.quickscythe.quipt.utils.chat.MessageUtils;
import me.quickscythe.quipt.utils.heartbeat.HeartbeatUtils;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static net.kyori.adventure.text.Component.text;

public class MinigamesUtils {


    private static PaperIntegration integration;

    public static void init(PaperIntegration integration) {
        integration.enable();

    }

    public static PaperIntegration integration() {
        return integration;
    }

}
