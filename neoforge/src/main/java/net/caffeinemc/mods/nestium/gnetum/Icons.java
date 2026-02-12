package net.caffeinemc.mods.nestium.gnetum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;

public class Icons {
    public static final ResourceLocation TICK;
    public static final ResourceLocation EXCLAMATION;
    public static final ResourceLocation QUESTION;

    static {
        TICK = ResourceLocation.fromNamespaceAndPath("nestium_gnetum", "icons/tick.png");
        EXCLAMATION = ResourceLocation.fromNamespaceAndPath("nestium_gnetum", "icons/exclamation.png");
        QUESTION = ResourceLocation.fromNamespaceAndPath("nestium_gnetum", "icons/question.png");
        Minecraft.getInstance().getTextureManager().register(TICK, new SimpleTexture(TICK));
        Minecraft.getInstance().getTextureManager().register(EXCLAMATION, new SimpleTexture(EXCLAMATION));
        Minecraft.getInstance().getTextureManager().register(QUESTION, new SimpleTexture(QUESTION));
    }
}
