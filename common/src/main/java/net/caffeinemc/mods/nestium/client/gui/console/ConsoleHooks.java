package net.caffeinemc.mods.nestium.client.gui.console;

import net.caffeinemc.mods.nestium.client.console.Console;
import net.minecraft.client.gui.GuiGraphics;

public class ConsoleHooks {
    public static void render(GuiGraphics graphics, double currentTime) {
        ConsoleRenderer.INSTANCE.update(Console.INSTANCE, currentTime);
        ConsoleRenderer.INSTANCE.draw(graphics);
    }
}
