package me.quickscythe.quipt.minigames.core.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class MinigamePlayer {

    private final UUID uid;
    private final Location returnLocation;
    private final ItemStack[] returnInventory;

    public MinigamePlayer(UUID uid) {
        this.uid = uid;
        OfflinePlayer player = Bukkit.getOfflinePlayer(uid);
        if(player.hasPlayedBefore()){
            this.returnLocation = player.getLocation();
            if(player.isOnline()){
                this.returnInventory = player.getPlayer().getInventory().getContents();
            } else {
                this.returnInventory = null;
            }
        } else {
            this.returnLocation = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
            this.returnInventory = null;
        }
    }

    public Player player() {
        return Bukkit.getOfflinePlayer(uid).getPlayer();
    }

    public UUID uid() {
        return uid;
    }

    public Location returnLocation() {
        return returnLocation;
    }

    public Optional<ItemStack[]> returnInventory() {
        return Optional.ofNullable(returnInventory);
    }
}
