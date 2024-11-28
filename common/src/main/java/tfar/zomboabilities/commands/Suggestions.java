package tfar.zomboabilities.commands;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import tfar.zomboabilities.Abilities;

public class Suggestions {

    protected static final SuggestionProvider<CommandSourceStack> ALL_ABILITIES = (commandContext, suggestionsBuilder) ->
            SharedSuggestionProvider.suggest(Abilities.ABILITIES_BY_NAME.keySet(),suggestionsBuilder);
}
