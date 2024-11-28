package tfar.zomboabilities.client;

import tfar.zomboabilities.network.C2SAbilityPacket;
import tfar.zomboabilities.platform.Services;

public class ModClient {

    public static void clientTick() {
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
    }
}
