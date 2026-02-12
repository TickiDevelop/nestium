package net.caffeinemc.mods.nestium.mixin.features.textures.animations.tracking;

import net.caffeinemc.mods.nestium.client.render.texture.SpriteContentsExtension;
import net.minecraft.client.renderer.texture.SpriteContents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SpriteContents.class)
public abstract class SpriteContentsMixin implements SpriteContentsExtension {
    @Shadow
    @Final
    @Nullable
    private SpriteContents.AnimatedTexture animatedTexture;

    @Unique
    private boolean active;

    @Override
    public void nestium$setActive(boolean value) {
        this.active = value;
    }

    @Override
    public boolean nestium$hasAnimation() {
        return this.animatedTexture != null;
    }

    @Override
    public boolean nestium$isActive() {
        return this.active;
    }
}
