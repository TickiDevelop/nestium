package dev.tr7zw.entityculling.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.Display;

@Mixin(Display.class)
public interface DisplayAccessor {

    @Invoker("setWidth")
    void invokeSetWidth(float width);

    @Invoker("setHeight")
    void invokeSetHeight(float height);

}
