package net.caffeinemc.mods.nestium.ferritecore.util;

import net.caffeinemc.mods.nestium.ferritecore.IPlatformHooks;

public class Constants {
    public static final String MODID = "ferritecore";
    public static final IPlatformHooks PLATFORM_HOOKS;
    public static final String DISABLED_OVERRIDES_KEY = MODID + ":disabled_options";

    static {
        try {
            Class<?> hooks = Class.forName("net.caffeinemc.mods.nestium.ferritecore.PlatformHooks");
            PLATFORM_HOOKS = (IPlatformHooks) hooks.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
