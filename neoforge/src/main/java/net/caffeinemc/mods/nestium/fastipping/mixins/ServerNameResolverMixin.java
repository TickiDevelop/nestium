package net.caffeinemc.mods.nestium.fastipping.mixins;

import net.caffeinemc.mods.nestium.fastipping.impl.FastIpPingResolver;
import net.minecraft.client.multiplayer.resolver.ServerAddressResolver;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerNameResolver.class)
public class ServerNameResolverMixin {
    @Redirect(
        method = "<clinit>",
        remap = false,
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/multiplayer/resolver/ServerAddressResolver;SYSTEM:Lnet/minecraft/client/multiplayer/resolver/ServerAddressResolver;"
        )
    )
    private static ServerAddressResolver redirectSystem() {
        return FastIpPingResolver.INSTANCE;
    }
}
