package net.caffeinemc.mods.nestium.ferritecore.mixin.fastmap;

import net.caffeinemc.mods.nestium.ferritecore.mixin.config.FerriteConfig;
import net.caffeinemc.mods.nestium.ferritecore.mixin.config.FerriteMixinConfig;

public class Config extends FerriteMixinConfig {
    public Config() {
        super(FerriteConfig.NEIGHBOR_LOOKUP);
    }
}
