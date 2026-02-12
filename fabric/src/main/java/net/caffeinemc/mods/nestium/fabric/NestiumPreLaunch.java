package net.caffeinemc.mods.nestium.fabric;

import net.caffeinemc.mods.nestium.client.compatibility.checks.PreLaunchChecks;
import net.caffeinemc.mods.nestium.client.compatibility.environment.probe.GraphicsAdapterProbe;
import net.caffeinemc.mods.nestium.client.compatibility.workarounds.Workarounds;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class NestiumPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        PreLaunchChecks.checkEnvironment();
        GraphicsAdapterProbe.findAdapters();
        Workarounds.init();
    }
}
