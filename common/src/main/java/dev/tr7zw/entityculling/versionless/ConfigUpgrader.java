package dev.tr7zw.entityculling.versionless;

public class ConfigUpgrader {

    public static boolean upgradeConfig(Config config) {
        boolean changed = false;
        if (config.configVersion < 7) {
            config.configVersion = 7;
            changed = true;
        }
        return changed;
    }

}
