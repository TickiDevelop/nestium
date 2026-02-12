package net.caffeinemc.mods.nestium.client.console;

import net.caffeinemc.mods.nestium.client.console.message.MessageLevel;
import org.jetbrains.annotations.NotNull;

public interface ConsoleSink {
    void logMessage(@NotNull MessageLevel level, @NotNull String text, boolean translatable, double duration);
}
