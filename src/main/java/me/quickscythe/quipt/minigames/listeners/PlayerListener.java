package me.quickscythe.quipt.minigames.listeners;

import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener implements Listener {

    public PlayerListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Component message = e.joinMessage();
        e.joinMessage(null);
        for(Player player : Bukkit.getOnlinePlayers()){
            if(MinigameManager.getMinigame(player) == null) player.sendMessage(message);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Minigame minigame = MinigameManager.getMinigame(e.getEntity());
        if(minigame == null) return;
        if(minigame.settings().canRespawn()){
            //todo handle respawns
            return;
        }
        minigame.spectate(e.getPlayer());
        e.setCancelled(true);

    }

}
