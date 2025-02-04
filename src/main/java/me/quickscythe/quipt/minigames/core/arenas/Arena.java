package me.quickscythe.quipt.minigames.core.arenas;


import me.quickscythe.quipt.minigames.core.arenas.locations.ArenaLocation;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

public class Arena {

    private final ArenaDefinition data;
    private final File worldFolder;
    private World world;

    public Arena(ArenaDefinition data) {
        this.data = data;
        this.worldFolder = data.worldFolder();
    }


    public World world(){
        return world;
    }

    public void unloadWorld(){
        Bukkit.unloadWorld(world, false);
    }

    public void deleteWorld() throws IOException {
        Bukkit.unloadWorld(world, false);
        Files.walk(world().getWorldFolder().toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);

    }
//
//    public void World world(){
//        return world;
//    }

    public void loadWorld(){

        if(!worldFolder.exists()) throw new IllegalStateException("World folder does not exist");
        try {
            String fileName = System.currentTimeMillis() + "-arena";
            Path source = worldFolder.toPath();
            Path target = new File(fileName).toPath();
            Files.walk(source)
                    .forEach(sourcePath -> {
                        try {
                            Path targetPath = target.resolve(source.relativize(sourcePath));
                            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            this.world = Bukkit.createWorld(new WorldCreator(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void respawn(Player player){
        respawn(player, GameMode.SURVIVAL);
    }

    public void respawn(Player player, GameMode mode) {
        //todo
        // Teleport player to spawn point depending on teams and such
        // Set players health to max
        // Clear inventory
        // Give player items?

        player.teleport(data.locations().ofType(ArenaLocation.Type.SPAWN).random().location(this));

        player.setHealth(player.getHealthScale());
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
    }
}
