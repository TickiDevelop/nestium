package net.caffeinemc.mods.nestium.ferritecore.mixin.mrl;

import net.caffeinemc.mods.nestium.ferritecore.mixin.config.FerriteConfig;
import net.caffeinemc.mods.nestium.ferritecore.mixin.config.FerriteMixinConfig;

public class Config extends FerriteMixinConfig {
    public Config() {
        super(FerriteConfig.MRL_CACHE);
    }
}
