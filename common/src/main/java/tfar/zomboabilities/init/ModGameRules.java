package tfar.zomboabilities.init;

import net.minecraft.world.level.GameRules;

public class ModGameRules {

    public static final GameRules.Key<GameRules.IntegerValue> RULE_STARTINGLIVES = GameRules.register("startingLives", GameRules.Category.PLAYER,GameRules.IntegerValue.create(10));

    public static void init() {

    }
}
