package tfar.zomboabilities.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.NotNull;
import tfar.zomboabilities.Abilities;
import tfar.zomboabilities.attachments.CommonDataAttachments;
import tfar.zomboabilities.init.ModLevels;
import tfar.zomboabilities.utils.AbilityUtils;
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

        dispatcher.register(Commands.literal("revive")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.argument("lives", IntegerArgumentType.integer())
                        .then(Commands.argument("players", EntityArgument.players())
                                        .executes(ModCommands::revive)
                                )
                )
        );

        dispatcher.register(Commands.literal("dangersafe")
                .then(Commands.argument("player",EntityArgument.player())
                        .executes(ModCommands::dangerSafe)
                )
                .then(Commands.literal("clear").executes(ModCommands::clearDangerSafe))
        );
    }

    static int dangerSafe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ServerPlayer other = EntityArgument.getPlayer(context,"player");
        AbilityUtils.insert(player, CommonDataAttachments.IGNORE_PLAYERS,other.getUUID());
        return 1;
    }

    static int clearDangerSafe(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        AbilityUtils.clear(player,CommonDataAttachments.IGNORE_PLAYERS);
        return 1;
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
            Ability previous = AbilityUtils.getAbility(player);
            AbilityUtils.setAbility(player,ability);
            updateAbility(player,previous,ability);
        }
        return players.size();
    }

    public static void updateAbility(ServerPlayer player, @NotNull Ability prev, @NotNull Ability next) {
        prev.onRemoved(player);
        next.onAdded(player);
    }

    static int queryAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context,"player");
        context.getSource().sendSuccess(() -> Component.literal("Ability: "+query(player)),false);
        return 1;
    }

    static String query(ServerPlayer player) {
        return String.valueOf(AbilityUtils.getAbility(player));
    }

    static int queryAbilitySelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        context.getSource().sendSuccess(() -> Component.literal("Ability: "+query(player)),false);
        return 1;
    }

    static int removeAbility(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");

        for (ServerPlayer player : players) {
            Ability previous = AbilityUtils.getAbility(player);
            AbilityUtils.removeAbility(player);
            updateAbility(player,previous,Abilities.NONE);
        }
        return players.size();
    }

    static int revive(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "players");
        int lives = IntegerArgumentType.getInteger(context, "lives");
        int count = 0;
        for (ServerPlayer player : players) {
            if (LivesUtils.getLives(player) <=0) {
                LivesUtils.setLives(player, lives);
                MinecraftServer server = player.server;
                ServerLevel level = server.overworld();
                player.setRespawnPosition(ServerLevel.OVERWORLD,null,0,false,false);
                BlockPos spawn = level.getSharedSpawnPos();
                player.teleportTo( level,spawn.getX() + 0.5, spawn.getY() + 1, spawn.getZ() + 0.5,
                        level.getSharedSpawnAngle(), 0);
                player.setGameMode(GameType.SURVIVAL);
                AbilityUtils.removeAbility(player);
            }
        }
        return count;
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
