package net.caffeinemc.mods.nestium.client.gui.options.storage;

import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import net.caffeinemc.mods.nestium.client.gui.NestiumGameOptions;

import java.io.IOException;

public class NestiumOptionsStorage implements OptionStorage<NestiumGameOptions> {
    private final NestiumGameOptions options;

    public NestiumOptionsStorage() {
        this.options = NestiumClientMod.options();
    }

    @Override
    public NestiumGameOptions getData() {
        return this.options;
    }

    @Override
    public void save() {
        try {
            NestiumGameOptions.writeToDisk(this.options);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't save configuration changes", e);
        }

        NestiumClientMod.logger().info("Flushed changes to Nestium configuration");
    }
}
