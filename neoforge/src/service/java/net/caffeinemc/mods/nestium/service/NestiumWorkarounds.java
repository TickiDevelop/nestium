package net.caffeinemc.mods.nestium.service;

import net.caffeinemc.mods.nestium.client.compatibility.checks.PreLaunchChecks;
import net.caffeinemc.mods.nestium.client.compatibility.environment.probe.GraphicsAdapterProbe;
import net.caffeinemc.mods.nestium.client.compatibility.workarounds.Workarounds;
import net.caffeinemc.mods.nestium.client.compatibility.workarounds.nvidia.NvidiaWorkarounds;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;

public class NestiumWorkarounds implements GraphicsBootstrapper {
    @Override
    public String name() {
        return "nestium";
    }

    @Override
    public void bootstrap(String[] arguments) {
        PreLaunchChecks.checkEnvironment();
        GraphicsAdapterProbe.findAdapters();
        Workarounds.init();

        // Context creation happens earlier on NeoForge, so we need to apply this now
        NvidiaWorkarounds.applyEnvironmentChanges();
    }
}
