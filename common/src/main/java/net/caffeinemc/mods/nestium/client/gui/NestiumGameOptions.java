package net.caffeinemc.mods.nestium.client.gui;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.caffeinemc.mods.nestium.client.gui.options.TextProvider;
import net.caffeinemc.mods.nestium.client.render.chunk.translucent_sorting.SortBehavior;
import net.caffeinemc.mods.nestium.client.services.PlatformRuntimeInformation;
import net.caffeinemc.mods.nestium.client.util.FileUtil;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

// TODO: Rename in Nestium 0.6
public class NestiumGameOptions {
    private static final String DEFAULT_FILE_NAME = "nestium-options.json";

    public final QualitySettings quality = new QualitySettings();
    public final AdvancedSettings advanced = new AdvancedSettings();
    public final PerformanceSettings performance = new PerformanceSettings();
    public final DynamicLightsSettings dynamicLights = new DynamicLightsSettings();
    public @NotNull DebugSettings debug = new DebugSettings();

    private boolean readOnly;

    private NestiumGameOptions() {
        // NO-OP
    }

    public static NestiumGameOptions defaults() {
        return new NestiumGameOptions();
    }

    public static class DynamicLightsSettings {
        public net.caffeinemc.mods.nestium.dynamiclights.DynamicLightsMode mode = net.caffeinemc.mods.nestium.dynamiclights.DynamicLightsMode.REALTIME;
        public boolean entities = false;
        public boolean self = true;
        public boolean blockEntities = true;
        public boolean waterSensitiveCheck = true;
        public net.caffeinemc.mods.nestium.dynamiclights.ExplosiveLightingMode creeper = net.caffeinemc.mods.nestium.dynamiclights.ExplosiveLightingMode.OFF;
        public net.caffeinemc.mods.nestium.dynamiclights.ExplosiveLightingMode tnt = net.caffeinemc.mods.nestium.dynamiclights.ExplosiveLightingMode.SIMPLE;
        public java.util.Map<String, Boolean> entitiesSettings = new java.util.HashMap<>();
    }

    public static class PerformanceSettings {
        public int chunkBuilderThreads = 0;
        @SerializedName("always_defer_chunk_updates_v2") // this will reset the option in older configs
        public boolean alwaysDeferChunkUpdates = true;

        public boolean animateOnlyVisibleTextures = true;
        public boolean useEntityCulling = true;
        public boolean useFogOcclusion = true;
        public boolean useBlockFaceCulling = true;
        public boolean useNoErrorGLContext = true;
    }

    public static class AdvancedSettings {
        public boolean enableMemoryTracing = false;
        public boolean useAdvancedStagingBuffers = true;

        public int cpuRenderAheadLimit = 3;
    }

    public static class DebugSettings {
        public boolean terrainSortingEnabled = true;

        @Deprecated(forRemoval = true)
        public SortBehavior getSortBehavior() {
            // TODO: This logic should not exist here, we need to move it into renderer initialization
            if (PlatformRuntimeInformation.getInstance().isDevelopmentEnvironment()) {
                return this.terrainSortingEnabled ? SortBehavior.DYNAMIC_DEFER_NEARBY_ZERO_FRAMES : SortBehavior.OFF;
            }

            return SortBehavior.DYNAMIC_DEFER_NEARBY_ZERO_FRAMES;
        }
    }

    public static class QualitySettings {
        public GraphicsQuality weatherQuality = GraphicsQuality.DEFAULT;
        public GraphicsQuality leavesQuality = GraphicsQuality.DEFAULT;

        public boolean enableVignette = true;
    }

    public enum GraphicsQuality implements TextProvider {
        DEFAULT("options.gamma.default"),
        FANCY("options.clouds.fancy"),
        FAST("options.clouds.fast");

        private final Component name;

        GraphicsQuality(String name) {
            this.name = Component.translatable(name);
        }

        @Override
        public Component getLocalizedName() {
            return this.name;
        }

        public boolean isFancy(GraphicsStatus graphicsStatus) {
            return (this == FANCY) || (this == DEFAULT && (graphicsStatus == GraphicsStatus.FANCY || graphicsStatus == GraphicsStatus.FABULOUS));
        }
    }

    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.PRIVATE)
            .create();

    public static NestiumGameOptions loadFromDisk() {
        Path path = getConfigPath();
        NestiumGameOptions config;

        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toFile())) {
                config = GSON.fromJson(reader, NestiumGameOptions.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse config", e);
            }
        } else {
            config = new NestiumGameOptions();
        }

        try {
            writeToDisk(config);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't update config file", e);
        }

        return config;
    }

    private static Path getConfigPath() {
        return PlatformRuntimeInformation.getInstance().getConfigDirectory()
                .resolve(DEFAULT_FILE_NAME);
    }

    public static void writeToDisk(NestiumGameOptions config) throws IOException {
        if (config.isReadOnly()) {
            throw new IllegalStateException("Config file is read-only");
        }

        Path path = getConfigPath();
        Path dir = path.getParent();

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        } else if (!Files.isDirectory(dir)) {
            throw new IOException("Not a directory: " + dir);
        }

        FileUtil.writeTextRobustly(GSON.toJson(config), path);
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly() {
        this.readOnly = true;
    }
}
