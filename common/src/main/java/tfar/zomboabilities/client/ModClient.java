package tfar.zomboabilities.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import tfar.zomboabilities.network.C2SAbilityPacket;
import tfar.zomboabilities.network.C2SHoldAbilityPacket;
import tfar.zomboabilities.platform.Services;

public class ModClient {

    public static void clientTick() {
        if (Minecraft.getInstance().level != null && !Minecraft.getInstance().isPaused()) {
            boolean holding_p = ModKeybinds.BIND_1.isDown();
            while (ModKeybinds.BIND_1.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SAbilityPacket(0));
            }
            while (ModKeybinds.BIND_2.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SAbilityPacket(1));
            }
            while (ModKeybinds.BIND_3.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SAbilityPacket(2));
            }
            while (ModKeybinds.BIND_2.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SAbilityPacket(3));
            }
            Services.PLATFORM.sendToServer(new C2SHoldAbilityPacket(holding_p, false, false, false));
        }
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

}
