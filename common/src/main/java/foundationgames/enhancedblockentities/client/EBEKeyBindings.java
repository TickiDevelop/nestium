package foundationgames.enhancedblockentities.client;

import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class EBEKeyBindings {
    public static final String CATEGORY = "key.categories.ebe";
    public static final String OPEN_CONFIG_KEY = "key.ebe.open_config";

    public static final KeyMapping OPEN_CONFIG = new KeyMapping(
            OPEN_CONFIG_KEY,
            GLFW.GLFW_KEY_UNKNOWN,
            CATEGORY
    );

    public static void onClientTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        if (OPEN_CONFIG.consumeClick()) {
            mc.setScreen(new EBEConfigScreen(mc.screen));
        }
    }
}
