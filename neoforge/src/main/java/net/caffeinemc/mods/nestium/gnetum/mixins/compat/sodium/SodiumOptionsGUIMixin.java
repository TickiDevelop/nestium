package net.caffeinemc.mods.nestium.gnetum.mixins.compat.sodium;

import net.caffeinemc.mods.nestium.gnetum.compat.nestium.GnetumSodiumPage;
import net.caffeinemc.mods.nestium.client.gui.NestiumOptionsGUI;
import net.caffeinemc.mods.nestium.client.gui.options.OptionPage;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = NestiumOptionsGUI.class, remap = false)
public class SodiumOptionsGUIMixin {
    @Shadow
    @Final
    private List<OptionPage> pages;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void gnetum$init(Screen prevScreen, CallbackInfo ci) {
        this.pages.add(new GnetumSodiumPage());
    }
}
