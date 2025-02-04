package me.quickscythe.quipt.minigames.core;

import me.quickscythe.quipt.minigames.MinigamesIntegration;
import me.quickscythe.quipt.minigames.core.annotations.MinigameStructure;
import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import me.quickscythe.quipt.minigames.core.objects.MinigamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MinigameManager {


    private static final Map<Class<? extends Minigame>, MinigameSettings> minigames = new HashMap<>();
    private static final List<Minigame> currentMinigames = new ArrayList<>();

    public static  <M extends Minigame> void registerMinigame(Class<M> minigameClass)  {
        if(!minigameClass.isAnnotationPresent(MinigameStructure.class)) throw new IllegalArgumentException("Minigame class must be annotated with @MinigameStructure");
        MinigameStructure structure = minigameClass.getAnnotation(MinigameStructure.class);
        minigames.put(minigameClass, MinigameSettings.convertStructure(structure));
    }

    public static <M extends Minigame> M createMinigame(MinigamesIntegration integration, Class<M> minigameClass, ArenaDefinition arenaDefinition) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MinigameSettings settings = minigames.getOrDefault(minigameClass, null);
        M minigame = minigameClass.getConstructor(MinigamesIntegration.class, MinigameSettings.class, ArenaDefinition.class).newInstance(integration, settings, arenaDefinition);
        minigame.init();
        currentMinigames.add(minigame);
        return minigame;
    }

    public static Set<Class<? extends Minigame>> registeredMinigames(){
        return minigames.keySet();
    }

    public static MinigameSettings settings(Class<? extends Minigame> minigame) {
        return minigames.getOrDefault(minigame, null);
    }

    public static Minigame getMinigame(Player player) {
        for(Minigame minigame : currentMinigames){
            MinigamePlayer minigamePlayer = minigame.player(player);
            if(minigame.participants().contains(minigamePlayer)) return minigame;
        }
        return null;
    }

    public static void removeCurrentMinigame(Minigame minigame) {
        currentMinigames.remove(minigame);
    }

    public static Minigame[] minigames(){
        return minigames(null);
    }

    public static Minigame[] minigames(@Nullable Class<? extends Minigame> minigameType) {
        List<Minigame> games = new ArrayList<>();
        for(Minigame game : currentMinigames){
            if(minigameType == null || game.getClass().equals(minigameType)) games.add(game);
        }
        return games.toArray(new Minigame[0]);
    }

    public static Minigame minigame(Class<? extends Minigame> minigameType, String potentialId) {
        for(Minigame game : minigames(minigameType)){
            if(game.id().toString().equals(potentialId)) return game;
        }
        return null;
    }

    public static void shutdown(){
        for(Minigame minigame : currentMinigames){
            try {
                minigame.destroy();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
