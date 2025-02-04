package me.quickscythe.quipt.minigames.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.quickscythe.quipt.commands.CommandExecutor;
import me.quickscythe.quipt.minigames.commands.arguments.ArenaArgument;
import me.quickscythe.quipt.minigames.commands.arguments.MinigameTypeArgument;
import me.quickscythe.quipt.minigames.utils.MinigamesUtils;
import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;
import static net.kyori.adventure.text.Component.text;

public class MinigameCommand extends CommandExecutor {


    public MinigameCommand(JavaPlugin plugin) {
        super(plugin, "minigame");
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> execute() {
        return literal(getName())
                .executes(context -> {
                    return 1;
                })
                .then(literal("leave")
                        .executes(context -> {
                            if(!(context.getSource().getSender() instanceof Player player)) return 0;
                            Minigame minigame = MinigameManager.getMinigame(player);
                            if(minigame != null){
                                minigame.leave(player);
                            } else {
                                player.sendMessage("You are not in a minigame.");
                            }
                            return 1;
                        })
                ).then(literal("create")
                        .executes(context -> {
                            return 1;
                        })
                        .then(argument("type", new MinigameTypeArgument())
                                .executes(context -> {
                                    context.getSource().getSender().sendMessage("Please specify an arena.");
                                    return 0;
                                })
                                .then(argument("arena", new ArenaArgument())
                                        .executes(context -> {
                                            Class<? extends Minigame> minigame = context.getArgument("type", Class.class);
                                            ArenaDefinition arena = context.getArgument("arena", ArenaDefinition.class);
                                            try {
                                                MinigameManager.createMinigame(MinigamesUtils.integration(), minigame, arena);
                                                context.getSource().getSender().sendMessage(text("Minigame created.", NamedTextColor.GREEN));
                                            } catch (NoSuchMethodException | InvocationTargetException |
                                                    InstantiationException | IllegalAccessException e) {
                                                throw new RuntimeException(e);
                                            }
                                            return 1;
                                        })
                                )
                        )
                ).then(literal("destroy")
                        .executes(context -> {
                            return 1;
                        })
                        .then(argument("game", new MinigameTypeArgument(false))
                                .executes(context -> {
                                    if(!(context.getSource().getSender() instanceof Player player)) return 0;
                                    Minigame minigame = MinigameManager.getMinigame(player);
                                    if(minigame != null){
                                        try {
                                            minigame.destroy();
                                        } catch (IOException e) {
                                            MinigamesUtils.integration().log("MinigameManager", "Error destroying minigame: " + e.getMessage());
                                        }
                                    } else {
                                        player.sendMessage("You are not in a minigame.");
                                    }

                                    return 1;
                                })
                        )
                ).then(literal("join")
                        .executes(context -> {
                            return 1;
                        })
                        .then(argument("game", new MinigameTypeArgument(true))
                                .executes(context -> {
                                    if(!(context.getSource().getSender() instanceof Player player)) return 0;

                                    Class<? extends Minigame> minigameType = context.getArgument("game", Class.class);
                                    Minigame[] games = MinigameManager.minigames(minigameType);
                                    if(games.length == 0){
                                        player.sendMessage("No games available.");
                                        return 0;
                                    }
                                    if(games.length == 1){
                                        games[0].join(player, false);
                                        return 1;
                                    }
                                    player.sendMessage("There are multiple games of that type:");
                                    for(Minigame game : games){
                                        TextComponent arenaName = text(game.arenaData().name(), game.open() ? NamedTextColor.GREEN : NamedTextColor.RED);
                                        TextComponent status = text(game.open() ? "(Open)" : "(In Progress)", game.open() ? NamedTextColor.AQUA : NamedTextColor.GRAY);
                                        TextComponent playerList = text("[" + game.players().size() + "/" + game.settings().maxPlayer() + "]", NamedTextColor.GOLD);
                                        Component message = text("  - ").append(arenaName.append(text(" ").append(status).append(text(" ").append(playerList)))).hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, text("Click to ." + (game.open() ? "join" : "spectate"), NamedTextColor.GOLD))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/minigame join " + game.settings().name() + " " + game.id()));
                                        player.sendMessage(message);
                                    }
//                                    todo select a game
                                    return 1;
                                })
                                .then(argument("id", StringArgumentType.string())
                                        .executes(context -> {
                                            String potentialId = context.getArgument("id", String.class);
                                            if(!(context.getSource().getSender() instanceof Player player)) return 0;
                                            Class<? extends Minigame> minigameType = context.getArgument("game", Class.class);
                                            Minigame game = MinigameManager.minigame(minigameType, potentialId);
                                            if(game == null){
                                                player.sendMessage("No game with that ID found.");
                                                return 0;
                                            }
                                            game.join(player, false);
                                            return 1;
                                        })))).build();
    }
}
