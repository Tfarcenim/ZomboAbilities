package tfar.zomboabilities.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.PlayerDuck;

import java.util.Collection;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ability")
                .then(Commands.literal("give"))
                .then(Commands.literal("take"))
        );

        dispatcher.register(Commands.literal("lives")
                .then(Commands.literal("add")
                        .then(Commands.argument("lives", IntegerArgumentType.integer())
                                .then(Commands.argument("players", EntityArgument.players())
                                        .executes(ModCommands::addLives)
                                )
                        )
                )
                .then(Commands.literal("set")
                        .then(Commands.argument("lives", IntegerArgumentType.integer())
                                .then(Commands.argument("players", EntityArgument.players())
                                        .executes(ModCommands::setLives)
                                )
                        )
                )
        );
    }

    static int addLives(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
        int lives = IntegerArgumentType.getInteger(context, "lives");
        for (ServerPlayer player : players) {
            PlayerDuck.of(player).addLives(lives);
        }
        return players.size();
    }

    static int setLives(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
        int lives = IntegerArgumentType.getInteger(context, "lives");
        for (ServerPlayer player : players) {
            PlayerDuck.of(player).setLives(lives);
        }
        return players.size();
    }

}
