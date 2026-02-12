package net.caffeinemc.mods.nestium.ferritecore.mixin.config;

import java.io.IOException;
import java.util.List;

public interface IPlatformConfigHooks {
    static IPlatformConfigHooks loadHooks() {
        try {
            Class<?> handler = Class.forName("net.caffeinemc.mods.nestium.ferritecore.mixin.platform.ConfigFileHandler");
            return (IPlatformConfigHooks) handler.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void readAndUpdateConfig(List<FerriteConfig.Option> options) throws IOException;

    void collectDisabledOverrides(OverrideCallback disableOption);

    interface OverrideCallback {
        void addOverride(String option, String mod);
    }
}
