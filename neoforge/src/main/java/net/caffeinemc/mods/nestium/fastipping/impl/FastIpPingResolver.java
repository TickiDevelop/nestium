package net.caffeinemc.mods.nestium.fastipping.impl;

import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddressResolver;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Optional;

public class FastIpPingResolver implements ServerAddressResolver {
    public static final FastIpPingResolver INSTANCE = new FastIpPingResolver();

    private FastIpPingResolver() {}

    @Override
    public Optional<ResolvedServerAddress> resolve(ServerAddress address) {
        try {
            InetAddress inetAddress = InetAddress.getByName(address.getHost());
            // Apply the patch to avoid reverse DNS lookup
            inetAddress = InetAddressPatcher.patch(address.getHost(), inetAddress);
            return Optional.of(ResolvedServerAddress.from(new InetSocketAddress(inetAddress, address.getPort())));
        } catch (UnknownHostException e) {
            return Optional.empty();
        }
    }
}
