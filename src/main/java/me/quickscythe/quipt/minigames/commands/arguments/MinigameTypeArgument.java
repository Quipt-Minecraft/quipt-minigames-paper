package me.quickscythe.quipt.minigames.commands.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import me.quickscythe.quipt.minigames.core.Minigame;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MinigameTypeArgument implements CustomArgumentType.Converted<Class<? extends Minigame>, String> {

    private final boolean openGamesOnly;

    public MinigameTypeArgument() {
        this(false);
    }

    public MinigameTypeArgument(boolean openGamesOnly) {
        this.openGamesOnly = openGamesOnly;
    }

    @Override
    public Class<? extends Minigame> convert(@NotNull String nativeType) {
        return MinigameManager.registeredMinigames().stream()
                .filter(minigame -> MinigameManager.settings(minigame).name().equalsIgnoreCase(nativeType))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @NotNull ArgumentType<String> getNativeType() {
        return StringArgumentType.word();
    }

    @Override
    public <S> @NotNull CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        List<String> suggestions = new ArrayList<>();
        MinigameManager.registeredMinigames().forEach(minigameType -> {
            String name = MinigameManager.settings(minigameType).name();
            if (openGamesOnly) {
                for (Minigame game : MinigameManager.minigames(minigameType)) {
                    if (!suggestions.contains(name)) {
                        suggestions.add(MinigameManager.settings(minigameType).name());
                    }
                }
            } else {
                if (!suggestions.contains(name))
                    suggestions.add(MinigameManager.settings(minigameType).name());
            }

        });

        suggestions.forEach(builder::suggest);

        return builder.buildFuture();
    }
}
