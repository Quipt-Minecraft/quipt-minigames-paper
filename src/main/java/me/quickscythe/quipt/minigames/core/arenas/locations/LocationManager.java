package me.quickscythe.quipt.minigames.core.arenas.locations;

import me.quickscythe.quipt.api.data.NestedListManager;
import me.quickscythe.quipt.api.utils.Metadata;
import me.quickscythe.quipt.minigames.utils.MinigamesUtils;
import me.quickscythe.quipt.minigames.core.teams.TeamDefinition;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationManager extends NestedListManager<ArenaLocation, ArenaLocation.Type> {


    public LocationManager(JSONObject data) {
        for (String key : data.keySet()) {
            JSONObject loc = data.getJSONObject(key);
            ArenaLocation.Type type = ArenaLocation.Type.valueOf(loc.getString("type").toUpperCase(Locale.ROOT));
            double x = loc.getDouble("x");
            double y = loc.getDouble("y");
            double z = loc.getDouble("z");
            Float yaw = loc.has("yaw") ? (float) loc.getDouble("yaw") : null;
            Float pitch = loc.has("pitch") ? (float) loc.getDouble("pitch") : null;
            Metadata metadata = loc.has("metadata") ? Metadata.of(loc.getJSONObject("metadata")) : null;
            all().add(new ArenaLocation(type, x, y, z, yaw, pitch, metadata));
        }
    }



    public ArenaLocation random(TeamDefinition team) {
        List<ArenaLocation> locations = new ArrayList<>(all());
        if (team != null) {
            for (ArenaLocation loc : locations) {
                if (loc.type().equals(ArenaLocation.Type.TEAM) && loc.hasMetadata() && loc.metadata().value("team", String.class).equals(team.id())) {
                    return loc;
                }
            }
            MinigamesUtils.integration().log("Error", "No team location found for " + team.name());
        }
        return locations.get((int) (Math.random() * locations.size()));
    }

    public @NotNull ArenaLocation lobby() {
        ArenaLocation lobby = ofType(ArenaLocation.Type.LOBBY).random();
        return lobby == null ? new ArenaLocation(ArenaLocation.Type.LOBBY, 0, 0, 0, null, null) : lobby;
    }
}