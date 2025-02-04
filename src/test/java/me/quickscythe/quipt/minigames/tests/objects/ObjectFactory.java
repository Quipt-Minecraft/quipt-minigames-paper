package me.quickscythe.quipt.minigames.tests.objects;

import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import org.json.JSONObject;

import java.io.File;

public class ObjectFactory {

    private static final String testArena = "{\n" +
            "  \"world\": \"colosseum\",\n" +
            "  \"locations\": {\n" +
            "    \"lobby\": {\n" +
            "      \"x\": 255,\n" +
            "      \"y\": 10,\n" +
            "      \"z\": -255,\n" +
            "      \"yaw\": 0,\n" +
            "      \"pitch\": 0,\n" +
            "      \"type\": \"lobby\"\n" +
            "    },\n" +
            "    \"default_1\": {\n" +
            "      \"x\": 219,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -255,\n" +
            "      \"yaw\": -90,\n" +
            "      \"type\": \"spawn\"\n" +
            "    },\n" +
            "    \"default_2\": {\n" +
            "      \"x\": 230,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -243,\n" +
            "      \"yaw\": -135,\n" +
            "      \"type\": \"spawn\"\n" +
            "    },\n" +
            "    \"default_3\": {\n" +
            "      \"x\": 219,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -249,\n" +
            "      \"yaw\": -114,\n" +
            "      \"type\": \"spawn\"\n" +
            "    },\n" +
            "    \"default_4\": {\n" +
            "      \"x\": 265,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -237,\n" +
            "      \"yaw\": 135,\n" +
            "      \"type\": \"spawn\"\n" +
            "    },\n" +
            "    \"default_5\": {\n" +
            "      \"x\": 296,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -255,\n" +
            "      \"yaw\": 90,\n" +
            "      \"type\": \"spawn\"\n" +
            "    },\n" +
            "    \"default_6\": {\n" +
            "      \"x\": 283,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -263,\n" +
            "      \"yaw\": 83,\n" +
            "      \"type\": \"spawn\"\n" +
            "    },\n" +
            "    \"default_7\": {\n" +
            "      \"x\": 258,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -268,\n" +
            "      \"type\": \"spawn\"\n" +
            "    },\n" +
            "    \"default_8\": {\n" +
            "      \"x\": 249,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -256,\n" +
            "      \"yaw\": -79,\n" +
            "      \"type\": \"spawn\"\n" +
            "    },\n" +
            "    \"default_9\": {\n" +
            "      \"x\": 271,\n" +
            "      \"y\": -29,\n" +
            "      \"z\": -249,\n" +
            "      \"yaw\": 97,\n" +
            "      \"type\": \"spawn\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"supports_teams\": false,\n" +
            "  \"teams\": {\n" +
            "    \"red\": {\n" +
            "      \"color\": \"#FF0000\",\n" +
            "      \"name\": \"Red Team\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"metadata\": {\n" +
            "    \"name\": \"Colosseum\",\n" +
            "    \"description\": \"A large arena with a colosseum\",\n" +
            "    \"author\": \"QuickScythe\",\n" +
            "    \"spleef\": {\n" +
            "      \"layers\": [\n" +
            "        \"lime_stained_glass\",\n" +
            "        \"orange_stained_glass\",\n" +
            "        \"red_stained_glass\"\n" +
            "      ]\n" +
            "    },\n" +
            "    \"common\": {\n" +
            "      \"chests\": {\n" +
            "      },\n" +
            "      \"generators\": {\n" +
            "      }\n" +
            "    },\n" +
            "    \"bedwars\": {\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static ArenaDefinition testArenaDefinition() {
//        return ArenaDefinition
        return new ArenaDefinition("test", new JSONObject(testArena), new File("TestWorldFolder"));
    }
}
