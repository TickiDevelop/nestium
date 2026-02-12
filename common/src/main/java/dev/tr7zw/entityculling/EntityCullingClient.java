package dev.tr7zw.entityculling;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import com.logisticscraft.occlusionculling.OcclusionCullingInstance;

import dev.tr7zw.entityculling.versionless.EntityCullingVersionlessBase;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.lwjgl.glfw.GLFW;

public class EntityCullingClient extends EntityCullingVersionlessBase {

    public static EntityCullingClient instance;
    public Set<BlockEntityType<?>> blockEntityWhitelist = new HashSet<>();
    public Set<EntityType<?>> entityWhitelist = new HashSet<>();
    public Set<EntityType<?>> tickCullWhistelist = new HashSet<>();
    public CullTask cullTask;
    public static KeyMapping keybind = new KeyMapping("key.entityculling.toggle", GLFW.GLFW_KEY_UNKNOWN,
            "text.entityculling.title");
    public static KeyMapping keybindBoxes = new KeyMapping("key.entityculling.toggleBoxes", GLFW.GLFW_KEY_UNKNOWN,
            "text.entityculling.title");
    private Set<Function<BlockEntity, Boolean>> dynamicBlockEntityWhitelist = new HashSet<>();
    private Set<Function<Entity, Boolean>> dynamicEntityWhitelist = new HashSet<>();
    private int tickCounter = 0;
    public double lastTickTime = 0;

    @Override
    public void onInitialize() {
        instance = this;
        super.onInitialize();
        culling = new OcclusionCullingInstance(config.tracingDistance, new Provider());
        cullTask = new CullTask(culling, blockEntityWhitelist, entityWhitelist);

        cullThread = new Thread(cullTask, "CullThread");
        cullThread.setUncaughtExceptionHandler((thread, ex) -> {
            LOGGER.error("The CullingThread has crashed! Please report the following stacktrace!", ex);
        });
        
        // initModloader logic handled externally or via hooks
    }

    @Override
    public void initModloader() {
        // No-op, called manually or via mixins
    }

    public void worldTick() {
        cullTask.requestCull = true;
    }

    public void clientTick() {
        if (!lateInit) {
            lateInit = true;
            cullThread.start();
            for (String blockId : config.blockEntityWhitelist) {
                Optional<BlockEntityType<?>> block = BuiltInRegistries.BLOCK_ENTITY_TYPE
                        .getOptional(ResourceLocation.parse(blockId));
                block.ifPresent(b -> {
                    blockEntityWhitelist.add(b);
                });
            }
            for (String entityType : config.tickCullingWhitelist) {
                Optional<EntityType<?>> entity = BuiltInRegistries.ENTITY_TYPE
                        .getOptional(ResourceLocation.parse(entityType));
                entity.ifPresent(e -> {
                    tickCullWhistelist.add(e);
                });
            }
            for (String entityType : config.entityWhitelist) {
                Optional<EntityType<?>> entity = BuiltInRegistries.ENTITY_TYPE
                        .getOptional(ResourceLocation.parse(entityType));
                entity.ifPresent(e -> {
                    entityWhitelist.add(e);
                });
            }
        }
        
        while (keybind.consumeClick()) {
                    enabled = !enabled;
                    if (enabled) {
                        Minecraft.getInstance().gui.getChat().addMessage(Component.translatable("nestium.entityculling.enabled").withStyle(ChatFormatting.GREEN));
                    } else {
                        Minecraft.getInstance().gui.getChat().addMessage(Component.translatable("nestium.entityculling.disabled").withStyle(ChatFormatting.RED));
                    }
                }
                
                while (keybindBoxes.consumeClick()) {
                    debugHitboxes = !debugHitboxes;
                    if (debugHitboxes) {
                        Minecraft.getInstance().gui.getChat().addMessage(
                                Component.translatable("nestium.entityculling.debug.enabled").withStyle(ChatFormatting.GREEN));
                    } else {
                        Minecraft.getInstance().gui.getChat().addMessage(
                                Component.translatable("nestium.entityculling.debug.disabled").withStyle(ChatFormatting.RED));
                    }
                }
        
        long start = System.nanoTime();
        Minecraft client = Minecraft.getInstance();
        boolean ingame = client.level != null && client.player != null && client.player.tickCount > 10;
        if (ingame && enabled) {
            boolean changed = false;
            if (tickCounter++ % config.captureRate == 0) {
                if (!config.skipEntityCulling) {
                    cullTask.setEntitiesForRendering(
                            StreamSupport.stream(client.level.entitiesForRendering().spliterator(), false).toList());
                }
                if (!config.skipBlockEntityCulling) {
                    Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();
                    for (int x = -8; x <= 8; x++) {
                        for (int z = -8; z <= 8; z++) {
                            LevelChunk chunk = client.level.getChunk(client.player.chunkPosition().x + x,
                                    client.player.chunkPosition().z + z);
                            blockEntities.putAll(chunk.getBlockEntities());
                        }
                    }
                    cullTask.setBlockEntities(blockEntities);
                }
                changed = true;
            }

            cullTask.setIngame(true);
            cullTask.setCameraMC(EntityCullingClient.instance.config.debugMode ? client.player.getEyePosition(0)
                    : client.gameRenderer.getMainCamera().getPosition());
            cullTask.requestCull = true;
            if (changed) {
                lastTickTime = (System.nanoTime() - start) / 1_000_000.0;
            }
        } else {
            cullTask.setIngame(false);
            cullTask.setEntitiesForRendering(Collections.emptyList());
            cullTask.setBlockEntities(Collections.emptyMap());
            lastTickTime = (System.nanoTime() - start) / 1_000_000.0;
        }
    }

    public AABB setupAABB(BlockEntity entity, BlockPos pos) {
        if (entity instanceof BannerBlockEntity) {
            return new AABB(pos).inflate(0, 1, 0);
        }
        return new AABB(pos);
    }

    public boolean isDynamicWhitelisted(BlockEntity entity) {
        for (Function<BlockEntity, Boolean> fun : dynamicBlockEntityWhitelist) {
            if (fun.apply(entity)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDynamicWhitelisted(Entity entity) {
        for (Function<Entity, Boolean> fun : dynamicEntityWhitelist) {
            if (fun.apply(entity)) {
                return true;
            }
        }
        return false;
    }

    public void addDynamicBlockEntityWhitelist(Function<BlockEntity, Boolean> function) {
        this.dynamicBlockEntityWhitelist.add(function);
    }

    public void addDynamicEntityWhitelist(Function<Entity, Boolean> function) {
        this.dynamicEntityWhitelist.add(function);
    }

}
