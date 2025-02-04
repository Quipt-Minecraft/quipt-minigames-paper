package me.quickscythe.quipt.minigames;

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
import org.jetbrains.annotations.Nullable;

import static net.kyori.adventure.text.Component.text;

public class MinigamesIntegration extends PaperIntegration {


    public MinigamesIntegration(@Nullable JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        super.enable();
        ArenaManager.init(this);
        MinigameManager.registerMinigame(Spleef.class);
        MinigameManager.registerMinigame(TestMinigame.class);
        MinigameManager.registerMinigame(SkyWars.class);


        MessageUtils.addMessage("minigame.common.countdown", text("").append(text("Starting in ", NamedTextColor.GRAY)).append(text("[0]", NamedTextColor.GREEN)).append(text(" seconds...", NamedTextColor.GRAY)));

        if (plugin().isPresent()) {
            HeartbeatUtils.init(this);
            HeartbeatUtils.heartbeat(this).addFlutter(() -> {
                for (Minigame minigame : MinigameManager.minigames()) {
                    if (minigame.started() > 0 && minigame.check()) {
                        minigame.end();
                    }
                }
                return true;
            });
        }
    }

    @Override
    public String name() {
        return "quipt-minigames";
    }


}
