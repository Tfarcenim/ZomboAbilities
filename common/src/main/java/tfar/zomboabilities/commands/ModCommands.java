package tfar.zomboabilities.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.abilities.Ability;

import java.util.Collection;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ability")
                .then(Commands.literal("give")
                        .then(Commands.argument("players",EntityArgument.players())
                                .then(Commands.argument("ability", StringArgumentType.string())
                                        .suggests(Suggestions.ALL_ABILITIES)
                                        .executes(ModCommands::setAbility)
                                )
                        )
                )
                .then(Commands.literal("take")
                        .then(Commands.argument("players",EntityArgument.players())
                                        .executes(ModCommands::removeAbility)
                        )
                )
        );

        dispatcher.register(Commands.literal("lives").requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
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
                .then(Commands.literal("get")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ModCommands::getLives)
                        )
                )
        );
    }

    static int setAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
        String s = StringArgumentType.getString(context,"ability");
        Ability ability = Abilities.ABILITIES_BY_NAME.get(s);
        if (ability == null) {
            context.getSource().sendFailure(Component.literal("No ability with name "+s+" found"));
            return 0;
        }
        for (ServerPlayer player : players) {
            PlayerDuck.of(player).setAbility(ability);
        }
        return players.size();
    }

    static int removeAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");

        for (ServerPlayer player : players) {
            PlayerDuck.of(player).setAbility(null);
        }
        return players.size();
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

    static int getSelfLives(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "player");
        int lives = IntegerArgumentType.getInteger(context, "lives");
        for (ServerPlayer player : players) {
            PlayerDuck.of(player).setLives(lives);
        }
        return players.size();
    }

    static int getLives(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        context.getSource().sendSuccess(() -> ZomboAbilities.getLivesInfo(player), false);
        return 1;
    }


}
