package net.caffeinemc.mods.nestium.gnetum;

import net.caffeinemc.mods.nestium.gnetum.gui.ConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class GnetumConfigScreenFactory implements IConfigScreenFactory {
    @Override
    public Screen createScreen(ModContainer modContainer, Screen parent) {
        Gnetum.ensureInitialized();
        return new ConfigScreen(parent);
    }
}
