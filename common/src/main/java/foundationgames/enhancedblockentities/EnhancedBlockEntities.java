package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.model.ModelRegistrationHandler;
import foundationgames.enhancedblockentities.client.resource.template.TemplateLoader;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Items;

public final class EnhancedBlockEntities {
    public static final String MOD_ID = "enhancedblockentities";
    public static final String NAMESPACE = "ebe";
    public static final EBEConfig CONFIG = new EBEConfig();

    public static final TemplateLoader TEMPLATE_LOADER = new TemplateLoader();

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;

        ResourceUtil.ensureInitialized();
        ModelIdentifiers.init();
        EBESetup.setupResourceProviders();
        var config = CONFIG;
        if (config.renderEnhancedChests) {
            EBESetup.setupChests();
        }
        if (config.renderEnhancedSigns) {
            EBESetup.setupSigns();
        }
        if (config.renderEnhancedBeds) {
            EBESetup.setupBeds();
        }
        if (config.renderEnhancedShulkerBoxes) {
            EBESetup.setupShulkerBoxes();
        }

        registerItemProperties();
        
        initialized = true;
    }

    private static void registerItemProperties() {
        ItemProperties.register(
                Items.CHEST,
                EBEUtil.id("is_christmas"),
                (stack, level, entity, seed) -> DateUtil.isChristmas() ? 1.0f : 0.0f
        );
        ItemProperties.register(
                Items.TRAPPED_CHEST,
                EBEUtil.id("is_christmas"),
                (stack, level, entity, seed) -> DateUtil.isChristmas() ? 1.0f : 0.0f
        );
    }

    public static void reload(ReloadType type) {
        if (!initialized) {
            return;
        }

        load();
        Minecraft mc = Minecraft.getInstance();

        if (type == ReloadType.WORLD && mc.levelRenderer != null) {
            mc.levelRenderer.allChanged();
        } else if (type == ReloadType.RESOURCES) {
            mc.reloadResourcePacks();
        }
    }

    public static void load() {
        CONFIG.load();

        EnhancedBlockEntityRegistry.clear();
        ModelRegistrationHandler.clearPlugins();
        ResourceUtil.resetBasePack();
        ResourceUtil.resetTopLevelPack();

        if (CONFIG.renderEnhancedChests) {
            EBESetup.setupChests();
            EBESetup.setupRRPChests();
        }

        if (CONFIG.renderEnhancedSigns) {
            EBESetup.setupSigns();
            EBESetup.setupRRPSigns();
        }

        if (CONFIG.renderEnhancedBeds) {
            EBESetup.setupBeds();
            EBESetup.setupRRPBeds();
        }

        if (CONFIG.renderEnhancedShulkerBoxes) {
            EBESetup.setupShulkerBoxes();
            EBESetup.setupRRPShulkerBoxes();
        }

        EBESetup.setupResourceProviders();
    }
}