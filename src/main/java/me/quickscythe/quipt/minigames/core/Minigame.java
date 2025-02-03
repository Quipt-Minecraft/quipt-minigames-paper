package me.quickscythe.quipt.minigames.core;


import me.quickscythe.quipt.minigames.core.arenas.Arena;
import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import me.quickscythe.quipt.minigames.core.objects.MinigamePlayer;
import me.quickscythe.quipt.minigames.core.teams.TeamDefinition;
import me.quickscythe.quipt.minigames.utils.MinigamesUtils;
import me.quickscythe.quipt.utils.chat.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.kyori.adventure.text.Component.text;

public abstract class Minigame {

    private final List<UUID> players = new ArrayList<>();
    private final List<UUID> spectators = new ArrayList<>();
    private final List<MinigamePlayer> participants = new ArrayList<>();
    private final MinigameSettings settings;
    private final JavaPlugin plugin;
    private final ArenaDefinition arenaDefinition;
    private final Arena arena;
    private final UUID id;
    private boolean open = false;
    private long started = 0;

    public Minigame(JavaPlugin plugin, MinigameSettings settings, ArenaDefinition arenaDefinition) throws IOException {
        this.id = UUID.randomUUID();
        this.settings = settings;
        this.arenaDefinition = arenaDefinition;
        this.arena = new Arena(arenaDefinition);
        arena.loadWorld();
        this.plugin = plugin;

    }

    public abstract Listener listener();

    public abstract boolean check();

    public void end() {
        if (MinigamesUtils.integration().plugin().isPresent())
            Bukkit.getScheduler().runTaskLater(MinigamesUtils.integration().plugin().get(), new MinigameCountdown(
                    this,
                    5,
                    null,
                    null,
                    () -> true,
                    () -> {
                        try {
                            destroy();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    () -> {
                        //There was an error?
                    }
            ), 0);

    }

    public Arena arena() {
        return arena;
    }

    public void broadcast(Component message) {
        for (MinigamePlayer player : participants) {
            if (player.player() != null) player.player().sendMessage(message);
        }
    }


    public MinigameSettings settings() {
        return settings;
    }

    public List<UUID> players() {
        return players;
    }

    public List<UUID> spectators() {
        return spectators;
    }

    public List<MinigamePlayer> participants() {
        return participants;
    }

    public void init() {
        plugin.getServer().getPluginManager().registerEvents(listener(), plugin);
        open = true;
    }

    public boolean open() {
        return open;
    }

    public long started() {
        return started;
    }

    public void start() {

        for (UUID uid : players()) {
            Player player = plugin.getServer().getPlayer(uid);
            if (player != null) {
                arena().respawn(player, GameMode.SURVIVAL);
            }
        }
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new MinigameCountdown(this, 5, MinigameCountdown.Type.CHAT, "minigame.common.countdown", () -> players().size() > 1, () -> {
            started = System.currentTimeMillis();
            open = false;
        }, () -> {
            end();
            broadcast(text("Not enough players... Countdown has stopped.", NamedTextColor.RED));
        }), 0);
    }

    public void join(Player player, boolean spectate) {
        MinigamePlayer gamePlayer = player(player);
        if (players().size() >= settings().maxPlayer() || participants().contains(gamePlayer)) {
            player.sendMessage(MessageUtils.getMessage("message.game.full"));
            return;
        }
        if (MinigameManager.getMinigame(player) != null) {
            player.sendMessage(MessageUtils.getMessage("message.game.in-game"));
            return;
        }
        if (started > 0) spectate = true;
        participants().add(gamePlayer);
        player.teleport(arenaDefinition.locations().lobby().location(arena));
        player.getInventory().clear();
        player.setGameMode(GameMode.SPECTATOR);
        player.setVelocity(new Vector(0, 0, 0));
        if (spectate) spectators().add(player.getUniqueId());
        else players().add(player.getUniqueId());
        player.setHealth(player.getHealthScale());

        TextColor playerColor = spectate ? TextColor.color(0xA2A2A2) : TextColor.color(0x25DB29);
        TextColor actionColor = NamedTextColor.YELLOW;
        TextColor parenColor = TextColor.color(0x888888);
        TextColor numberColor = TextColor.color(0xF3A513);
        TextColor slashColor = TextColor.color(0x484848);
        broadcast(text("").append(text(player.getName(), playerColor).append(text(" has joined the game! ", actionColor).append(text("(", parenColor).append(text(players().size(), numberColor).append(text("/", slashColor).append(text(settings().maxPlayer(), numberColor)).append(text(")", parenColor))))))));
        if (players().size() >= settings().minPlayers() && started() <= 0) {
            startCountdown();
        }
    }

    public void startCountdown() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new MinigameCountdown(this, 10, MinigameCountdown.Type.CHAT, "minigame.common.countdown", () -> players().size() >= settings().minPlayers(), this::start, () -> broadcast(text("Not enough players... Countdown has stopped.", NamedTextColor.RED))), 0);
    }

    public void leave(Player player) {
        MinigamePlayer gamePlayer = player(player);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.teleport(gamePlayer.returnLocation());

        }, 2);
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(player.getHealthScale());

        if (gamePlayer.returnInventory().isPresent())
            player.getInventory().setContents(gamePlayer.returnInventory().get());
        player.setVelocity(new Vector(0, 0, 0));
        TextColor playerColor = TextColor.color(0xE9334B);
        TextColor actionColor = NamedTextColor.YELLOW;
        TextColor parenColor = TextColor.color(0x888888);
        TextColor numberColor = TextColor.color(0xF3A513);
        TextColor slashColor = TextColor.color(0x484848);
        broadcast(text("").append(text(player.getName(), playerColor).append(text(" has left the game! ", actionColor).append(text("(", parenColor).append(text(players().size(), numberColor).append(text("/", slashColor).append(text(settings().maxPlayer(), numberColor)).append(text(")", parenColor))))))));
        participants().remove(player(player));
        players().remove(player.getUniqueId());
        spectators().remove(player.getUniqueId());
    }

    public MinigamePlayer player(Player player) {
        for (MinigamePlayer gamePlayer : participants) {
            if (gamePlayer.uid().equals(player.getUniqueId())) {
                return gamePlayer;
            }
        }
        return new MinigamePlayer(player.getUniqueId());
    }

    public void destroy() throws IOException {
        List<MinigamePlayer> participants = new ArrayList<>(this.participants);
        for (MinigamePlayer player : participants) {
            Player p = player.player();
            if (p != null) {
                leave(p);
            } else {
                this.participants.remove(player);
                this.spectators.remove(player.uid());
                this.players.remove(player.uid());
            }
        }

        arena.unloadWorld();

        arena.deleteWorld();

        MinigameManager.removeCurrentMinigame(this);

    }

    public UUID id() {
        return id;
    }

    public ArenaDefinition arenaData() {
        return arenaDefinition;
    }

    public void spectate(@NotNull Player player) {
        players().remove(player.getUniqueId());
        if (!spectators().contains(player.getUniqueId())) spectators().add(player.getUniqueId());
        if (player.getLocation().y() <= 0) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.teleport(arenaDefinition.locations().lobby().location(arena));
            }, 1);
        }
        player.setGameMode(GameMode.SPECTATOR);

    }

    public @Nullable TeamDefinition team(Player player) {
        return null;
    }
}
