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
import tfar.zomboabilities.utils.AbilityUtils;
import tfar.zomboabilities.PlayerDuck;
import tfar.zomboabilities.ZomboAbilities;
import tfar.zomboabilities.abilities.Ability;
import tfar.zomboabilities.utils.LivesUtils;

import java.util.Collection;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ability")
                .then(Commands.literal("give").requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.argument("players",EntityArgument.players())
                                .then(Commands.argument("ability", StringArgumentType.string())
                                        .suggests(Suggestions.ALL_ABILITIES)
                                        .executes(ModCommands::setAbility)
                                )
                        )
                )
                .then(Commands.literal("take").requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.argument("players",EntityArgument.players())
                                        .executes(ModCommands::removeAbility)
                        )
                )
                .then(Commands.literal("query")
                        .then(Commands.argument("player",EntityArgument.player())
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                .executes(ModCommands::queryAbility)
                        )
                        .executes(ModCommands::queryAbilitySelf)
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
                        ).executes(ModCommands::getSelfLives)
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
            Ability previous = AbilityUtils.getAbility(player).orElse(null);
            AbilityUtils.setAbility(player,ability);
            updateAbility(player,previous,ability);
        }
        return players.size();
    }

    public static void updateAbility(ServerPlayer player,Ability prev,Ability next) {
        if (prev != null) {
            prev.removePassive(player);
        }
        if (next != null) {
            next.applyPassive(player);
        }
    }

    static int queryAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context,"player");
        context.getSource().sendSuccess(() -> Component.literal("Ability: "+query(player)),false);
        return 1;
    }

    static String query(ServerPlayer player) {
        return AbilityUtils.getAbility(player).map(Ability::getName).orElse(null);
    }

    static int queryAbilitySelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        context.getSource().sendSuccess(() -> Component.literal("Ability: "+query(player)),false);
        return 1;
    }

    static int removeAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");

        for (ServerPlayer player : players) {
            Ability previous = AbilityUtils.getAbility(player).orElse(null);
            AbilityUtils.removeAbility(player);
            updateAbility(player,previous,null);
        }
        return players.size();
    }


    static int addLives(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
        int lives = IntegerArgumentType.getInteger(context, "lives");
        for (ServerPlayer player : players) {
            LivesUtils.addLives(player,lives);
        }
        return players.size();
    }

    static int setLives(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
        int lives = IntegerArgumentType.getInteger(context, "lives");
        for (ServerPlayer player : players) {
            LivesUtils.setLives(player,lives);
        }
        return players.size();
    }

    static int getSelfLives(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        context.getSource().sendSuccess(() -> ZomboAbilities.getLivesInfo(player), false);
        return 1;
    }

    static int getLives(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        context.getSource().sendSuccess(() -> ZomboAbilities.getLivesInfo(player), false);
        return 1;
    }


}
