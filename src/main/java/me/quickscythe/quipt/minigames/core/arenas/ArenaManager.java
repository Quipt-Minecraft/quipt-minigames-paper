package me.quickscythe.quipt.minigames.core.arenas;

import me.quickscythe.quipt.api.QuiptIntegration;
import me.quickscythe.quipt.api.config.ConfigManager;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ArenaManager {

    private static File arenaFolder;
    private static File worldsFolder;

    private static final Map<String, ArenaDefinition> arenas = new HashMap<>();

    public static void init(QuiptIntegration integration) throws IOException {
        arenaFolder = new File(integration.dataFolder(), "arenas");
        if(!arenaFolder.exists()) integration.log("ArenaManager", "Creating arena folder..." + (arenaFolder.mkdirs() ? "Success!" : "Failed!"));

        worldsFolder = new File(arenaFolder, "worlds");
        if(!worldsFolder.exists()) integration.log("ArenaManager", "Creating worlds folder..." + (worldsFolder.mkdirs() ? "Success!" : "Failed!"));

        for(File file : Objects.requireNonNull(arenaFolder.listFiles())){
            if(file.getName().endsWith(".json")){
                integration.log("ArenaManager", "Loading arena " + file.getName());
                JSONObject arenaData = ConfigManager.loadJson(file);
                if(!arenaData.has("world")) {
                    integration.log("ArenaManager", "Failed to load arena " + file.getName() + " (No world specified)");
                    continue;
                }
                File worldFolder = new File(worldsFolder, arenaData.getString("world"));
                if(!worldFolder.exists()) {
                    integration.log("ArenaManager", "Failed to load arena " + file.getName() + " (World folder not found)");
                    continue;
                }
                String id = file.getName().replace(".json", "");
                arenas.put(id, new ArenaDefinition(id, arenaData, worldFolder));
            }
        }
    }

    public static ArenaDefinition arena(String name){
        return arenas.get(name);
    }

    public static Map<String, ArenaDefinition> arenas(){
        return arenas;
    }
}
