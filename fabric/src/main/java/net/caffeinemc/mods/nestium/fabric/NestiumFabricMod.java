package net.caffeinemc.mods.nestium.fabric;

import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import net.caffeinemc.mods.nestium.client.render.frapi.NestiumRenderer;
import net.caffeinemc.mods.nestium.client.util.FlawlessFrames;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import dev.tr7zw.entityculling.EntityCullingClient;
import java.util.function.Consumer;

public class NestiumFabricMod implements ClientModInitializer {
    @Override
    @SuppressWarnings("unchecked")
    public void onInitializeClient() {
        ModContainer mod = FabricLoader.getInstance()
                .getModContainer("nestium")
                .orElseThrow(NullPointerException::new);

        NestiumClientMod.onInitialization(mod.getMetadata().getVersion().getFriendlyString());

        FabricLoader.getInstance()
                .getEntrypoints("frex_flawless_frames", Consumer.class)
                .forEach(api -> api.accept(FlawlessFrames.getProvider()));

        RendererAccess.INSTANCE.registerRenderer(NestiumRenderer.INSTANCE);

        KeyBindingHelper.registerKeyBinding(EntityCullingClient.keybind);
        KeyBindingHelper.registerKeyBinding(EntityCullingClient.keybindBoxes);
    }
}
