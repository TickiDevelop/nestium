package net.caffeinemc.mods.nestium.neoforge;

import net.caffeinemc.mods.nestium.client.gui.NestiumOptionsGUI;
import net.caffeinemc.mods.nestium.client.render.frapi.NestiumRenderer;
import net.caffeinemc.mods.nestium.client.util.FlawlessFrames;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforgespi.language.IModInfo;
import dev.tr7zw.entityculling.EntityCullingClient;
import net.caffeinemc.mods.nestium.ferritecore.impl.Deduplicator;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Function;

import net.caffeinemc.mods.nestium.gnetum.Gnetum;
import net.caffeinemc.mods.nestium.fastipping.FastIpPingMod;

@Mod(value = "nestium", dist = Dist.CLIENT)
public class NestiumForgeMod {
    public NestiumForgeMod(IEventBus bus, ModContainer modContainer) {
        // Initialize Gnetum
        new Gnetum(modContainer, bus, Dist.CLIENT);
        // Initialize FastIpPing
        new FastIpPingMod();

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (minecraft, screen) -> NestiumOptionsGUI.createScreen(screen));
        RendererAccess.INSTANCE.registerRenderer(NestiumRenderer.INSTANCE);

        bus.addListener(this::registerKeyMappings);
        bus.addListener(this::registerReloadListeners);

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        for (IModInfo mod : ModList.get().getMods()) {
            String handler = (String) mod.getModProperties().getOrDefault("frex:flawless_frames_handler", null);

            if (handler == null) {
                continue;
            }

            try {
                lookup.findStatic(Class.forName(handler), "acceptController", MethodType.methodType(void.class, Function.class)).invoke(FlawlessFrames.getProvider());
            } catch (Throwable e) {
                throw new RuntimeException("Failed to execute Flawless Frames handler for mod " + mod.getModId() + "!", e);
            }
        }
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(EntityCullingClient.keybind);
        event.register(EntityCullingClient.keybindBoxes);
    }

    private void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        Deduplicator.registerReloadListener();
    }
}