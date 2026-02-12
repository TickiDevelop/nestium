package net.caffeinemc.mods.nestium.fabric.model;

import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import net.caffeinemc.mods.nestium.client.services.PlatformModelAccess;
import net.caffeinemc.mods.nestium.client.services.NestiumModelData;
import net.caffeinemc.mods.nestium.client.services.NestiumModelDataContainer;
import net.caffeinemc.mods.nestium.client.world.LevelSlice;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.List;

public class FabricModelAccess implements PlatformModelAccess {
    private static final NestiumModelDataContainer EMPTY_CONTAINER = new NestiumModelDataContainer(Long2ObjectMaps.emptyMap());

    @Override
    public Iterable<RenderType> getModelRenderTypes(BlockAndTintGetter level, BakedModel model, BlockState state, BlockPos pos, RandomSource random, NestiumModelData modelData) {
        return Collections.singleton(ItemBlockRenderTypes.getChunkRenderType(state));
    }

    @Override
    public List<BakedQuad> getQuads(BlockAndTintGetter level, BlockPos pos, BakedModel model, BlockState state, Direction face, RandomSource random, RenderType renderType, NestiumModelData modelData) {
        return model.getQuads(state, face, random);
    }

    @Override
    public NestiumModelDataContainer getModelDataContainer(Level level, SectionPos sectionPos) {
        return EMPTY_CONTAINER;
    }

    @Override
    public NestiumModelData getModelData(LevelSlice slice, BakedModel model, BlockState state, BlockPos pos, NestiumModelData originalData) {
        return null;
    }

    @Override
    public NestiumModelData getEmptyModelData() {
        return null;
    }
}
