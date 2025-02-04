package me.quickscythe.quipt.minigames.core.arenas;


import me.quickscythe.quipt.api.utils.Metadata;
import me.quickscythe.quipt.minigames.core.arenas.locations.LocationManager;
import me.quickscythe.quipt.minigames.core.teams.TeamDefinition;
import org.json.JSONObject;

import java.io.File;

public class ArenaDefinition {

    private final File worldFolder;
    private final LocationManager locationManager;
    private final String name;
    private final String id;
    private final Metadata metadata;
    private final TeamDefinition[] teams;


    public ArenaDefinition(String id, JSONObject data, File worldFolder) {
        this.id = id;
        this.worldFolder = worldFolder;
        if(!data.has("locations")) throw new IllegalArgumentException("No locations defined for arena");
        this.locationManager = new LocationManager(data.getJSONObject("locations"));
        if(!data.has("metadata")) throw new IllegalStateException("No metadata defined for arena");
        this.metadata = Metadata.of(data.getJSONObject("metadata"));
        try {
            this.name = metadata.value("name", String.class);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("No name defined for arena (" + id + ")");
        }
        if(!data.has("teams")) teams = new TeamDefinition[0];
        else {
            JSONObject teams = data.getJSONObject("teams");
            this.teams = new TeamDefinition[teams.keySet().size()];
            int i = 0;
            for(String key : teams.keySet()){
                this.teams[i] = new TeamDefinition(key, teams.getJSONObject(key));
                i++;
            }
        }
    }

    public TeamDefinition[] teams(){
        return teams;
    }

    public Metadata metadata(){
        return metadata;
    }

    public File worldFolder(){
        return worldFolder;
    }

    public String name(){
        return name;
    }

    public String id(){
        return id;
    }


    public LocationManager locations() {
        return locationManager;
    }
}
