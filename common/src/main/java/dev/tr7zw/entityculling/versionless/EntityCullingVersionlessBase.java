package dev.tr7zw.entityculling.versionless;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.logisticscraft.occlusionculling.OcclusionCullingInstance;

public abstract class EntityCullingVersionlessBase {

    public static final Logger LOGGER = LoggerFactory.getLogger("EntityCulling");
    public OcclusionCullingInstance culling;
    public boolean debugHitboxes = false;
    public static boolean enabled = true;
    protected Thread cullThread;
    protected boolean pressed = false;
    protected boolean pressedBox = false;
    protected boolean lateInit = false;
    public Config config;
    protected final File settingsFile = new File("config", "nestium-culling.json");
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public int renderedBlockEntities = 0;
    public int skippedBlockEntities = 0;
    public int renderedEntities = 0;
    public int skippedEntities = 0;
    public int tickedEntities = 0;
    public int skippedEntityTicks = 0;

    public EntityCullingVersionlessBase() {
        super();
    }

    public void onInitialize() {
        if (settingsFile.exists()) {
            try {
                config = gson.fromJson(new String(Files.readAllBytes(settingsFile.toPath()), StandardCharsets.UTF_8),
                        Config.class);
            } catch (Exception ex) {
                LOGGER.error("Error while loading config! Creating a new one!", ex);
            }
        }
        if (config == null) {
            config = new Config();
            writeConfig();
        } else {
            if (ConfigUpgrader.upgradeConfig(config)) {
                writeConfig();
            }
        }
    }

    public void writeConfig() {
        if (settingsFile.exists())
            settingsFile.delete();
        try {
            Files.write(settingsFile.toPath(), gson.toJson(config).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public abstract void initModloader();

}
