package net.caffeinemc.mods.nestium.fabric.level;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.caffeinemc.mods.nestium.client.model.color.ColorProviderRegistry;
import net.caffeinemc.mods.nestium.client.model.light.LightPipelineProvider;
import net.caffeinemc.mods.nestium.client.render.chunk.compile.pipeline.FluidRenderer;
import net.caffeinemc.mods.nestium.client.services.PlatformLevelAccess;
import net.caffeinemc.mods.nestium.client.world.LevelSlice;
import net.caffeinemc.mods.nestium.client.world.NestiumAuxiliaryLightManager;
import net.caffeinemc.mods.nestium.fabric.render.FluidRendererImpl;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.List;
import java.util.function.Function;

public class FabricLevelAccess implements PlatformLevelAccess {
    @Override
    public @Nullable Object getBlockEntityData(BlockEntity blockEntity) {
        return blockEntity.getRenderData();
    }

    @Override
    public @Nullable NestiumAuxiliaryLightManager getLightManager(LevelChunk chunk, SectionPos pos) {
        return null;
    }
}
