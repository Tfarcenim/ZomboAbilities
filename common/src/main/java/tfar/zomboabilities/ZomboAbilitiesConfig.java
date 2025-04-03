package tfar.zomboabilities;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import tfar.zomboabilities.utils.Scaling;

public class ZomboAbilitiesConfig {

    public static final Server SERVER;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        final Pair<Server,ModConfigSpec> specPair2 = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specPair2.getRight();
        SERVER = specPair2.getLeft();
    }

    public static class Server {
        public final ModConfigSpec.EnumValue<Scaling> healthScaling;
        public final ModConfigSpec.DoubleValue minHealth;
        public Server(ModConfigSpec.Builder builder) {
            builder.push("general");
            healthScaling = builder.defineEnum("health_scaling",Scaling.LINEAR);
            minHealth = builder.defineInRange("min_health_scaling",1d,0,100);
            builder.pop();
        }
    }
}
