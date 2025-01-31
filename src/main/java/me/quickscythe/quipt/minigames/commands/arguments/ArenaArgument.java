package me.quickscythe.quipt.minigames.commands.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import me.quickscythe.quipt.minigames.core.arenas.ArenaManager;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ArenaArgument implements CustomArgumentType.Converted<ArenaDefinition, String> {



    @Override
    public ArenaDefinition convert(@NotNull String nativeType) {
        return ArenaManager.arena(nativeType);
    }

    @Override
    public @NotNull ArgumentType<String> getNativeType() {
        return StringArgumentType.word();
    }

    @Override
    public <S> @NotNull CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {

        ArenaManager.arenas().values().forEach(arena -> builder.suggest(arena.id()));

        return builder.buildFuture();
    }
}
