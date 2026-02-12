package net.caffeinemc.mods.nestium.client;

import net.caffeinemc.mods.nestium.client.console.Console;
import net.caffeinemc.mods.nestium.client.console.message.MessageLevel;
import net.caffeinemc.mods.nestium.client.data.fingerprint.FingerprintMeasure;
import net.caffeinemc.mods.nestium.client.data.fingerprint.HashedFingerprint;
import net.caffeinemc.mods.nestium.client.gui.NestiumGameOptions;
import net.caffeinemc.mods.nestium.client.services.PlatformRuntimeInformation;
import net.caffeinemc.mods.nestium.dynamiclights.NestiumDynamicLights;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.tr7zw.entityculling.EntityCullingClient;

import java.io.IOException;

public class NestiumClientMod {
    private static NestiumGameOptions CONFIG;
    private static final Logger LOGGER = LoggerFactory.getLogger("Nestium");

    private static String MOD_VERSION;

    public static void onInitialization(String version) {
        MOD_VERSION = version;

        CONFIG = loadConfig();

        try {
            updateFingerprint();
        } catch (Throwable t) {
            LOGGER.error("Failed to update fingerprint", t);
        }

        // Initialize EntityCulling
        new EntityCullingClient().onInitialize();

        // Initialize NestiumDynamicLights
        new NestiumDynamicLights().init();

        // Initialize EnhancedBlockEntities
        foundationgames.enhancedblockentities.EnhancedBlockEntities.init();
    }

    public static NestiumGameOptions options() {
        if (CONFIG == null) {
            throw new IllegalStateException("Config not yet available");
        }

        return CONFIG;
    }

    public static Logger logger() {
        if (LOGGER == null) {
            throw new IllegalStateException("Logger not yet available");
        }

        return LOGGER;
    }

    private static NestiumGameOptions loadConfig() {
        try {
            return NestiumGameOptions.loadFromDisk();
        } catch (Exception e) {
            LOGGER.error("Failed to load configuration file", e);
            LOGGER.error("Using default configuration file in read-only mode");

            Console.instance().logMessage(MessageLevel.SEVERE, "nestium.console.config_not_loaded", true, 12.5);

            var config = NestiumGameOptions.defaults();
            config.setReadOnly();

            return config;
        }
    }

    public static void restoreDefaultOptions() {
        CONFIG = NestiumGameOptions.defaults();

        try {
            NestiumGameOptions.writeToDisk(CONFIG);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write config file", e);
        }
    }

    public static String getVersion() {
        if (MOD_VERSION == null) {
            throw new NullPointerException("Mod version hasn't been populated yet");
        }

        return MOD_VERSION;
    }

    private static void updateFingerprint() {
        var current = FingerprintMeasure.create();

        if (current == null) {
            return;
        }

        HashedFingerprint saved = null;

        try {
            saved = HashedFingerprint.loadFromDisk();
        } catch (Throwable t) {
            LOGGER.error("Failed to load existing fingerprint",  t);
        }

        if (saved == null || !current.looselyMatches(saved)) {
            HashedFingerprint.writeToDisk(current.hashed());
        }
    }

    public static boolean allowDebuggingOptions() {
        return PlatformRuntimeInformation.getInstance().isDevelopmentEnvironment();
    }
}
