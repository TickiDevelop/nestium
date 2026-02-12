package net.caffeinemc.mods.nestium.neoforge;

import net.caffeinemc.mods.nestium.client.services.PlatformMixinOverrides;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForgeMixinOverrides implements PlatformMixinOverrides {
    protected static final String JSON_KEY_NESTIUM_OPTIONS = "nestium:options";

    public ForgeMixinOverrides() {
        super();
    }

    @Override
    public List<MixinOverride> applyModOverrides() {
        List<MixinOverride> list = new ArrayList<>();

        for (ModInfo meta : FMLLoader.getLoadingModList().getMods()) {
            meta.getConfigElement(JSON_KEY_NESTIUM_OPTIONS).ifPresent(override -> {
                if (override instanceof Map<?, ?> overrides && overrides.keySet().stream().allMatch(key -> key instanceof String)) {
                    overrides.forEach((key, value) -> {
                        if (!(value instanceof Boolean) || !(key instanceof String)) {
                            System.out.printf("[Nestium] Mod '%s' attempted to override option '%s' with an invalid value, ignoring", meta.getModId(), key);
                            return;
                        }

                        list.add(new MixinOverride(meta.getModId(), (String) key, (Boolean) value));
                    });
                } else {
                    System.out.printf("[Nestium] '%s' contains invalid Nestium option overrides, ignoring", meta.getModId());
                }
            });
        }

        return list;
    }
}
