package net.caffeinemc.mods.nestium.gnetum.mixins;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import net.neoforged.fml.loading.LoadingModList;

import java.util.List;
import java.util.Set;

public class GnetumMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("compat.jade")) {
            return isModLoaded("jade");
        }
        if (mixinClassName.contains("compat.journeymap")) {
            return isModLoaded("journeymap");
        }
        if (mixinClassName.contains("compat.pingwheel")) {
            return isModLoaded("ping-wheel") || isModLoaded("pingwheel");
        }
        return true;
    }

    private boolean isModLoaded(String modId) {
        try {
            return LoadingModList.get().getModFileById(modId) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
