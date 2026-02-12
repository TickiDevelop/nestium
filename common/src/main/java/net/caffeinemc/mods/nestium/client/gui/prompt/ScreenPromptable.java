package net.caffeinemc.mods.nestium.client.gui.prompt;

import net.caffeinemc.mods.nestium.client.util.Dim2i;
import org.jetbrains.annotations.Nullable;

public interface ScreenPromptable {
    void setPrompt(@Nullable ScreenPrompt prompt);

    @Nullable ScreenPrompt getPrompt();

    Dim2i getDimensions();
}
