package net.caffeinemc.mods.nestium.neoforge.level;

import net.caffeinemc.mods.nestium.client.services.PlatformLevelAccess;
import net.caffeinemc.mods.nestium.client.world.NestiumAuxiliaryLightManager;
import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

public class NeoForgeLevelAccess implements PlatformLevelAccess {
    @Override
    public @Nullable Object getBlockEntityData(BlockEntity blockEntity) {
        return ((RenderDataBlockEntity) blockEntity).getRenderData();
    }

    @Override
    public @Nullable NestiumAuxiliaryLightManager getLightManager(LevelChunk chunk, SectionPos pos) {
        return (NestiumAuxiliaryLightManager) chunk.getAuxLightManager(pos.origin());
    }
}
