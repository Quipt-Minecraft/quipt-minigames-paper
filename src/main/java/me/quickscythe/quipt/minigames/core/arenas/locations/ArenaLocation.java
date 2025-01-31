package me.quickscythe.quipt.minigames.core.arenas.locations;

import me.quickscythe.quipt.api.data.TypeAdapter;
import me.quickscythe.quipt.api.utils.Metadata;
import me.quickscythe.quipt.minigames.core.arenas.Arena;
import me.quickscythe.quipt.utils.teleportation.LocationUtils;
import org.bukkit.Location;

import javax.annotation.Nullable;

public class ArenaLocation extends TypeAdapter<ArenaLocation.Type> {

    Location pseudoLocation;
    Metadata metadata;


    public ArenaLocation(ArenaLocation.Type type, Location location) {
        this(type, location.x(), location.y(), location.z(), location.getYaw(), location.getPitch(), null);
    }
    public ArenaLocation(ArenaLocation.Type type, Location location, @Nullable Metadata metadata) {
        this(type, location.x(), location.y(), location.z(), location.getYaw(), location.getPitch(), metadata);
    }

    public ArenaLocation(ArenaLocation.Type type, double x, double y, double z, @Nullable Float yaw, @Nullable Float pitch) {
        this(type, x, y, z, yaw, pitch, null);
    }

    public ArenaLocation(ArenaLocation.Type type, double x, double y, double z, @Nullable Float yaw, @Nullable Float pitch, @Nullable Metadata metadata) {
        super(type);
        pseudoLocation = new Location(null, x, y, z, yaw == null ? 0 : yaw, pitch == null ? 0 : pitch);
        this.metadata = metadata;
    }

    public Location location(Arena arena) {
        return new Location(arena.world(), pseudoLocation.x(), pseudoLocation.y(), pseudoLocation.z(), pseudoLocation.getYaw(), pseudoLocation.getPitch());
    }

    public boolean hasMetadata() {
        return metadata != null;
    }

    public Metadata metadata() {
        if(metadata == null)
            throw new IllegalStateException("No metadata defined for " + type().name() + " location (" + LocationUtils.serialize(pseudoLocation) + ")");
        return metadata;
    }

    public enum Type {
        SPAWN, LOBBY, SPECTATOR, TEAM, OTHER
    }
}