package tfar.zomboabilities.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

    static final String CATEGORY = "Zombo Abilities";

    public static final KeyMapping BIND_1 = new KeyMapping("Primary", GLFW.GLFW_KEY_T,CATEGORY);
    public static final KeyMapping BIND_2 = new KeyMapping("Secondary", GLFW.GLFW_KEY_R,CATEGORY);
    public static final KeyMapping BIND_3 = new KeyMapping("Tertiary", GLFW.GLFW_KEY_C,CATEGORY);
    public static final KeyMapping BIND_4 = new KeyMapping("Quaternary", GLFW.GLFW_KEY_Y,CATEGORY);

}
