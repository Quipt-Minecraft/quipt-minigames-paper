package me.quickscythe.quipt.minigames.core.teams;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.json.JSONObject;

public class TeamDefinition {

    private final String id;
    private final String name;
    private final TextColor color;
    private final boolean friendlyFire;

    public TeamDefinition(String id, JSONObject data) {
        this.id = id;
        this.name = data.optString("name", id);
        this.color = data.has("color") ? TextColor.fromHexString(data.getString("color")) : NamedTextColor.GRAY;
        this.friendlyFire = data.optBoolean("friendlyFire", false);
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public TextColor color() {
        return color;
    }

    public boolean friendlyFire() {
        return friendlyFire;
    }
}
