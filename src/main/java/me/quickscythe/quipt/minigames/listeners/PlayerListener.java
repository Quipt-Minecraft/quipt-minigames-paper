package me.quickscythe.quipt.minigames.listeners;

import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import me.quickscythe.quipt.minigames.core.templates.Spleef;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public class PlayerListener implements Listener {

    public PlayerListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Component message = e.joinMessage();
        e.joinMessage(null);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (MinigameManager.getMinigame(player) == null) player.sendMessage(message);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) && !(e.getDamager() instanceof Player)) return;
        Player player = e.getEntity() instanceof Player ? (Player) e.getEntity() : (Player) e.getDamager();
        Minigame minigame = MinigameManager.getMinigame(player);
        if (minigame == null) return;
        if (e.getDamager() instanceof Player) {
            //Handle player attacking SOMETHING
            if (e.getEntity() instanceof Player) {
                if (!minigame.settings().canDamagePlayers()) e.setCancelled(true);
                if (minigame.arenaData().teams().length > 0) {
                    if (!minigame.settings().friendlyFire() && minigame.team(player) == minigame.team((Player) e.getEntity()))
                        e.setCancelled(true);
                }
            } else {
                if (e.getEntity() instanceof Vehicle)
                    if (!minigame.settings().canDamageVehicles()) e.setCancelled(true);
                    else if (!minigame.settings().canDamageMobs()) e.setCancelled(true);
            }


        }
        if (e.getEntity() instanceof Player) {
            //Handle player being attacked
        }
    }

    @EventHandler
    public void onBlockPunch(BlockDamageEvent e) {
        Minigame minigame = MinigameManager.getMinigame(e.getPlayer());
        if (minigame == null) return;
        if(!minigame.settings().canBreakBlocks()) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player player)) return;
        Minigame minigame = MinigameManager.getMinigame(player);
        if(minigame == null) return;
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL) && !minigame.settings().canTakeFallDamage()) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Minigame minigame = MinigameManager.getMinigame(e.getEntity());
        if (minigame == null) return;
        if (minigame.settings().canRespawn()) {
            minigame.arena().respawn(e.getPlayer());
            e.setCancelled(true);
            //todo handle respawns
            return;
        }
        minigame.spectate(e.getPlayer());
        e.setCancelled(true);

    }

}
